/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.utilities;

import java.util.Collection;
import java.util.LinkedList;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class PlayerMethods
{
    static final private LinkedList<Coordinate> trail = new LinkedList<>();
    static final private int 
        PLAYER_RECORD_COUNT = 20,
        WAIT_DURATION       = 30; 
        
    
    static public void updateTrailList()
    {
        final Player players[] = getOnlinePlayers();
        trimTrailList(players.length);
        
        Block playerBlock;
        for (Player player : players)
        {
            playerBlock = player.getLocation().getBlock();
            trail.add(new Coordinate(playerBlock.getX(), playerBlock.getY(), playerBlock.getZ()));
        }
    }
    
    static private void trimTrailList(final int playerCount)
    {
        final int trailLimit = playerCount * PLAYER_RECORD_COUNT;
        while (trail.size() > trailLimit)
            trail.removeFirst();
    }
    
    
    public static Player[] getOnlinePlayers()
    {
        final Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        
        Player[] playerArray = new Player[onlinePlayers.size()];
        int idx = 0;
        
        for (Player player : onlinePlayers)
            playerArray[idx++] = player;
        
        return playerArray;
    }
}
