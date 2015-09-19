/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.team;

import java.util.ArrayList;
import net.projectzombie.survivalteams.controller.TeamController;
import net.projectzombie.survivalteams.player.TeamPlayer;
import org.bukkit.Location;

/**
 *
 * @author jb
 */
public class Team
{
    private final String teamName;
    private final ArrayList<TeamPlayer> teamPlayers;
    private final Location teamSpawn;
    
    public Team(final String teamName,
                final ArrayList<TeamPlayer> teamPlayers,
                final Location teamSpawn)
    {
        this.teamName = teamName;
        this.teamPlayers = teamPlayers;
        this.teamSpawn = teamSpawn;
    }
    
    public String                getTeamName()      { return teamName; }
    public ArrayList<TeamPlayer> getTeamPlayers()   { return teamPlayers; }
    public Location              getSpawnLocation() { return teamSpawn; }
    
    public ArrayList<TeamPlayer> getLeaders()
    {
        final ArrayList<TeamPlayer> leaders = new ArrayList<>();
        int highestRank = -1;
        int playerRank;
        
        for (TeamPlayer player : teamPlayers)
        {
            playerRank = player.getRank();
            if (playerRank > highestRank)
                highestRank = playerRank;
        }
        
        for (TeamPlayer player : teamPlayers)
        {
            playerRank = player.getRank();
            if (playerRank == highestRank)
                leaders.add(player);
        }
        
        return leaders;
    }
    
    public boolean removePlayer(final TeamPlayer player)
    {
        return false;
    }
    
}
