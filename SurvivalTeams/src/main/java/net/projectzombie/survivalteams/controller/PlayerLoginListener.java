/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.controller;

import java.util.UUID;
import net.projectzombie.survivalteams.player.TeamPlayer;
import net.projectzombie.survivalteams.team.TeamRank;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 *
 * @author jb
 */
public class PlayerLoginListener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void AssignTeamPlayerOnLoogin(final PlayerLoginEvent event)
    {
        // Search if team exists based on UUID
        // if exists
          // if !team instance is created
          //   create instance of team
          // join player to team
        // else
          // make player team null
        final Player player = event.getPlayer();
        final UUID playerUUID = player.getUniqueId();
        
        
    }
}
