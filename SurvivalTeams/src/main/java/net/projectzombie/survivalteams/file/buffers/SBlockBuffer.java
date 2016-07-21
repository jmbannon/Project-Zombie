package net.projectzombie.survivalteams.file.buffers;

import net.projectzombie.survivalteams.block.SurvivalBlock;
import net.projectzombie.survivalteams.file.FileRead;
import net.projectzombie.survivalteams.file.FileWrite;
import net.projectzombie.survivalteams.file.WorldCoordinate;
import net.projectzombie.survivalteams.team.Team;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Buffer to hold default types and all SBs placed.
 */
public class SBlockBuffer
{

    private static Map<Material, SurvivalBlock> defaultBlocks;
    private static Map<Location, SurvivalBlock> teamBlocks;
    private static int buildRadius;
    private static boolean breakNaturally;
    private static int attackDelay;
    private static Material tool;

    public static boolean isBreakNaturally()
    {
        return breakNaturally;
    }

    public static Material getTool()
    {
        return tool;
    }

    public static int getAttackDelay()
    {
        return attackDelay;
    }

    public static void removeDefault(Material material)
    {
        defaultBlocks.remove(material);
    }

    /**
     * Gets all types of blocks tracked, so blocks able to be broken and placed.
     * @return
     */
    public static Set<Material> getPlaceableBlocks()
    {
        return defaultBlocks.keySet();
    }

    public static int getBuildRadius()
    {
        return buildRadius;
    }

    /**
     * @param material = Type of block needed from defaults.
     * @return The SB default holding the default SB info. Null if not a saved type.
     */
    public static SurvivalBlock getDefault(Material material) {
        return defaultBlocks.get(material);
    }

    /**
     * Reads in all defaults for blocks and tools.
     */
    public static void readInDefaults()
    {
        buildRadius = FileRead.getBuildRadius();
        breakNaturally = FileRead.getBreakNaturally();
        attackDelay = FileRead.getAttackDelay();
        tool = FileRead.getSBTool();

        // Read in default blocks.
        Set<String> blocks = FileRead.getDefaultSBlocks();
        if (defaultBlocks == null)
            defaultBlocks = new HashMap<Material, SurvivalBlock>();
        if (blocks != null)
        {
            for (String block : blocks)
            {
                int health = FileRead.getDefaultSBlockHealth(block);
                defaultBlocks.put(Material.valueOf(block), new SurvivalBlock(health));
            }
        }
    }

    /**
     * Use readInDefaults before using this method. It reads in already placed blocks.
     */
    public static void readInPlacedSBlocks()
    {
        Set<String> blocks = FileRead.getTeamSBlocks();

        if (teamBlocks == null)
        {
            teamBlocks = new HashMap<Location, SurvivalBlock>();
        }

        if (blocks != null)
        {
            for (String iD : blocks)
            {
                int health = FileRead.getTeamSBlockHealth(iD);
                String teamName = WorldCoordinate.toTeamName(iD);
                Location loc = WorldCoordinate.toLocation(iD);
                SurvivalBlock sB = new SurvivalBlock(health, teamName, loc);
                teamBlocks.put(loc, sB);
            }
        }
    }

    /**
     * Receives a SB from the buffer.
     * @param loc = Location of SB.
     * @return = SB at location, null if not found.
     */
    public static SurvivalBlock getSB(Location loc) {
        return teamBlocks.get(loc);
    }

    /**
     * SB to be added by its location.
     * @param sB = Sb to be added.
     * @param loc = Location to find it in buffer.
     */
    public static void add(SurvivalBlock sB, Location loc)
    {
        teamBlocks.put(loc, sB);
    }

    /**
     * @param loc = SB to be removed from buffer.
     */
    public static void remove(Location loc)
    {
        teamBlocks.remove(loc);
    }

    /**
     * Remove blocks belonging to a team.
     * @param teamName = Team that owns the blocks.
     */
    public static void removeTeamBlocks(String teamName)
    {
        Map<Location, SurvivalBlock> tempTeamBlocks = new HashMap<>();
        for (Location loc : teamBlocks.keySet())
        {
            if (teamBlocks.get(loc).getTeamName().equals(teamName))
            {
                removeSB(loc);
            }
            else
                tempTeamBlocks.put(loc, teamBlocks.get(loc));
        }
        teamBlocks = tempTeamBlocks;
    }

    /**
     * Removes blocks out of range from the base.
     * @param teamName = Team that owns the blocks.
     */
    public static void removeTeamBlocksFar(String teamName)
    {
        Team team = TeamBuffer.get(teamName);
        Location spawn = team.getSpawn();
        Map<Location, SurvivalBlock> tempTeamBlocks = new HashMap<>();
        for (Location loc : teamBlocks.keySet())
        {
            if (teamBlocks.get(loc).getTeamName().equals(teamName) && spawn != null &&
                Math.abs(spawn.distance(loc)) > SBlockBuffer.getBuildRadius())
            {
                removeSB(loc);
            }
            else
                tempTeamBlocks.put(loc, teamBlocks.get(loc));
        }
        teamBlocks = tempTeamBlocks;
    }

    /**
     * Wipes all disc saved blocks, and saves what is in memory.
     */
    public static void saveSBlocks()
    {
        FileWrite.wipeSBlocks(); // Wipe before save of all blocks in memory.
        for (SurvivalBlock sB : teamBlocks.values())
            if (sB.getLocation().getBlock().getType() != Material.AIR)
                sB.saveSBlock();
    }

    /**
     * Removes all blocks from world and buffer.
     */
    public static void clearBuffer()
    {
        for (Location loc : teamBlocks.keySet())
        {
            removeSB(loc);
        }

        teamBlocks.clear();
    }

    /**
     * Removes the SB.
     * @param loc = SB's location.
     */
    public static void removeSB(Location loc)
    {

        FileWrite.wipeSBlock(teamBlocks.get(loc).getID());
        loc.getBlock().setType(Material.AIR);
    }
}
