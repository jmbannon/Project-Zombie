/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.player;

import java.util.UUID;
import net.projectzombie.survivalteams.controller.TeamController;
import net.projectzombie.survivalteams.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author jb
 */
public class TeamPlayer
{
    
    private final UUID playerUUID;
    private Team team;
    private int rank;
    
    public TeamPlayer(final UUID uuid,
                      final Team team,
                      final int rank)
    {
        this.playerUUID = uuid;
        this.team       = team;
        this.rank       = rank;
    }
    
    public Team   getTeam()       { return team; }
    public Player getPlayer()     { return Bukkit.getPlayer(playerUUID); }
    public String getPlayerName() { return getPlayer().getName(); }
    public int    getRank()       { return rank; }
    
    public boolean setRank(final int rank)
    {
        if (rank >= 0)
        {
            this.rank = rank;
            return true;
        }
        else
            return false;
    }
    
    public void teleToSpawn()
    {
        final Player player = this.getPlayer();
        if (this.team != null)
            player.teleport(this.team.getSpawnLocation());
        else
            player.sendMessage("You're not on a team!");
    }
    
    /**
     * Disbands the team if he is the team leader.
     * @return 
     */
    public boolean quitTeam()
    {
        team = null;
        rank = -1;
        return team.removePlayer(this);
    }
    
    public void joinTeam(final Team teamToJoin)
    {
        
    }
    
    @Override
    public boolean equals(final Object object)
    {
        if (object instanceof TeamPlayer)
        {
            final TeamPlayer otherPlayer = (TeamPlayer) object;
            return this.playerUUID.equals(otherPlayer.playerUUID);
        }
        else
            return false;
    }
    
}
