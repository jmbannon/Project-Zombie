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
import net.projectzombie.survivalteams.player.TPText;
import net.projectzombie.survivalteams.player.TeamPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

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
        this(teamName, leaderUUID, new ArrayList<UUID>(), null);
    }
    
    public String                getName()        { return teamName; }
    public UUID                  getLeaderUUID()  { return this.leaderUUID; }
    public ArrayList<UUID>       getMemberUUIDs() { return members; }
    public Location              getSpawn()       { return teamSpawn; }
    public boolean               canAdd()         { return members.size() < teamLimit; }
    
    public ArrayList<TeamPlayer> getPlayers()
    {
        final ArrayList<TeamPlayer> playersOnline = new ArrayList<>();
        TeamPlayer player = TeamFile.getPlayer(leaderUUID);
        
        if (player != null)
            playersOnline.add(TeamFile.getPlayer(leaderUUID));
        
        for (UUID uuid : members)
            if ((player = TeamFile.getPlayer(uuid)) != null)
                playersOnline.add(player);
        
        return playersOnline;
    }
    
    public TeamPlayer getLeader()
    {
        return TeamFile.getPlayer(leaderUUID);
    }
    
    public ArrayList<TeamPlayer> getMembers(final TeamRank memberRank)
    {
        final ArrayList<TeamPlayer> membersWithRank = new ArrayList<>();
        for (TeamPlayer player : getPlayers())
            if (player.getRank().equals(memberRank))
                membersWithRank.add(player);
        return membersWithRank;
    }
    
    public boolean addPlayer(final TeamPlayer player)
    {
        final boolean fileWriteCheck = TeamFile.writePlayerToTeam(this, player, TeamRank.FOLLOWER);
        if (fileWriteCheck)
        {
            for (TeamPlayer onlineMembers : getPlayers())
                onlineMembers.getPlayer().sendMessage(TPText.hasJoinedTeam(player));
            
            members.add(player.getUUID());
        }
        return fileWriteCheck;
    }
    public boolean removePlayer(final TeamPlayer player)
    {
        final boolean fileWriteCheck = TeamFile.removePlayerFromTeam(this, player);
        if (fileWriteCheck)
            members.remove(player.getUUID());
        return fileWriteCheck;
    }
    
    public void setSpawn(final Location location) 
    { 
        this.teamSpawn = location;
        final Block flagBlock = location.getBlock().getRelative(BlockFace.DOWN);
        if (flagBlock.isEmpty())
            flagBlock.setType(Material.STANDING_BANNER);
    }
    
    public boolean validSpawn()
    {
        if (teamSpawn != null)
        {
            final Block spawnBlock = teamSpawn.getBlock();
            return teamSpawn != null
                    && spawnBlock.isEmpty()
                    && spawnBlock.getRelative(0, -1, 0).getType().equals(Material.STANDING_BANNER)
                    && !spawnBlock.getRelative(0, -2, 0).isEmpty();
        }
        else
            return false;
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
