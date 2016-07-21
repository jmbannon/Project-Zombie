/*
 * SurvivalTeams
 *
 * Version:     0.5
 * MC Build:    1.8.3
 * Date:        09-22-2015
 *
 * Author:      Jesse Bannon
 * Email:       jbann1994@gmail.com
 * Server:      Project Zombie
 * Website:     www.projectzombie.net
 *
 * Allows players to create rank-based Teams. Includes features such as no
 * team PVP and a group spawn.
 *
*/
package net.projectzombie.survivalteams.file;

import java.util.UUID;
import net.projectzombie.survivalteams.player.TeamPlayer;
import net.projectzombie.survivalteams.team.Team;
import org.bukkit.Location;
import org.bukkit.Material;

/**
 *
 * @author jb
 */
public class FilePath
{
    private static final String ROOT_PATH      = "teams";
    private static final String ROOT_BLOCKS    = "blocks";
    
    private FilePath() { /* Do nothing */ }

    static protected String sBWeaponDurability(Material material)
    {
        return sBWeapon(material) + ".durability";
    }

    static protected String sBWeapons()
    {
        return ROOT_BLOCKS + ".weapons";
    }

    static protected String sBWeapon(Material material)
    {
        return sBWeapons() + "." + material.toString();
    }

    static protected String sBWeaponDamage(Material material)
    {
        return sBWeapon(material) + ".health";
    }

    static protected String sBCheckTool()
    {
        return ROOT_BLOCKS + ".tool";
    }

    static protected String buildRadius()
    {
        return ROOT_BLOCKS + ".build-R";
    }

    static protected String sBDefaultDamage()
    {
        return ROOT_BLOCKS + ".damage-D";
    }

    static protected String sBDefaultDurability()
    {
        return ROOT_BLOCKS + ".durability-D";
    }

    static protected String breakNaturally()
    {
        return ROOT_BLOCKS + ".break-N";
    }

    static protected String attackDelay()
    {
        return ROOT_BLOCKS + ".delay";
    }

    static protected String defaultBlocks()
    {
        return ROOT_BLOCKS + ".defaults";
    }

    static protected String defaultBlock(String material)
    {
        return defaultBlocks() + "." + material;
    }

    static protected String defaultBlockHealth(String material)
    {
        return defaultBlock(material) + ".health";
    }

    static protected String rootTeamBlocks()
    {
        return ROOT_BLOCKS + ".team";
    }

    static protected String teamBlock(String teamName, Location loc)
    {
        return teamBlock(WorldCoordinate.toStringLocID(teamName, loc.getBlock()));
    }

    static protected String teamBlock(String ID)
    {
        return rootTeamBlocks() + "." + ID;
    }

    static protected String teamBlockHealth(String ID)
    {
        return teamBlock(ID) + ".health";
    }

    static protected String teamBlock(Team team, Location loc)
    {
        return teamBlock(team.getName(), loc);
    }

    static protected String teamBlockHealth(String teamName, Location loc)
    {
        return teamBlock(teamName, loc) + ".health";
    }

    static protected String teamBlockHealth(Team team, Location loc)
    {
        return teamBlockHealth(team.getName(), loc);
    }

    static protected String teams()
    {
        return ROOT_PATH;
    }
    
    static protected String team(final String teamName)
    {
        return ROOT_PATH + "." + teamName;
    }
    
    static protected String team(final Team team)
    {
        return ROOT_PATH + "." + team.getName();
    }

    static protected String member(final Team team,
                                   final TeamPlayer player)
    {
        return members(team.getName()) + "." + player.getUUID().toString();
    }
    
    static protected String member(final Team team,
                                   final UUID uuid)
    {
        return members(team.getName()) + "." + uuid.toString();
    }
    
    static protected String member(final String teamName,
                                   final UUID uuid)
    {
        return members(teamName) + "." + uuid.toString();
    }
    
    static protected String memberRank(final Team team,
                                       final TeamPlayer player)
    {
        return members(team.getName()) + "." + player.getUUID().toString() + ".rank";
    }
    
    static protected String memberName(final Team team,
                                       final TeamPlayer player)
    {
        return members(team.getName()) + "." + player.getUUID().toString() + ".name";
    }

    static protected String members(final String teamName)
    {
        return team(teamName) + ".members";
    }

    static protected String leader(final String teamName)
    {
        return team(teamName) + ".leader";
    }
    
    static protected String leaderName(final String teamName,
                                       final UUID uuid)
    {
        return team(teamName) + ".leader." + uuid.toString();
    }
    
    static protected String spawn(final String team)
    {
        return team(team) + ".spawn";
    }
    
    static protected String spawn(final Team team)
    {
        return team(team) + ".spawn";
    }
}
