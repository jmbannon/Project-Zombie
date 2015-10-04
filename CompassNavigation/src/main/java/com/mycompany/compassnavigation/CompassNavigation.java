/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compassnavigation;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import net.projectzombie.care_package.StateSwitcher;
import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 *
 * @author sam
 */
public class CompassNavigation {
    
    private static Block CHEST = null; 
    
    @EventHandler(priority=EventPriority.HIGH)
        
    public void onPlayerUse(PlayerInteractEvent event)
    {
        Player p = event.getPlayer();

        if (p.getItemInHand().getType() == Material.COMPASS)
        {
            CHEST = StateSwitcher.getChestBlock();
            Location location = CHEST.getLocation();
            p.setCompassTarget(location);
        }
    }
}