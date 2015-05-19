/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gun_decay;

import com.shampaggon.crackshot.events.WeaponPreShootEvent;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
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
    private static final int TIERS = 5;
    
    public ShootListener() {
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
	public void decayEvent(WeaponPreShootEvent event) {
        final Player shooter = event.getPlayer();
        final ItemStack gunItem = shooter.getItemInHand();
        final ItemMeta gunMeta = gunItem.getItemMeta();

        if (!gunMeta.hasLore())
            return;
        
        List<String> lore = gunMeta.getLore();
        final String lastElement = lore.get(lore.size() - 1);
        
        if (hasSpecLore(lastElement)) {
            lore = decrementDurability(lore);
            if (lore == null) {
                shooter.getInventory().remove(gunItem);
                shooter.sendMessage(ChatColor.GOLD + "Your gun is jammed.");
                event.setCancelled(true);
                return;
            }
        } else
            lore = initializeSpecLore(lore, event.getBulletSpread());
        
        gunMeta.setLore(lore);
        gunItem.setItemMeta(gunMeta);   
    }
    
    private List<String> initializeSpecLore(final List<String> lore,
                                            final double accuracy)
    {
        final StringBuilder stb = new StringBuilder();
        final int initialDurability = getInitialDurability(accuracy);
        final int maxDurability = getMaxDurability(accuracy);
        final int gunTier = getGunTier(initialDurability, maxDurability);
        
        lore.add(getConditionLore(gunTier));
        
        stb.append(LORE_VERIFY);
        stb.append(ChatColor.COLOR_CHAR);
        stb.append(initialDurability);
        stb.append(ChatColor.COLOR_CHAR);
        stb.append(maxDurability);
        stb.append(ChatColor.COLOR_CHAR);
        stb.append(gunTier);
        
        lore.add(stb.toString());
        
        return lore;
    }
    
    /**
     * Decrements the durability and returns the gun tier (0 broken, TIERS max)
     * @param specLore String of the specifications from the gun lore.
     * @return Returns the gun tier.
     */
    private static List<String> decrementDurability(final List<String> lore)
    {
        final String specLore = lore.get(lore.size() - 1);
        final String[] split = specLore.split(String.valueOf(ChatColor.COLOR_CHAR));
        if (split.length != 4)
            return null;
        
        final int initialDurability = Integer.valueOf(split[2]);
        final int durability = Integer.valueOf(split[1]) - 1;
        if (durability == 0)
            return null;
        
        final int gunTier = getGunTier(durability, initialDurability);
        final StringBuilder stb = new StringBuilder();
        
        if (gunTier != Integer.valueOf(split[3])) {
            split[3] = String.valueOf(gunTier);
            lore.set(lore.size() - 2, getConditionLore(gunTier));
        }
        
        split[1] = String.valueOf(durability);
        for (int i = 0; i < 4; i++) {
            stb.append(split[i]);
            stb.append(ChatColor.COLOR_CHAR);
        }
        lore.set(lore.size()-1, stb.toString());
        return lore;
    }
    
    private static String getConditionLore(final int tier)
    {
        return ChatColor.GOLD + "Condition: " + getCondition(tier);
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
     * @param accuracy Accuracy of the gun.
     * @return Initial amount of shots.
     */
    private static int getInitialDurability(final double accuracy)
    {
        return (int)(accuracy * (LOWER_BOUND + RAND.nextInt(UPPER_BOUND - LOWER_BOUND + 1)));
    }
    
    private static int getMaxDurability(final double accuracy)
    {
        return (int)(accuracy * UPPER_BOUND);
    }
            
}
