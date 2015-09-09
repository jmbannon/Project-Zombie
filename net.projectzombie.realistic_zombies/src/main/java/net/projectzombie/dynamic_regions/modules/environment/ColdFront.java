/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.modules.environment;

import net.projectzombie.dynamic_regions.utilities.Coordinate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author jesse
 */
public class ColdFront extends DamageArea
{
    
    private static final String BEGIN_MSG = ChatColor.GOLD + "A " + ChatColor.AQUA + "cold front" + ChatColor.GOLD + " is approaching. Wear fur armor or stand near a fire to stay warm!";
    private static final String END_MSG   = ChatColor.GOLD + "The " + ChatColor.AQUA + "cold front" + " has passed.";
    
    private static final int DURATION_MIN     = 70; //120
    private static final int DURATION_MAX     = 79; //270
    private static final int DAMAGE_FREQUENCY = 10; //30
    private static final int DAMAGE           = 2;
    
    static boolean IS_RUNNING = false;
    
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
              DAMAGE,
              BEGIN_MSG,
              END_MSG);
        
        this.yValue = yValue;
    }
    
    @Override
    public boolean globallyInConditions(final World world)
    { 
        return world.hasStorm();
    }

    @Override
    public boolean isDamageable(final World world,
                                final Player player)
    {
        return player.getLocation().getBlockY() > yValue && !this.isNearFire(player);
    }

    @Override
    public double damageModifier(Player player) {
        final PlayerInventory inv = player.getInventory();
        int damageModifier = 6;
        
        final ItemStack 
          helmet = inv.getHelmet(),
          chest  = inv.getChestplate(),
          legs   = inv.getLeggings(),
          boots  = inv.getBoots();
        
        if(helmet != null && helmet.getType().equals(Material.LEATHER_HELMET))
            damageModifier -= 1;
        if(chest != null && chest.getType().equals(Material.LEATHER_CHESTPLATE))
            damageModifier -= 2;
        if(legs != null && legs.getType().equals(Material.LEATHER_LEGGINGS))
            damageModifier -= 2;
        if(boots != null && boots.getType().equals(Material.LEATHER_BOOTS))
            damageModifier -= 1;
        
        return (double)damageModifier/6.0;
    }

    @Override
    public boolean isRunning()
    {
        return IS_RUNNING;
    }

    @Override
    public void setRunning(boolean isRunning) {
        IS_RUNNING = isRunning;
    }
    
    private boolean isNearFire(final Player player)
    {
        final Coordinate[] toCheck = Coordinate.getRectangle(new Coordinate(-2, -3, -2), new Coordinate(2, 1, 2));
        final Block playerBlock = player.getLocation().getBlock();
        
        for (Coordinate offset : toCheck)
            if (this.isWarm(offset.toBlockOffset(playerBlock)))
                return true;
        
        return false;
    }
    
    private boolean isWarm(final Block block)
    {
        final Material type = block.getType();
        return type.equals(Material.FIRE)
                || type.equals(Material.LAVA)
                || type.equals(Material.BURNING_FURNACE);
    }
}
