/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.player;

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
    
    public UUID   getUUID()       { return playerUUID; }
    public Team   getTeam()       { return team; }
    public Player getPlayer()     { return Bukkit.getPlayer(playerUUID); }
    public String getPlayerName() { return getPlayer().getName(); }
    public TeamRank getRank()     { return rank; }
    
    public boolean setTeam(final Team team)
    {
        if (team == null)
        {
            this.team = team;
            return true;
        }
        else
            return false;
    }
    
    public boolean setRank(final TeamRank rank)
    {
        this.rank = rank;
        return true;
    }
    
    public void clearTeam()
    {
        this.team = null;
        this.rank = TeamRank.NULL;
    }
    
    public void teleToSpawn()
    {
        if (this.team != null)
            player.teleport(this.team.getSpawn());
        else
            player.sendMessage("You're not on a team!");
    }
    
    public boolean invitePlayerToTeam(final TeamPlayer player)
    {
        return (rank.canInvite());
            
    }
    
    public String toFileName()
    {
        return playerUUID.toString();
    }
    
}
