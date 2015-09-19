/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.controller;

import java.util.UUID;
import net.projectzombie.survivalteams.player.TeamPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 *
 * @author jb
 */
public class PlayerLogin
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void AssignTeamPlayerOnLoogin(PlayerLoginEvent event)
    {
        // Search if team exists based on UUID
        // if exists
          // if team instance is created
          //   add player to team
          // else
          //   create instance of team
          // join player to team
        // else
          // make team null
        final Player player = event.getPlayer();
        final UUID playerUUID = player.getUniqueId();
        
        final TeamPlayer loggedInPlayer = new TeamPlayer(playerUUID,
                                                         null,  // Team
                                                         1000); // Rank
    }
}
