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
package net.projectzombie.survivalteams.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import net.projectzombie.survivalteams.controller.file.FilePath;
import net.projectzombie.survivalteams.controller.file.TeamFile;
import static net.projectzombie.survivalteams.player.TPText.*;
import net.projectzombie.survivalteams.team.Team;
import net.projectzombie.survivalteams.team.TeamRank;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author jb
 * 
 * Instance of all online players as a 'TeamPlayer'. Stores their current team
 * and rank. Responsible for all private player variables.
 * 
 */
public class TeamPlayer
{
    // Seconds
    static private final long INVITATION_ACCEPT_TIME = 20;
    
    private final Player player;
    private final UUID playerUUID;
    private Team team;
    private TeamRank rank;
    
    private final HashMap<String, Long> pendingInvites;
    
    /**
     * Creates a TeamPlayer if they have a team and rank.
     * @param player Bukkit Player object.
     * @param team Team they are on.
     * @param rank  Current rank on team.
     */
    public TeamPlayer(final Player player,
                      final Team team,
                      final TeamRank rank)
    {
        this.player     = player;
        this.playerUUID = player.getUniqueId();
        this.team       = team;
        this.rank       = rank;
        this.pendingInvites = new HashMap<>();
    }
    
    /**
     * Creates a TeamPlayer if they do not have a team.
     * @param player Bukkit Player object.
     */
    public TeamPlayer(final Player player)
    {
        this.player = player;
        this.playerUUID = player.getUniqueId();
        this.team = null;
        this.rank = TeamRank.NULL;
        this.pendingInvites = new HashMap<>();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Accessor functions
    //
    public UUID     getUUID()       { return playerUUID; }
    public Team     getTeam()       { return team; }
    public Player   getPlayer()     { return Bukkit.getPlayer(playerUUID); }
    public String   getPlayerName() { return getPlayer().getName(); }
    public TeamRank getRank()       { return rank; }
    
    ////////////////////////////////////////////////////////////////////////////
    // Leader functions
    //
    
    /**
     * Creates a team of the given name if the name is not taken by an existing
     * team.
     * @param teamName Team name to be created.
     */
    public void createTeam(final String teamName)
    {
        if (!hasTeam())
        {
          if (!TeamFile.containsTeam(teamName))
          {
              final Team team = new Team(teamName, this.playerUUID);
              if (TeamFile.writeTeam(this, team))
              {
                  initializeTeam(team);
                  player.sendMessage(TPText.createdNewTeam(team));
              }
              else
                  player.sendMessage(FILE_ERROR);
          }
          else
              player.sendMessage(TEAM_CREATE_NAME_TAKEN);
        }
        else
            player.sendMessage(ON_TEAM);
    }
    
    /**
     * Creates a new Team as leader and sets rank as leader. 
     * @param createdTeam
     */
    public void initializeTeam(final Team createdTeam)
    {
        team = createdTeam;
        rank = TeamRank.LEADER;
    }
    
    /**
     * Disbands the entire team if the player is the team leader.
     */
    public void disbandTeam()
    {
        if (isLeader())
        {
            if (TeamFile.removeTeam(team))
                for (TeamPlayer teamMembers : team.getPlayers())
                    teamMembers.disbandedFromTeam();
            else
                player.sendMessage(FILE_ERROR);
        }
        else
            player.sendMessage(NOT_LEADER);
    }
    
    /**
     * Promotes a player to the specified rank if the given player and rank are
     * valid. The playerToPromote must be on the same team and a low
     * @param playerToPromote
     * @param theRank 
     */
    public void promotePlayer(final String playerToPromote,
                              final String theRank)
    {
        this.setMemberRank(playerToPromote, theRank, true);
    }
    
    /**
     * Promotes a player to the specified rank if the given player and rank are
     * valid. The playerToPromote must be on the same team and a low
     * @param playerToPromote
     * @param theRank 
     */
    public void demotePlayer(final String playerToPromote,
                             final String theRank)
    {
        this.setMemberRank(playerToPromote, theRank, false);
    }
    
    private void setMemberRank(final String playerToPromote,
                               final String theRank,
                               final boolean promote)
    {
        final TeamPlayer reciever = TeamFile.getPlayer(playerToPromote);
        final TeamRank newRank = TeamRank.getRank(theRank);
        
        if (team == null)
            player.sendMessage(NO_TEAM);
        else if (reciever == null)
            player.sendMessage(PLAYER_NOT_FOUND);
        else if (reciever.equals(this))
            player.sendMessage(SELF_ERROR);
        else if (newRank == null)
            player.sendMessage(RANK_NOT_FOUND);
        else if (!team.equals(reciever.team))
            player.sendMessage(TPText.NOT_ON_TEAM);
        else if (promote && rank.canPromote(newRank))
        {
            if (TeamFile.writeRank(reciever, newRank))
            {
                player.sendMessage(demoted(reciever, newRank));
                reciever.recieveDemotion(this, newRank);
            }
            else
                player.sendMessage(FILE_ERROR);
        }
        else if (!promote && rank.canDemote(newRank))
            if (TeamFile.writeRank(reciever, newRank))
            {
                player.sendMessage(promoted(reciever, newRank));
                reciever.recievePromotion(this, newRank);
            }
            else
                player.sendMessage(FILE_ERROR);
        else
            player.sendMessage(TPText.RANK_NO_OPERATION);
    }
    
    /**
     * Sets the team spawn if the player is the team leader.
     */
    public void setSpawn()
    {
        if (isLeader())
        {
            if (TeamFile.writeSpawn(team, player.getLocation()))
            {
                team.setSpawn(player.getLocation());
                for (TeamPlayer member : team.getPlayers())
                    member.getPlayer().sendMessage(NEW_SPAWN);
            }
            else
                player.sendMessage(FILE_ERROR);
        }
        else
            player.sendMessage(NOT_LEADER);
            
    }

    ////////////////////////////////////////////////////////////////////////////
    // Officer functions
    //
    public void invitePlayerToTeam(final String toInvite)
    {
        final TeamPlayer reciever = TeamFile.getPlayer(toInvite);
        if (reciever == null)
            player.sendMessage(PLAYER_NOT_FOUND);
        else if (rank.canInvite() && !reciever.hasTeam())
        {
            reciever.recieveTeamInvite(this);
            player.sendMessage(TPText.sendInvite(reciever));
        }
        else if (reciever.team != null)
            player.sendMessage(INVITE_INVALID_HAS_TEAM);
        else if (team == null)
            player.sendMessage(NO_TEAM);
        else
            player.sendMessage(RANK_NO_OPERATION);
    }
    
    public void kickPlayerFromTeam(final String toKick)
    {
        final TeamPlayer reciever;
        if (hasTeam())
        {
            reciever = TeamFile.getPlayer(toKick);
            if (reciever == null)
                player.sendMessage(PLAYER_NOT_FOUND);
            else if (reciever.equals(this))
                player.sendMessage(SELF_ERROR);
            else if (rank.canKick(reciever.getRank()))
            {
                if (reciever.getTeam().equals(team))
                {
                    if (TeamFile.removePlayerFromTeam(team, reciever))
                    {
                        reciever.kickedFromTeam();
                        player.sendMessage(TPText.kickedPlayer(reciever));
                    }
                    else
                        player.sendMessage(FILE_ERROR);
                }
                else
                    player.sendMessage(NOT_ON_TEAM);
            }
            else
                player.sendMessage(RANK_NO_OPERATION);
        }
        else
            player.sendMessage(NO_TEAM);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Team functions
    //
    
    public void listOnlineTeamMembers()
    {
        if (hasTeam())
        {
            final ArrayList<TeamPlayer> onlineTeamMembers = team.getPlayers();
            if (onlineTeamMembers.size() == 1)
                player.sendMessage(TEAM_NOT_ONLINE);
            else
            {
                for (TeamPlayer onlinePlayer : team.getPlayers())
                    player.sendMessage(onlinePlayer.getPlayerName());
            }
        }
        else
            player.sendMessage(NO_TEAM);
            
    }
    
    public void teleportToSpawn()
    {
        final Location spawn = team.getSpawn();
        if (hasTeam())
            if (spawn != null)
                player.teleport(spawn);
            else
                player.sendMessage(NO_SPAWN);
        else
            player.sendMessage(NO_TEAM);
    }
    
    public void quitTeam()
    {
        if (hasTeam())
            if (!isLeader())
                if (TeamFile.removePlayerFromTeam(team, this))
                {
                    player.sendMessage(TPText.quitTeam(team));
                    for (TeamPlayer oldMember : team.getPlayers())
                        oldMember.getPlayer().sendMessage(hasQuitTeam(this));
                    clearTeam();
                }
            else
                player.sendMessage(LEADER_CANT_QUIT);
        else
            player.sendMessage(NO_TEAM);
    }
    
    private void kickedFromTeam()
    {
        player.sendMessage(TPText.kickedFromTeam(team));
        clearTeam();
    }
    
    private void disbandedFromTeam()
    {
        player.sendMessage(disbandedTeam(team));
        clearTeam();
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Outsider Functions
    //

    private void recievePromotion(final TeamPlayer sender,
                                  final TeamRank newRank)
    {
        rank = newRank;
        player.sendMessage(TPText.recievePromotion(sender, newRank));
    }
    
    private void recieveDemotion(final TeamPlayer sender,
                                 final TeamRank newRank)
    {
        rank = newRank;
        player.sendMessage(TPText.recieveDemotion(sender, newRank));
    }
    
    private void recieveTeamInvite(final TeamPlayer sender)
    {
        if (sender.team != null && team == null)
        {
            pendingInvites.put(sender.team.getName(), getTimeStamp());
            player.sendMessage(TPText.recieveTeamInvite(sender));
        }
    }
    
    public void acceptTeamInvite(final String teamName)
    {
        final Team newTeam = TeamFile.getTeam(teamName);
        if (validInvite(newTeam))
        {
            if (newTeam.addPlayer(this))
                joinTeam(newTeam);
            else
                player.sendMessage(FILE_ERROR);
        }
        else
            player.sendMessage(INVITE_INVALID_EXPIRED);
    }
    
    public void listOnlineTeams(int pageNumber)
    {
        ArrayList<Team> onlineTeams = TeamFile.getOnlineTeams();
        
        if (pageNumber < 0)
            pageNumber = 1;
        
        final int indexBegin = (pageNumber - 1) * 10;
        final int indexEnd   = indexBegin + 10;
        
        for (int i = indexBegin; i < indexEnd; i++)
        {
            if (i < onlineTeams.size())
                player.sendMessage(onlineTeams.get(i).getName());
        }
    }
    

    ////////////////////////////////////////////////////////////////////////////
    // Helper Functions
    //
    
    private boolean validInvite(final Team team)
    {
        return team != null && pendingInvites.containsKey(team.getName())
                && (getTimeStamp() - pendingInvites.get(team.getName())) <= INVITATION_ACCEPT_TIME;
    }
    
    private void clearTeam()
    {
        this.team = null;
        this.rank = TeamRank.NULL;
    }
    
    private void joinTeam(final Team team)
    {
        this.team = team;
        this.rank = TeamRank.FOLLOWER;
        this.pendingInvites.clear();
    }
    
    public String getPath()
    {
        if (hasTeam())
            return rank.equals(TeamRank.LEADER) ? FilePath.getLeaderPath(team.getName()) : FilePath.getPlayerPath(this, team.getName());
        else
            return null;
    }
    
    public String getFileName()     { return playerUUID.toString(); }
    public boolean hasTeam()       { return team != null; }
    public boolean isLeader()      { return team != null && rank.isLeader() && team.getLeaderUUID().equals(playerUUID); }
    
    static public long getTimeStamp() { return System.currentTimeMillis() / 1000L; }
}
