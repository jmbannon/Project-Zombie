package net.projectzombie.survivalteams.controller;

import net.projectzombie.survivalteams.block.SurvivalBlock;
import net.projectzombie.survivalteams.file.buffers.SBlockBuffer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Set;

/**
 * Created by maxgr on 7/19/2016.
 */
public class CreatureChase implements Runnable
{
    public static final int ZOMBIE_Hit_RANGE = 4; // Same as player

    private Zombie zombie;
    private BukkitScheduler scheduler;
    private JavaPlugin plugin;

    public CreatureChase(Zombie zombie, BukkitScheduler scheduler, JavaPlugin plugin)
    {
        this.zombie = zombie;
        this.scheduler = scheduler;
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        if (zombie.getTarget() instanceof Player && !zombie.isDead())
        {
            scheduler.scheduleSyncDelayedTask(plugin, this, SBlockBuffer.getAttackDelay());
            Set< Material > transparentB = null;

            // TODO change to be the blocks infront of zombie
            // could use its location and just see if those are blocks.
            // or just add all.
            Block lower = zombie.getTargetBlock(transparentB, ZOMBIE_Hit_RANGE);
            Block upper = lower.getRelative(BlockFace.UP, 1); // One above

            SurvivalBlock lowerBlock = SBlockBuffer.getSB(lower.getLocation());
            if (lowerBlock != null)
            {
                lowerBlock.takeDamage(zombie.getEquipment());
            }

            SurvivalBlock upperBlock = SBlockBuffer.getSB(upper.getLocation());
            if (upperBlock != null)
            {
                upperBlock.takeDamage(zombie.getEquipment());
            }
        }
    }


}
