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
package net.projectzombie.survivalteams.controller.file;

import java.util.UUID;
import net.projectzombie.survivalteams.player.TeamPlayer;
import net.projectzombie.survivalteams.team.Team;
import org.bukkit.entity.Player;

/**
 *
 * @author jb
 */
public class FileController
{
    /**
     * To be called when a player logs into the server.
     * @param player
     * @return The player's initialized TeamPlayer object.
     */
    static public TeamPlayer initializePlayer(final Player player)
    {
        final UUID uuid = player.getUniqueId();
        final String teamName;
        final TeamPlayer teamPlayer;

        if ((teamName = TeamFile.getTeamName(uuid)) != null)
        {
            Team team = getTeam(teamName);
            teamPlayer = new TeamPlayer(player, team, TeamFile.getMemberRank(teamName, uuid));
        }
        else
            teamPlayer = new TeamPlayer(player);
        
        TeamFile.ONLINE_PLAYERS.put(uuid, teamPlayer);
        return teamPlayer;
    }
    
    /**
     * Gets the team by name if one of its players are online and the team is
     * within ONLINE_TEAMS.
     * 
     * @param teamName Name of the team.
     * @return  Team of the given name if one of the players is online.
     */
    static private Team getTeam(final String teamName)
    {
        final Team team;
        if (TeamFile.ONLINE_TEAMS.containsKey(teamName))
            team = TeamFile.ONLINE_TEAMS.get(teamName);
        else
            team = new Team(teamName, TeamFile.getLeaderUUID(teamName), TeamFile.getMemberUUIDs(teamName), TeamFile.getSpawn(teamName));
        
        TeamFile.ONLINE_TEAMS.put(teamName, team);
        return team;
    }

}
