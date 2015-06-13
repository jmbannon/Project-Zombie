package breakable_windows;

import breakable_windows.GlassFormations.GlassOffset;
import org.bukkit.event.Listener;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import com.shampaggon.crackshot.events.WeaponHitBlockEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BlockBreakListener implements Listener
{
	private final Plugin plugin;
    private static GlassFormations formations;
    private static BlockSerialize serialize;
    private static int tickDelay = 0;
    
    private final File blockBuffer;
    private final File lightBuffer;
    
	public BlockBreakListener(final Plugin plugin) throws IOException
    {
		this.plugin = plugin;
        BlockBreakListener.formations = new GlassFormations();
        BlockBreakListener.serialize = new BlockSerialize(plugin);
        
        if (plugin.getDataFolder() == null || !plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        
        blockBuffer = new File(this.plugin.getDataFolder(), "block_buffer");
        lightBuffer = new File(this.plugin.getDataFolder(), "light_buffer");
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void meleeBlockBreakEvent(BlockBreakEvent event) throws IOException
    {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();

		if (player.getGameMode() == GameMode.SURVIVAL) {
			if (isBreakableBlock(block)) {
				chunckBreak(block);
				if (player.getItemInHand().getType() == Material.AIR)
					player.damage(0.5);
			}
			else if (isBreakableLight(block)) {
				storeAndBreakBlock(block, true);
                if (block.getType() != Material.TORCH &&
                        player.getItemInHand().getType() == Material.AIR)
					player.damage(0.5);
			}
		}
	}
		
	@EventHandler(priority = EventPriority.NORMAL)
	public void projectileBlockBreakEvent(WeaponHitBlockEvent event) throws IOException
    {
        final Block block = event.getBlock();
		if (isBreakableBlock(block))
			chunckBreak(block);
		else if (isBreakableLight(block))
			storeAndBreakBlock(block, true);
        else if (isTorch(block))
        {
            block.setType(Material.AIR);
            block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.GLASS.getId());
        }
	}

	public void storeAndBreakBlock(final Block theBlock,
                                   final boolean isLightBlock) throws IOException
    {
        final FileWriter blockWriter; 
        if (isLightBlock) {
            blockWriter = new FileWriter(lightBuffer, true);
            blockWriter.append(serialize.blockSerialize(theBlock));
        } else {
            blockWriter = new FileWriter(blockBuffer, true);
            blockWriter.append(serialize.blockSerialize(theBlock));
        }
        blockWriter.close();
        theBlock.getWorld().playEffect(theBlock.getLocation(), Effect.STEP_SOUND, Material.GLASS.getId());
		theBlock.setType(Material.AIR); 
	}
	
    public boolean isTorch(final Block block)
    {
        return block.getType().equals(Material.TORCH);
    }
    
	public boolean isBreakableBlock(final Block block)
    {
        final Material blockMaterial = block.getType();
		return (blockMaterial.equals(Material.GLASS)
				|| blockMaterial.equals(Material.THIN_GLASS));
	}
	
	public boolean isBreakableLight(final Block block)
    {
        final Material blockMaterial = block.getType();
		return (blockMaterial.equals(Material.GLOWSTONE)
				|| blockMaterial.equals(Material.REDSTONE_LAMP_ON)	
				|| blockMaterial.equals(Material.BEACON)
                || blockMaterial.equals(Material.REDSTONE_LAMP_OFF));
	}
	
    public void checkFormation(final Block glassBlock,
                               final GlassOffset[] offsets) throws IOException
    {
        Block glassBlockOffset;
        for (GlassOffset offset : offsets) {
            glassBlockOffset = glassBlock.getRelative(offset.getX(),
                                                      offset.getY(),
                                                      offset.getZ());
            if (isBreakableBlock(glassBlockOffset))
                storeAndBreakBlock(glassBlockOffset, false);
        }
    }
	
	public void chunckBreak(final Block glassBlock) throws IOException
    {
        this.storeAndBreakBlock(glassBlock, false);
		this.checkFormation(glassBlock, formations.getConstantCheck());
        this.checkFormation(glassBlock, formations.getRandomCheck());
	}
	
	public int restoreGlass() throws FileNotFoundException, IOException
    {
        final BufferedReader reader = new BufferedReader(new FileReader(blockBuffer));
        final String buffer = reader.readLine();
        reader.close();
        
        if (buffer == null)
            return 0;
        
        final String[] blocks = buffer.split("#");
        int blockCount = 0;
        
        for (String block : blocks) {
            serialize.deserializeSet(block);
            ++blockCount;
        }
        
        final FileWriter blockWriter = new FileWriter(blockBuffer);
        blockWriter.write("");
        blockWriter.close();
        return blockCount;
    }
    
    public int restoreLights(final Player sender) throws FileNotFoundException, IOException
    {
        final HashMap<Chunk, LinkedList<String>> hash = new HashMap<>();
        final String[] serializedBlocks;
        final Location originalLocation = sender.getLocation();
        final BufferedReader reader = new BufferedReader(new FileReader(lightBuffer));
        final String buffer = reader.readLine();
        reader.close();

        if (buffer == null)
            return 0;
        
        serializedBlocks = buffer.split("#");
        int blockCount = 0;
        tickDelay = 0;
        Chunk tempChunk;

        for (String block : serializedBlocks) {
            tempChunk = serialize.deserializeGetChunk(block);
            if (hash.get(tempChunk) == null)
                hash.put(tempChunk, new LinkedList<String>());
            
            hash.get(serialize.deserializeGetChunk(block)).add(block);
            ++blockCount;
        }
        
        for (final Map.Entry<Chunk, LinkedList<String>> entry: hash.entrySet())
        {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
            {
                @Override
                public void run()
                {
                    // Make sure sender is online to actually teleport and restore
                    if (!sender.isOnline())
                        return;
                    
                    sender.teleport(serialize.deserializeGetLocation(entry.getValue().getFirst()));
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            while (!entry.getValue().isEmpty())
                                serialize.deserializeSet(entry.getValue().removeFirst());
                        }
                    }, 20);
                }
            }, tickDelay);
            tickDelay+=40;
        }
        
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                sender.teleport(originalLocation);
            }
        }, tickDelay);
        
        // Make sure sender is online to remove buffer to assure blocks have
        // been properly restored.
        if (sender.isOnline())
        {
            final FileWriter blockWriter = new FileWriter(lightBuffer);
            blockWriter.write("");
            blockWriter.close();
        }
        return blockCount;
    }
}
