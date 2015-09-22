/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.team;

import java.util.ArrayList;
import net.projectzombie.survivalteams.controller.TeamFile;
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
    private final int teamLimit;
    private final ArrayList<TeamPlayer> members;
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
        this.teamLimit = 10;
        
    }
    
    public Team(final String teamName,
                final TeamPlayer leader)
    {
        this(teamName, leader, newTeamMemberList(leader), null);
    }
    
    public String                getName()      { return teamName; }
    public TeamPlayer            getLeader()    { return this.leader; }
    public ArrayList<TeamPlayer> getPlayers()   { return members; }
    public Location              getSpawn()     { return teamSpawn; }
    public boolean               canAdd()       { return members.size() < teamLimit; }
    
    public boolean addPlayer(final TeamPlayer player)
    {
        final boolean fileWriteCheck = TeamFile.writePlayerToTeam(this, player, player.getRank());
        if (fileWriteCheck)
            members.add(player);
        return fileWriteCheck;
    }
    public boolean removePlayer(final TeamPlayer player)
    {
        final boolean fileWriteCheck = TeamFile.removePlayerFromTeam(this, player);
        if (fileWriteCheck)
            members.remove(player);
        return fileWriteCheck;
    }
    
    public void setSpawn(final Location location)     { this.teamSpawn = location; }
    
    public String toFileName()
    {
        return this.teamName.trim();
    }
    
    private static ArrayList<TeamPlayer> newTeamMemberList(final TeamPlayer leader)
    {
        final ArrayList<TeamPlayer> newTeam = new ArrayList<>();
        newTeam.add(leader);
        return newTeam;
    }
    
}
