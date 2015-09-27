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
package net.projectzombie.survivalteams.team;

import java.util.ArrayList;
import java.util.UUID;
import net.projectzombie.survivalteams.controller.file.FilePath;
import net.projectzombie.survivalteams.controller.file.TeamFile;
import net.projectzombie.survivalteams.player.TeamPlayer;
import org.bukkit.Location;

/**
 *
 * @author jb
 */
public class Team implements Comparable<Team>
{
    private final String teamName;
    private final UUID leaderUUID;
    private final int teamLimit;
    private final ArrayList<UUID> members;
    private Location teamSpawn;
    
    /**
     * Creates a team from file/database. 
     * @param teamName
     * @param leaderUUID
     * @param members
     * @param teamSpawn 
     */
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
            if ((player = TeamFile.getPlayer(uuid)) != null)
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
            members.remove(player.getUUID());
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
         return FilePath.getTeamPath(teamName);
    }

    @Override
    public int compareTo(final Team o)
    {
        return teamName.compareTo(o.teamName);
    }
    
}
