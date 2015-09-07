/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.utilities;

import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class PlayerMethods
{
    static private final ArrayList<Player> onlinePlayers = new ArrayList<>();
    
    public static ArrayList<Player> getOnlinePlayers()
    {
        final Collection<? extends Player> currentlyOnline = Bukkit.getServer().getOnlinePlayers();
        
        for (int i = 0; i < onlinePlayers.size(); i++)
        {
            if (!onlinePlayers.get(i).isOnline())
                onlinePlayers.remove(i);
        }
        
        for (Player player : currentlyOnline)
        {
            if (!onlinePlayers.contains(player))
                onlinePlayers.add(player);
        }
        
        return onlinePlayers;
    }
}
