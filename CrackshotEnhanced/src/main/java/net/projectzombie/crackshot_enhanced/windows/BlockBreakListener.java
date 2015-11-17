package net.projectzombie.crackshot_enhanced.windows;

import net.projectzombie.crackshot_enhanced.windows.GlassFormations.GlassOffset;
import org.bukkit.event.Listener;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import com.shampaggon.crackshot.events.WeaponHitBlockEvent;
import net.projectzombie.block_buffer.buffer.Buffer;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

public class BlockBreakListener implements Listener
{

    private static GlassFormations formations;

    public BlockBreakListener()
    {
        BlockBreakListener.formations = new GlassFormations();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void meleeBlockBreakEvent(BlockBreakEvent event)
    {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();

        if (player.getGameMode() == GameMode.SURVIVAL)
        {
            if (isBreakableBlock(block))
            {
                chunckBreak(block);
                if (player.getItemInHand().getType() == Material.AIR)
                {
                    player.damage(0.5);
                }
            } else if (isBreakableLight(block))
            {
                storeAndBreakBlock(block);
                if (block.getType() != Material.TORCH
                        && player.getItemInHand().getType() == Material.AIR)
                {
                    player.damage(0.5);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void projectileBlockBreakEvent(WeaponHitBlockEvent event)
    {
        final Block block = event.getBlock();
        
        if (isBreakableBlock(block))
        {
            chunckBreak(block);
        }
        else if (isBreakableLight(block))
        {
            storeAndBreakBlock(block);
        } 
        else if (isTorch(block))
        {
            block.setType(Material.AIR);
            block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.GLASS.getId());
        }
        else if (!block.getType().equals(Material.SNOW))
        {
            block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getTypeId());
        }
    }

    public void storeAndBreakBlock(final Block theBlock)
    {
        Buffer.writeToBrokeBuffer(theBlock);
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
            final GlassOffset[] offsets) {
        Block glassBlockOffset;
        for (GlassOffset offset : offsets) {
            glassBlockOffset = glassBlock.getRelative(offset.getX(),
                    offset.getY(),
                    offset.getZ());
            if (isBreakableBlock(glassBlockOffset)) {
                storeAndBreakBlock(glassBlockOffset);
            }
        }
    }

    public void chunckBreak(final Block glassBlock)
    {
        this.storeAndBreakBlock(glassBlock);
        this.checkFormation(glassBlock, formations.getConstantCheck());
        this.checkFormation(glassBlock, formations.getRandomCheck());
    }
}
