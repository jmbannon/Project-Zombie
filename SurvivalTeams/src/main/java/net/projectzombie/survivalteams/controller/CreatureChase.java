package net.projectzombie.survivalteams.controller;

import net.projectzombie.survivalteams.block.SurvivalBlock;
import net.projectzombie.survivalteams.file.buffers.SBlockBuffer;
import org.bukkit.Bukkit;
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
    public static final int ZOMBIE_Hit_RANGE = 15; // Same as player

    private Zombie zombie;
    private BukkitScheduler scheduler;
    private JavaPlugin plugin;

    /**
     * Basic constructor to get things needed to track reoccurring zombie block breaks.
     * @param zombie = The zombie chasing a player.
     * @param scheduler = Thing to schedule new block break event.
     * @param plugin = Needed for scheduling new events.
     */
    public CreatureChase(Zombie zombie, BukkitScheduler scheduler, JavaPlugin plugin)
    {
        this.zombie = zombie;
        this.scheduler = scheduler;
        this.plugin = plugin;
    }

    /**
     * If a zombie
     */
    @Override
    public void run()
    {
        if (zombie.getTarget() instanceof Player && !zombie.isDead())
        {
            scheduler.scheduleSyncDelayedTask(plugin, this, SBlockBuffer.getAttackDelay());
            Set<Material> transparentB = null;

            // TODO change to be the blocks infront of zombie
            // could use its location and just see if those are blocks.
            // or just add all.

            Block main = zombie.getLocation().getBlock().getRelative(BlockFace.UP, 1);
            Block look = zombie.getTargetBlock(transparentB, ZOMBIE_Hit_RANGE);

            Block main2 = zombie.getLocation().getBlock();
            Block look2 = zombie.getTargetBlock(transparentB, ZOMBIE_Hit_RANGE).getRelative(BlockFace.DOWN, 1);

            Block main3 = zombie.getLocation().getBlock().getRelative(BlockFace.UP, 2);
            Block look3 = zombie.getTargetBlock(transparentB, ZOMBIE_Hit_RANGE).getRelative(BlockFace.UP, 1);

            isBreakableBlock(main, main.getX() - look.getX(), main.getZ() - look.getZ());
            isBreakableBlock(main2, main2.getX() - look2.getX(), main2.getZ() - look2.getZ());
            isBreakableBlock(main3, main3.getX() - look3.getX(), main3.getZ() - look3.getZ());
        }
    }

    public void isBreakableBlock(Block main, int x, int z)
    {
        BlockFace mainDirection = null;
        if (x != 0)
        {
            if (x > 0)
                mainDirection = BlockFace.WEST;
            else
                mainDirection = BlockFace.EAST;
        }
        else if (z != 0)
        {
            if (z > 0)
                mainDirection = BlockFace.NORTH;
            else
                mainDirection = BlockFace.SOUTH;
        }

        if (mainDirection != null)
        {
            Block middle = main.getRelative(mainDirection, 1);

            SurvivalBlock middleSB = SBlockBuffer.getSB(middle.getLocation());
            if (middleSB != null)
            {
                middleSB.takeDamage(zombie.getEquipment());
            }
        }
    }
}