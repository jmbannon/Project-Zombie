/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.modules.environment;

import java.util.Random;
import java.util.logging.Level;
import net.projectzombie.dynamic_regions.modules.Controller;
import net.projectzombie.dynamic_regions.modules.RegionModule;
import net.projectzombie.dynamic_regions.world.DRWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author jesse
 */
public abstract class DamageArea extends RegionModule
{
    private static final Random RAND = new Random();
    
    private final int durationMin;
    private final int durationMax;
    private final int damageFrequency;
    private final double damage;
    
    private final String beginMessage;
    private final String endMessage;
    
    public DamageArea(final String regionName,
                      final int executeFrequency,
                      final int durationMin,
                      final int durationMax,
                      final int damageFrequency,
                      final int damage,
                      final String beginMessage,
                      final String endMessage) 
    {
        super(regionName, executeFrequency);
        this.durationMin = DRWorld.getTickAmount(durationMin);
        this.durationMax = DRWorld.getTickAmount(durationMax);
        this.damageFrequency = DRWorld.getTickAmount(damageFrequency);
        this.damage = damage;
        this.beginMessage = beginMessage;
        this.endMessage = endMessage;
    }
    
    @Override
    public boolean executeModule(final World world,
                                 final Player player)
    {
        if (!this.isRunning() && this.globallyInConditions(world))
        {
            this.setRunning(true);
            final int tickStart = 0;
            final int tickDuration = RAND.nextInt(durationMax - durationMin) + durationMin;
            
            if (beginMessage != null)
                Bukkit.broadcastMessage(beginMessage);
            
            this.scheduleDamageCheck(world, player, tickStart, tickDuration);
            return true;
        }
        else
            return false;
    }
    
    private void scheduleDamageCheck(final World world,
                                     final Player player,
                                     final int tickDelay,
                                     final int tickDuration)
    {
        final DamageArea area = this;

        Bukkit.getLogger().info("scheduling damage check");
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Controller.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                if (tickDelay <= tickDuration && area.globallyInConditions(world))
                {
                    area.damagePlayer(world, player);
                    area.scheduleDamageCheck(world, player, tickDelay + damageFrequency, tickDuration);
                    Bukkit.getLogger().info("tick delay: " + tickDelay + " tickDuration: " + tickDuration);
                    
                } else
                {
                    if (area.endMessage != null)
                        Bukkit.broadcastMessage(area.endMessage);
                    
                    area.setRunning(false);
                }
            }
        }, tickDelay + damageFrequency);
    }
    
    private void damagePlayer(final World world,
                              final Player player)
    {
        final double damageModifier = this.damageModifier(player);
        if (this.isDamageable(world, player) && damageModifier > 0)
            player.damage(damage * damageModifier);
    }
    
    public boolean globallyInConditions(final World world) { return true; }
    
    public abstract boolean isDamageable(final World world,
                                         final Player player);
    
    public abstract double damageModifier(final Player player);
    
    public abstract boolean isRunning();
    public abstract void setRunning(final boolean isRunning);
}
