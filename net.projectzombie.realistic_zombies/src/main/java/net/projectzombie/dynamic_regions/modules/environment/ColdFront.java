/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.modules.environment;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author jesse
 */
public class ColdFront extends DamageArea
{
    private static final int DURATION_MIN     = 70; //120
    private static final int DURATION_MAX     = 79; //270
    private static final int DAMAGE_FREQUENCY = 10; //30
    private static final int DAMAGE           = 2;
    
    private final int yValue;
    
    public ColdFront(final String regionName, 
                     final int executeFrequencySeconds,
                     final int yValue)
    {
        super(regionName,
              executeFrequencySeconds,
              DURATION_MIN,
              DURATION_MAX,
              DAMAGE_FREQUENCY,
              DAMAGE);
        
        this.yValue = yValue;
    }
    
    @Override
    public boolean globallyInConditions(final World world)
    { 
        return world.hasStorm();
    }

    @Override
    public boolean inConditions(final World world,
                                final Player player)
    {
        return player.getLocation().getBlockY() < yValue;
    }

    @Override
    public double damageModifier(Player player) {
        final PlayerInventory inv = player.getInventory();
        int damageModifier = 1;
        
        if(inv.getHelmet().getType().equals(Material.LEATHER_HELMET))
            damageModifier -= 1;
        if(inv.getHelmet().getType().equals(Material.LEATHER_CHESTPLATE))
            damageModifier -= 2;
        if(inv.getHelmet().getType().equals(Material.LEATHER_LEGGINGS))
            damageModifier -= 2;
        if(inv.getHelmet().getType().equals(Material.LEATHER_BOOTS))
            damageModifier -= 1;
        
        return (double)damageModifier/6.0;
    }
}
