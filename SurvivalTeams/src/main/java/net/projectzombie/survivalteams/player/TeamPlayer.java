/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.player;

import java.util.HashMap;
import java.util.UUID;
import net.projectzombie.survivalteams.controller.TeamFile;
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
    
    private HashMap<String, Long> pendingInvites;
    
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
    public void createTeam(final String teamName)
    {
        if (!hasTeam())
          if (TeamFile.teamExists(teamName))
              if (TeamFile.writeTeam(this, teamName))
              {
                  team = new Team(teamName, this);
                  rank = TeamRank.LEADER;
                  player.sendMessage(TPText.createdNewTeam(team));
              }
              else
                  player.sendMessage(TPText.FILE_ERROR);
          else
              player.sendMessage(TPText.TEAM_CREATE_NAME_TAKEN);
        else
            player.sendMessage(TPText.ON_TEAM);
    }
    
    public void disbandTeam()
    {
        if (isLeader())
        {
            if (TeamFile.removeTeam(team))
                for (TeamPlayer teamMembers : team.getPlayers())
                    teamMembers.disbandedFromTeam();
            else
                player.sendMessage(TPText.FILE_ERROR);
        }
        else
            player.sendMessage(TPText.NOT_LEADER);
    }
    
    public void promotePlayer(final TeamPlayer reciever,
                              final TeamRank newRank)
    {
        if (rank.canPromote())
            if (reciever.rank.canBePromoted() && team.equals(reciever.team))
                if (TeamFile.writePromotion(this, reciever, newRank))
                {
                    player.sendMessage(TPText.promoted(reciever, newRank));
                    reciever.recievePromotion(this, newRank);
                }
                else
                    player.sendMessage(TPText.FILE_ERROR);
            else
                player.sendMessage(TPText.CANNOT_PROMOTE);
        else if (team == null)
            player.sendMessage(TPText.NO_TEAM);
        else
            player.sendMessage(TPText.RANK_NO_OPERATION);
    }
    
    public void setSpawn()
    {
        if (hasTeam() && rank.canSetSpawn())
            if (TeamFile.writeSpawn(team))
            {
                team.setSpawn(player.getLocation());
                for (TeamPlayer member : team.getPlayers())
                    member.getPlayer().sendMessage(TPText.NEW_SPAWN);
            }
            else
                player.sendMessage(TPText.FILE_ERROR);
        else
            player.sendMessage(TPText.RANK_NO_OPERATION);
            
    }

    ////////////////////////////////////////////////////////////////////////////
    // Officer functions
    //
    public void invitePlayerToTeam(final TeamPlayer reciever,
                                   final TeamRank recieverRank)
    {
        if (rank.canInvite() && !reciever.hasTeam())
            reciever.recieveTeamInvite(this, recieverRank);
        else if (reciever.team != null)
            player.sendMessage(TPText.INVITE_INVALID_HAS_TEAM);
        else if (team == null)
            player.sendMessage(TPText.NO_TEAM);
        else
            player.sendMessage(TPText.RANK_NO_OPERATION);
    }
    
    public void kickPlayerFromTeam(final TeamPlayer reciever)
    {
        if (rank.canKick())
            if (reciever.getTeam().equals(team))
                if (TeamFile.removePlayerFromTeam(team, reciever))
                {
                    reciever.kickedFromTeam();
                    player.sendMessage(TPText.kickedPlayer(reciever));
                }
                else
                    player.sendMessage(TPText.FILE_ERROR);
            else
                player.sendMessage(TPText.NOT_ON_TEAM);
        else
            player.sendMessage(TPText.RANK_NO_OPERATION); 
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Team functions
    //
    
    public void teleportToSpawn()
    {
        final Location spawn = team.getSpawn();
        if (hasTeam())
            if (spawn != null)
                player.teleport(spawn);
            else
                player.sendMessage(TPText.NO_SPAWN);
        else
            player.sendMessage(TPText.NO_TEAM);
    }
    
    public void quitTeam()
    {
        if (hasTeam())
            if (!isLeader())
                if (TeamFile.removePlayerFromTeam(team, this))
                {
                    player.sendMessage(TPText.quitTeam(team));
                    for (TeamPlayer oldMember : team.getPlayers())
                        oldMember.getPlayer().sendMessage(TPText.hasQuitTeam(this));
                    clearTeam();
                }
            else
                player.sendMessage(TPText.LEADER_CANT_QUIT);
        else
            player.sendMessage(TPText.NO_TEAM);
    }
    
    private void kickedFromTeam()
    {
        player.sendMessage(TPText.kickedFromTeam(team));
        clearTeam();
    }
    
    private void disbandedFromTeam()
    {
        player.sendMessage(TPText.disbandedTeam(team));
        clearTeam();
    }
    
    
    private void recievePromotion(final TeamPlayer sender,
                                  final TeamRank newRank)
    {
        rank = newRank;
        player.sendMessage(TPText.recievePromototion(sender, newRank));
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Outsider Functions
    //

    
    private void recieveTeamInvite(final TeamPlayer sender,
                                  final TeamRank rank)
    {
        if (sender.team != null && team == null)
        {
            pendingInvites.put(sender.team.getName(), getTimeStamp());
            player.sendMessage(TPText.recieveTeamInvite(sender, rank));
        }
    }
    
    public void acceptTeamInvite(final TeamPlayer sender,
                                 final TeamRank offeredRank)
    {
        final Team newTeam = sender.team;
        if (validInvite(sender))
        {
            if (newTeam.addPlayer(sender))
                joinTeam(newTeam, offeredRank);
            else
                player.sendMessage(TPText.FILE_ERROR);
        }
        else
            player.sendMessage(TPText.INVITE_INVALID_EXPIRED);
    }
    

    ////////////////////////////////////////////////////////////////////////////
    // Helper Functions
    //
    
    private boolean validInvite(final TeamPlayer sender)
    {
        final String teamName = sender.team.getName();
        return team == null && pendingInvites.containsKey(teamName)
                && (pendingInvites.get(teamName) - getTimeStamp() <= INVITATION_ACCEPT_TIME);
    }
    
    private void clearTeam()
    {
        this.team = null;
        this.rank = TeamRank.NULL;
        this.pendingInvites = new HashMap<>();
    }
    
    private void joinTeam(final Team team,
                          final TeamRank rank)
    {
        this.team = team;
        this.rank = rank;
        this.pendingInvites = null;
    }
    
    public String toFileName()     { return playerUUID.toString(); }
    public boolean hasTeam()       { return team != null; }
    public boolean isLeader()      { return team != null && rank.isLeader() && team.getLeader().equals(this); }
    
    static public long getTimeStamp() { return System.currentTimeMillis() / 1000L; }
}
