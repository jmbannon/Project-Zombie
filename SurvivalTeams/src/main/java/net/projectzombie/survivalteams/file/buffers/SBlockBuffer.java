package net.projectzombie.survivalteams.file.buffers;

import net.projectzombie.survivalteams.block.SurvivalBlock;
import net.projectzombie.survivalteams.file.FileRead;
import net.projectzombie.survivalteams.file.FileWrite;
import net.projectzombie.survivalteams.file.WorldCoordinate;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by maxgr on 7/18/2016.
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

    public static Set<Material> getPlaceableBlocks()
    {
        return defaultBlocks.keySet();
    }

    public static int getBuildRadius()
    {
        return buildRadius;
    }

    public static SurvivalBlock getDefault(Material material) {
        return defaultBlocks.get(material);
    }

    public static void readInDefaults()
    {
        buildRadius = FileRead.getBuildRadius();
        breakNaturally = FileRead.getBreakNaturally();
        attackDelay = FileRead.getAttackDelay();
        tool = FileRead.getSBTool();

        Set<String> blocks = FileRead.getDefaultSBlocks();

        if (defaultBlocks == null)
        {
            defaultBlocks = new HashMap<Material, SurvivalBlock>();
        }

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

    public static SurvivalBlock getSB(Location loc) {
        return teamBlocks.get(loc);
    }

    public static void add(SurvivalBlock sB, Location loc)
    {
        teamBlocks.put(loc, sB);
    }

    public static void remove(Location loc)
    {
        teamBlocks.remove(loc);
    }

    public static void removeTeamBlocks(String teamName)
    {
        Map<Location, SurvivalBlock> tempTeamBlocks = new HashMap<>();
        for (Location loc : teamBlocks.keySet())
        {
            if (teamBlocks.get(loc).getTeamName().equals(teamName))
            {
                FileWrite.writeSBlock(teamBlocks.get(loc).getID(), null);
                loc.getBlock().setType(Material.AIR);
            }
            else
                tempTeamBlocks.put(loc, teamBlocks.get(loc));
        }
        teamBlocks = tempTeamBlocks;
    }

    public static void saveSBlocks()
    {
        for (SurvivalBlock sB : teamBlocks.values())
            sB.saveSBlock();
    }
}
