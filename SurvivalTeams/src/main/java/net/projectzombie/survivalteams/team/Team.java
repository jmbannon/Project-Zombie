/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.team;

import java.util.ArrayList;
import net.projectzombie.survivalteams.player.TeamPlayer;
import org.bukkit.Location;

/**
 *
 * @author jb
 */
public class Team
{
    private final String teamName;
    private final TeamPlayer leader;
    private ArrayList<TeamPlayer> members;
    private Location teamSpawn;
    
    public Team(final String teamName,
                final TeamPlayer leader,
                final ArrayList<TeamPlayer> members,
                final Location teamSpawn)
    {
        this.teamName = teamName;
        this.leader = leader;
        this.members = members;
        this.teamSpawn = teamSpawn;
    }
    
    public String                getName()      { return teamName; }
    public TeamPlayer            getLeader()    { return this.leader; }
    public ArrayList<TeamPlayer> getPlayers()   { return members; }
    public Location              getSpawn()     { return teamSpawn; }
    
    public boolean addPlayer(final TeamPlayer player)    { return members.add(player); }
    public boolean removePlayer(final TeamPlayer player) { return members.remove(player); }
    public void setSpawn(final Location location)     { this.teamSpawn = location; }
    
    /**
     * Nulls the team variables to be garbage collected.
     */
    public void clearTeam()
    { 
        for (TeamPlayer player : members)
            player.clearTeam();
        
        this.members = null;
        this.teamSpawn = null;
    }
    

    
    public String toFileName()
    {
        return this.teamName.trim();
    }
}
