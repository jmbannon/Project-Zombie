package net.projectzombie.survivalteams.controller;

import net.projectzombie.survivalteams.block.TeamBlock;
import net.projectzombie.survivalteams.file.buffers.BlockBuffer;
import net.projectzombie.survivalteams.main.PluginHelpers;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Zombie block hitting scheduler, if a monster is chasing a person, it will check if a block
 *  is in between them and try to break it.
 */
public class CreatureChase implements Runnable
{
    // Magic number, can be changed, just allows a valid loc.
    public static final int ZOMBIE_Hit_RANGE = 15;

    private Monster monster;

    /**
     * Basic constructor to get things needed to track reoccurring monster block breaks.
     * @param monster = The monster chasing a player.
     */
    public CreatureChase(Monster monster)
    {
        this.monster = monster;
    }

    /**
     * Checks if a monster is targeting a player, if so keeps a delayed hit event and tries to
     *  damage the blocks at eye level, above head, and at the monster's feet.
     */
    @Override
    public void run()
    {
        if (monster.getTarget() instanceof Player && !monster.isDead())
        {
            PluginHelpers.getScheduler().scheduleSyncDelayedTask(
                PluginHelpers.getPlugin(), this, BlockBuffer.getAttackDelay());

            Set<Material> transparentB = null;

            // Lower block, at monster's feet.
            Block main = monster.getLocation().getBlock().getRelative(BlockFace.UP, 1);
            Block look = monster.getTargetBlock(transparentB, ZOMBIE_Hit_RANGE);
            breakBlock(main, main.getX() - look.getX(), main.getZ() - look.getZ());

            // Block at eye level.
            Block main2 = monster.getLocation().getBlock();
            Block look2 = monster.getTargetBlock(transparentB, ZOMBIE_Hit_RANGE).getRelative(BlockFace.DOWN, 1);
            breakBlock(main2, main2.getX() - look2.getX(), main2.getZ() - look2.getZ());

            // Block above head.
            Block main3 = monster.getLocation().getBlock().getRelative(BlockFace.UP, 2);
            Block look3 = monster.getTargetBlock(transparentB, ZOMBIE_Hit_RANGE).getRelative(BlockFace.UP, 1);
            breakBlock(main3, main3.getX() - look3.getX(), main3.getZ() - look3.getZ());
        }
    }

    /**
     * Block hit, it the block is there and not air.
     * @param main = Reference block, the one at monster's body.
     * @param x = Determines direction of E or W if not 0.
     * @param z = Determines direction of N or S if not 0.
     */
    public void breakBlock(Block main, int x, int z)
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

            TeamBlock middleTB = BlockBuffer.getTeamBlock(middle.getLocation());
            if (middleTB != null)
            {
                middleTB.takeHit(monster.getEquipment());
            }
        }
    }
}