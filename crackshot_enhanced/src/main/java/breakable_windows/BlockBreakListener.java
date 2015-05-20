package breakable_windows;

import breakable_windows.GlassFormations.GlassOffset;
import org.bukkit.event.Listener;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import org.bukkit.entity.Player;

public class BlockBreakListener implements Listener
{
	private final Plugin plugin;
    private static GlassFormations formations;
    
    private final File blockBuffer;
	
	public BlockBreakListener(final Plugin plugin) throws IOException
    {
		this.plugin = plugin;
        BlockBreakListener.formations = new GlassFormations();
        
        if (plugin.getDataFolder() == null || !plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        
        blockBuffer = new File(this.plugin.getDataFolder(), "block_buffer");
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void meleeBlockBreakEvent(BlockBreakEvent event) throws IOException
    {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        
		if (player.getGameMode() == GameMode.SURVIVAL) {
			if (isGlass(block)) {
				chunckBreak(block);
				if (player.getItemInHand().getType() == Material.AIR)
					player.damage(0.5);
			}
			else if (isLight(block)) {
				block.getWorld().playSound(block.getLocation(), Sound.GLASS, 1, 1);
				storeAndBreakBlock(block);				
			}
		}
	}
		
	@EventHandler(priority = EventPriority.NORMAL)
	public void projectileBlockBreakEvent(WeaponHitBlockEvent event) throws IOException
    {
        final Block block = event.getBlock();
        
		if (isGlass(block)) {
			chunckBreak(block);
			block.getWorld().playSound(block.getLocation(), Sound.GLASS, 1, 1);
		}
		else if (isLight(block)) {
			storeAndBreakBlock(block);
			block.getWorld().playSound(block.getLocation(), Sound.GLASS, 1, 1);
		}
	}

	public void storeAndBreakBlock(final Block theBlock) throws IOException
    {
        final FileWriter blockWriter = new FileWriter(blockBuffer, true);
        blockWriter.append(BlockSerialize.serialize(theBlock));
        blockWriter.close();
		theBlock.setType(Material.AIR); 
	}
	
	public boolean isGlass(final Block glassBlock)
    {
		return (glassBlock.getType() == Material.GLASS
				|| glassBlock.getType() == Material.THIN_GLASS
				|| glassBlock.getType() == Material.STAINED_GLASS
				|| glassBlock.getType() == Material.STAINED_GLASS_PANE);
	}
	
	public boolean isLight(final Block lightBlock)
    {
		return (lightBlock.getType() == Material.GLOWSTONE
				|| lightBlock.getType() == Material.REDSTONE_LAMP_ON
				|| lightBlock.getType() == Material.REDSTONE_LAMP_OFF
				|| lightBlock.getType() == Material.BEACON
				|| lightBlock.getType() == Material.TORCH);
	}
	
	public void checkToStoreGlass(final Block glassBlock) throws IOException
    {
		if (this.isGlass(glassBlock)) 
			this.storeAndBreakBlock(glassBlock);
	}
	
    public void checkBlocks(final Block glassBlock,
                            final GlassOffset[] offsets) throws IOException
    {
        for (GlassOffset offset : offsets)
            this.checkToStoreGlass(glassBlock.getRelative(offset.getX(),
                                                          offset.getY(),
                                                          offset.getZ()));
    }
	
	public void chunckBreak(final Block glassBlock) throws IOException
    {
        this.storeAndBreakBlock(glassBlock);
		this.checkBlocks(glassBlock, formations.getConstantCheck());
        this.checkBlocks(glassBlock, formations.getRandomCheck());
	}
	
	public int restoreBlocks() throws FileNotFoundException, IOException
    {
        final BufferedReader reader = new BufferedReader(new FileReader(blockBuffer));
        final String buffer = reader.readLine();
        reader.close();
        
        if (buffer == null)
            return 0;
        
        final String[] blocks = buffer.split("#");
        int blockCount = 0;
        
        for (String block : blocks) {
            BlockSerialize.deserializeAndSet(block);
            ++blockCount;
        }
        
        final FileWriter blockWriter = new FileWriter(blockBuffer);
        blockWriter.write("");
        blockWriter.close();
        return blockCount;
    }
}
