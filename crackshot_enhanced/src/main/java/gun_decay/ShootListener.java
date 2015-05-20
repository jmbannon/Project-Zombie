/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gun_decay;

import com.shampaggon.crackshot.events.WeaponPreShootEvent;
import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jbannon
 */
public class ShootListener implements Listener {
    
    private static final Random RAND = new Random();
    private static final int UPPER_BOUND = 1200;
    private static final int LOWER_BOUND = 650;
    
    private static final String LORE_VERIFY = "PZ";
    private static final String SEPERATOR = "&&";
    private static final int TIERS = 5;
    
    public ShootListener() {
    }
    
    @EventHandler(priority = EventPriority.HIGH)
	public void preDecayEvent(WeaponPreShootEvent event) {
        final Player shooter = event.getPlayer();
        final ItemStack gunItem = shooter.getItemInHand();
        final ItemMeta gunMeta = gunItem.getItemMeta();
        final double bulletSpread = event.getBulletSpread();
        final int gunTier;
        
        if (!gunMeta.hasLore())
            return;
        
        List<String> lore = gunMeta.getLore();
        final String lastElement = lore.get(lore.size() - 1);
        
        if (hasSpecLore(lastElement)) {
            gunTier = decrementDurability(lore, bulletSpread);
            if (gunTier <= 0) {
                shooter.sendMessage(ChatColor.GOLD + "Your gun is jammed.");
                event.setCancelled(true);
                return;
            }
        } else
            gunTier = initializeSpecLore(lore, event.getBulletSpread());
        
        event.setBulletSpread(getTrueBulletSpread(bulletSpread, gunTier));
        gunMeta.setLore(lore);
        gunItem.setItemMeta(gunMeta);
    }
    
    private static double getTrueBulletSpread(final double spread,
                                              final int tier)
    {
        return spread + (double)(spread/(double)tier);
    }
    
    private static int initializeSpecLore(final List<String> lore,
                                          final double bulletSpread)
    {
        final StringBuilder stb = new StringBuilder();
        final int initialDurability = getInitialDurability(bulletSpread);
        final int maxDurability = getMaxDurability(bulletSpread);
        final int gunTier = getGunTier(initialDurability, maxDurability);
        lore.set(1, getAccuracyLore(getTrueBulletSpread(bulletSpread, gunTier)));
        lore.add(getConditionLore(gunTier));
        
        stb.append(LORE_VERIFY);
        stb.append(SEPERATOR);
        stb.append(initialDurability);
        stb.append(SEPERATOR);
        stb.append(maxDurability);
        stb.append(SEPERATOR);
        stb.append(gunTier);
        
        lore.add(stb.toString());
        return gunTier;
    }
    
    /**
     * Decrements the durability and returns the gun tier (0 broken, TIERS max)
     * @param specLore String of the specifications from the gun lore.
     * @return Returns the gun tier.
     */
    private static int decrementDurability(final List<String> lore,
                                           final double bulletSpread)
    {
        final String specLore = lore.get(lore.size() - 1);
        final String[] split = specLore.split(SEPERATOR);
        if (split.length != 4)
            return -1;
        
        final int initialDurability = Integer.valueOf(split[2]);
        final int durability = Integer.valueOf(split[1]) - 1;
        if (durability == 0)
            return 0;
        
        final int gunTier = getGunTier(durability, initialDurability);
        final StringBuilder stb = new StringBuilder();
        
        if (gunTier != Integer.valueOf(split[3])) {
            split[3] = String.valueOf(gunTier);
            lore.set(lore.size() - 2, getConditionLore(gunTier));
            lore.set(1, getAccuracyLore(getTrueBulletSpread(bulletSpread, gunTier)));
        }
        
        split[1] = String.valueOf(durability);
        for (int i = 0; i < 4; i++) {
            stb.append(split[i]);
            stb.append(SEPERATOR);
        }
        lore.set(lore.size()-1, stb.toString());
        return gunTier;
    }
    
    private static String getAccuracyLore(final double trueBulletSpread)
    {
        return ChatColor.DARK_GREEN + "Accuracy: "
                + ChatColor.GREEN
                + String.format("%.2f", (double)(100 - (trueBulletSpread * 16.1337))) 
                + "%";
    }
    
    private static String getConditionLore(final int tier)
    {
        return ChatColor.DARK_GREEN + "Condition: " + getCondition(tier);
    }
    
    private static String getCondition(final int tier)
    {
        switch (tier) {
        case 5: return ChatColor.RED + "New";
        case 4: return ChatColor.AQUA + "Mint";
        case 3: return ChatColor.GREEN + "Used";
        case 2: return ChatColor.YELLOW + "Worn";
        case 1: return ChatColor.RED + "Rusty";
        case 0: return "";
        }
        return "";
    }
    
    private static int getGunTier(final int currentDurability,
                                  final int initialDurability)
    {
        final double ratio = (double)currentDurability / (double)initialDurability;
        for (int i = 0; i <= TIERS; i++)
            if (Double.compare(ratio, (double)i/(double)TIERS) <= 0) return i;
        
        return TIERS;
    }
    
    private static Boolean hasSpecLore(final String specLore)
    {
        return specLore.startsWith(LORE_VERIFY);
    }
    
    /**
     * Returns the initial amount of a shots a gun starts with based on its
     * accuracy. Calculates this with the following algorithm:
     * ACCURACY * (1000 ~ 1400)
     * 
     * @param bulletSpread Accuracy of the gun.
     * @return Initial amount of shots.
     */
    private static int getInitialDurability(final double bulletSpread)
    {
        return (int)(bulletSpread * (LOWER_BOUND + RAND.nextInt(UPPER_BOUND - LOWER_BOUND + 1)));
    }
    
    private static int getMaxDurability(final double bulletSpread)
    {
        return (int)(bulletSpread * UPPER_BOUND);
    }
            
}
