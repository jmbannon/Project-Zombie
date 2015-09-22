/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.player;

import net.projectzombie.survivalteams.controller.Controller;
import java.util.UUID;
import net.projectzombie.survivalteams.team.Team;
import net.projectzombie.survivalteams.team.TeamRank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author jb
 * 
 * Instance of all online players as a 'TeamPlayer'. Stores their current team
 * and rank. Controls all of their commands.
 * 
 */
public class TeamPlayer
{
    
    private final Player player;
    private final UUID playerUUID;
    private Team team;
    private TeamRank rank;
    
    public TeamPlayer(final Player player,
                      final Team team,
                      final TeamRank rank)
    {
        this.player     = player;
        this.playerUUID = player.getUniqueId();
        this.team       = team;
        this.rank       = rank;
    }
    
    public UUID     getUUID()       { return playerUUID; }
    public Team     getTeam()       { return team; }
    public Player   getPlayer()     { return Bukkit.getPlayer(playerUUID); }
    public String   getPlayerName() { return getPlayer().getName(); }
    public TeamRank getRank()     { return rank; }
    
    public void setTeam(final Team team)     { this.team = team; }
    public void setRank(final TeamRank rank) { this.rank = rank; }
    
    public void clearTeam()
    {
        this.team = null;
        this.rank = TeamRank.NULL;
    }
    
    public void teleToSpawn()
    {
        if (team != null)
            player.teleport(team.getSpawn());
        else
            player.sendMessage(TPText.NO_TEAM);
    }
    
    public void invitePlayerToTeam(final TeamPlayer reciever,
                                   final TeamRank rank)
    {
        if (canInvite())
            Controller.invitePlayerToTeam(this, reciever, rank);
        else if (team == null)
            player.sendMessage(TPText.NO_TEAM);
        else
            player.sendMessage(TPText.CANNOT_INVITE);
    }
    
    public void promotePlayer(final TeamPlayer reciever,
                              final TeamRank rank)
    {
        if (canPromote())
            if (reciever.canBePromoted())
                ;
    }
    
    public void recievePromotion()
    {
        
    }
    
    public String toFileName() { return playerUUID.toString(); }
    public boolean isLeader()  { return rank.equals(TeamRank.LEADER); }
    public boolean canInvite() { return rank.canInvite() && team != null;}
    public boolean canPromote() { return rank.equals(TeamRank.LEADER); }
    public boolean canBePromoted() { return rank.canBePromoted(); }
}
