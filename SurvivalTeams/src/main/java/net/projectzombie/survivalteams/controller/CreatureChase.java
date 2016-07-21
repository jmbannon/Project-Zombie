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
 * Zombie block hitting scheduler, if a zombie is chasing a person, it will check if a block
 *  is in between them and try to break it.
 */
public class CreatureChase implements Runnable
{
    // Magic number, can be changed, just allows a valid loc.
    public static final int ZOMBIE_Hit_RANGE = 15;

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
     * Checks if a zombie is targeting a player, if so keeps a delayed hit event and tries to
     *  damage the blocks at eye level, above head, and at the zombie's feet.
     */
    @Override
    public void run()
    {
        if (zombie.getTarget() instanceof Player && !zombie.isDead())
        {
            scheduler.scheduleSyncDelayedTask(plugin, this, SBlockBuffer.getAttackDelay());
            Set<Material> transparentB = null;

            // Lower block, at zombie's feet.
            Block main = zombie.getLocation().getBlock().getRelative(BlockFace.UP, 1);
            Block look = zombie.getTargetBlock(transparentB, ZOMBIE_Hit_RANGE);
            isBreakableBlock(main, main.getX() - look.getX(), main.getZ() - look.getZ());

            // Block at eye level.
            Block main2 = zombie.getLocation().getBlock();
            Block look2 = zombie.getTargetBlock(transparentB, ZOMBIE_Hit_RANGE).getRelative(BlockFace.DOWN, 1);
            isBreakableBlock(main2, main2.getX() - look2.getX(), main2.getZ() - look2.getZ());

            // Block above head.
            Block main3 = zombie.getLocation().getBlock().getRelative(BlockFace.UP, 2);
            Block look3 = zombie.getTargetBlock(transparentB, ZOMBIE_Hit_RANGE).getRelative(BlockFace.UP, 1);
            isBreakableBlock(main3, main3.getX() - look3.getX(), main3.getZ() - look3.getZ());
        }
    }

    /**
     * The GPS part of the block break, it finds the zombie's direction.
     * @param main = Reference block, the one at zombie's body.
     * @param x = Determines direction of E or W if not 0.
     * @param z = Determines direction of N or S if not 0.
     */
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