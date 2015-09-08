/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.projectzombie.dynamic_regions.world.DRWorld;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author jesse
 */
public class PlayerTrail
{
    
    static final private List<Object> trail =  Collections.synchronizedList(new LinkedList<>());
    static final private int 
        PLAYER_RECORD_COUNT = 20,
        WAIT_DURATION       = 30;
    
        
    static public List<Object> getTrail()          { return trail; }
    static public int          getFrequencyTicks() { return DRWorld.getTickAmount(WAIT_DURATION); }
    
    static public void updateTrailList()
    {
        final ArrayList<Player> players = PlayerMethods.getOnlinePlayers();
        trimTrailList(players.size());
        
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
            trail.remove(0);
    }
    
}
