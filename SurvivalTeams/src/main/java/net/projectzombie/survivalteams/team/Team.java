/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.team;

import java.util.ArrayList;
import java.util.UUID;
import net.projectzombie.survivalteams.controller.file.Paths;
import net.projectzombie.survivalteams.controller.file.TeamFile;
import net.projectzombie.survivalteams.player.TeamPlayer;
import org.bukkit.Location;

/**
 *
 * @author jb
 */
public class Team
{
    private final String teamName;
    private final UUID leaderUUID;
    private final int teamLimit;
    private final ArrayList<UUID> members;
    private Location teamSpawn;
    
    public Team(final String teamName,
                final UUID leaderUUID,
                final ArrayList<UUID> members,
                final Location teamSpawn)
    {
        this.teamName = teamName;
        this.leaderUUID = leaderUUID;
        this.members = members;
        this.teamSpawn = teamSpawn;
        this.teamLimit = 10;
        
    }
    
    public Team(final String teamName,
                final UUID   leaderUUID)
    {
        this(teamName, leaderUUID, newTeamMemberList(leaderUUID), null);
    }
    
    public String                getName()        { return teamName; }
    public UUID                  getLeaderUUID()  { return this.leaderUUID; }
    public ArrayList<UUID>       getMemberUUIDs() { return members; }
    public Location              getSpawn()       { return teamSpawn; }
    public boolean               canAdd()         { return members.size() < teamLimit; }
    
    public ArrayList<TeamPlayer> getOnlinePlayers()
    {
        final ArrayList<TeamPlayer> playersOnline = new ArrayList<>();
        TeamPlayer player;
        
        for (UUID uuid : members)
            if ((player = TeamFile.getOnlineTeamPlayer(uuid)) != null)
                playersOnline.add(player);
        
        return playersOnline;
    }
    
    public boolean addPlayer(final TeamPlayer player)
    {
        final boolean fileWriteCheck = TeamFile.writePlayerToTeam(this, player, player.getRank());
        if (fileWriteCheck)
            members.add(player.getUUID());
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
    
    private static ArrayList<UUID> newTeamMemberList(final UUID leaderUUID)
    {
        final ArrayList<UUID> newTeam = new ArrayList<>();
        newTeam.add(leaderUUID);
        return newTeam;
    }
    
    public String getPath()
    {
         return Paths.getTeamPath(teamName);
    }
    
}
