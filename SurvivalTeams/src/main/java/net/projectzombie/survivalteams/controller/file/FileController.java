/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
