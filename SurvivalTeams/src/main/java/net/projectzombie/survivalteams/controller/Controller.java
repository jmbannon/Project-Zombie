/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.controller;

import java.util.ArrayList;
import java.util.HashMap;
import net.projectzombie.survivalteams.player.TeamPlayer;
import net.projectzombie.survivalteams.team.Team;
import net.projectzombie.survivalteams.team.TeamRank;

/**
 *
 * @author jb
 */
public class Controller
{
    private static final HashMap<String, Team> teams = new HashMap<>();
    
    private Controller() { /* Do nothing */ }
    
    static public boolean addPlayerToTeam(final Team team,
                                          final TeamPlayer player,
                                          final TeamRank rank)
    {
        if (teamOnline(team) && TeamFiles.writePlayerToTeam(team, player, rank))
        {
            team.addPlayer(player);
            player.setTeam(team);
            player.setRank(rank);
            return true;
        } else
            return false;
    }
    
    static public boolean removePlayerFromTeam(final Team team,
                                               final TeamPlayer player)
    {
        if (teamOnline(team) && TeamFiles.removePlayerFromTeam(team, player))
        {
            team.removePlayer(player);
            player.clearTeam();
            return true;
        } else
            return false;
    }
    
    static public boolean createNewTeam(final TeamPlayer player,
                                        final String teamName)
    {
        final Team team = new Team(teamName, player, new ArrayList<TeamPlayer>(), null);
        
        if (TeamFiles.writePlayerToTeam(team, player, player.getRank()))
        {
            player.setRank(TeamRank.LEADER);
            player.setTeam(team);
            team.addPlayer(player);
            teams.put(team.toFileName(), team);
            return true;
        }
        else
            return false;
    }
    
    static public boolean invitePlayerToTeam(final TeamPlayer sender,
                                             final TeamPlayer reciever,
                                             final TeamRank rankOffered)
    {
        return true;
    }
    
    static public boolean disbandTeam(final Team team)
    {
        if (teamOnline(team) && TeamFiles.teamExists(team.toFileName()))
        {
            team.clearTeam();
            teams.remove(team.toFileName());
            return TeamFiles.removeTeam(team);
        } else
            return false;
    }
    
    static public Team getExistingTeam(final String teamName)
    {
        if (!teams.containsKey(teamName))
        {
            
        }
        return teams.get(teamName);
    }
    
    static public boolean teamOnline(final Team team)
    {
        return teams.containsKey(team.toFileName());
    }
}
