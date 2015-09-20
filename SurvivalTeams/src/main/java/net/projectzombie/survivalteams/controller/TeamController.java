/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.controller;

import java.util.HashMap;
import net.projectzombie.survivalteams.player.TeamPlayer;
import net.projectzombie.survivalteams.team.Team;
import net.projectzombie.survivalteams.team.TeamRank;

/**
 *
 * @author jb
 */
public class TeamController
{
    private static HashMap<String, Team> teams = new HashMap<>();
    
    private TeamController() { /* Do nothing */ }
    
    static public boolean addNewPlayerToTeam(final Team team,
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
    
    static public boolean removePlayerFrontTeam(final Team team,
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
    
    static public boolean disbandTeam(final Team team)
    {
        if (teamOnline(team) && TeamFiles.teamExists(team))
        {
            team.clearTeam();
            teams.remove(team.toFileName());
            return TeamFiles.removeTeam(team);
        } else
            return false;
    }
    
    static public boolean teamOnline(final Team team)
    {
        return teams.containsKey(team.toFileName());
    }
}
