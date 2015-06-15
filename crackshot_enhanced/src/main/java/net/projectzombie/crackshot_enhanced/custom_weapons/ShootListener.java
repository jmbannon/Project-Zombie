/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons;

import com.shampaggon.crackshot.events.WeaponPreShootEvent;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * Gun decay happens within the lore of the item of the weapon. There are two
 * lines that the listener reads from to determine gun durability and accuracy.
 * 
 * These lines appear to be invisible from the player's perspective, but
 * are actually converted to HEX and have the chat symbol placed in front
 * of them to act as colors with no text. These lines are encoded and decoded
 * through the HiddenStringUtils class.
 * 
 * Durability lore string:
 * PZ==durability==maxDurability==conditionTier==hasSight==hasBarrel==hasGrip
 * 
 * Stat lore string:
 * PZ==gunType==CSBulletSpread==scope==triggerType
 * 
 */
public class ShootListener implements Listener
{
    private static final String LORE_VERIFY = "PZ";
    private static final String SEPERATOR = "==";
    
    //private static final int DURABILITY_PARTS = 7;
    private static final int STAT_PARTS = 5;
    
    /* Indexes within the weapon lore */
    private static final int ACCURACY_IDX = 1;
    private static final int SCOPE_IDX = 3;
    private static final int FIRE_MODE_IDX = 2;
    private static final int CONDITION_IDX = 4;
    private static final int HIDDEN_DURABILITY_IDX = 5;
    
    private static final int HIDDEN_PRE_STAT_IDX = 2;
    private static final int HIDDEN_POST_STAT_IDX = 6;
    
    
    
    public ShootListener() { /* Do nothing */ }
    
    /**
     * Right before the weapon fires, method will check to see if custom 
     * durability has been initialized within the lore. Initializes it if it
     * has not, then decrements the durability by one.
     * 
     * @param event Event before the weapon fires. 
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void preDecayEvent(WeaponPreShootEvent event)
    {
        final Player shooter = event.getPlayer();
        final ItemStack gunItem = shooter.getItemInHand();
        final ItemMeta gunMeta = gunItem.getItemMeta();
        final Double setBulletSpread;
        if (!gunMeta.hasLore())
            return;
        
        final List<String> lore = gunMeta.getLore();

        if (isInitialized(lore))
            setBulletSpread = decrementDurability(lore, event.getBulletSpread());
        else
            setBulletSpread = initializeSpecLore(lore, event.getBulletSpread());
        
        assert(setBulletSpread > 0);
        
        event.setBulletSpread(setBulletSpread);
        gunMeta.setLore(lore);
        gunItem.setItemMeta(gunMeta);
    }
    
    /**
     * Initializes the weapon's lore with custom durability and condition, 
     * calculates its bullet spread based on condition, and returns it.
     * 
     * @param lore Weapon lore.
     * @param eventBulletSpread Bullet spread from the fire event.
     * @return Bullet spread based on the condition.
     */
    private static double initializeSpecLore(final List<String> lore,
                                             final double eventBulletSpread)
    {
        final String[] statParts = lore.get(HIDDEN_PRE_STAT_IDX).split(SEPERATOR);
        assert(statParts.length == STAT_PARTS && verifyLore(statParts[0]));
        
        /* Stat lore string */
        final WeaponType weaponType = WeaponType.getType(statParts[1]);
        final double CSBulletSpread = Double.valueOf(statParts[2]);
        final int scopeType = Integer.valueOf(statParts[3]);
        final int fireMode = Integer.valueOf(statParts[4]);
        
        
        final int initialDurability = weaponType.getInitialDurability(CSBulletSpread);
        final int maxDurability = weaponType.getMaxDurability(CSBulletSpread);
        final int gunTier = weaponType.getTierInt(CSBulletSpread, initialDurability);
        final String accuracyDisplay = weaponType.getAccuracy(CSBulletSpread, gunTier);
        final String scopeTypeDisplay = getScopeDisplay(scopeType);
        final String fireModeDisplay = getFireModeDisplay(fireMode);
        final String conditionDisplay = weaponType.getCondition(gunTier);
        
        assert(initialDurability > 0);
        assert(maxDurability > 0);
        assert(gunTier > 0);
        assert(accuracyDisplay != null);
        assert(scopeTypeDisplay != null);
        assert(fireModeDisplay != null);
        assert(conditionDisplay != null);
        
        lore.set(ACCURACY_IDX, accuracyDisplay);
        lore.add(FIRE_MODE_IDX, fireModeDisplay);
        lore.add(SCOPE_IDX, scopeTypeDisplay);
        lore.add(CONDITION_IDX, conditionDisplay);
        
        final StringBuilder stb = new StringBuilder(LORE_VERIFY);
        stb.append(SEPERATOR);
        stb.append(initialDurability);
        stb.append(SEPERATOR);
        stb.append(maxDurability);
        stb.append(SEPERATOR);
        stb.append(gunTier);
        
        //lore.add(SPEC_IDX, HiddenStringUtils.encodeString(stb.toString())); Hidden Lore
        lore.add(HIDDEN_DURABILITY_IDX, stb.toString());
        return weaponType.getBulletSpread(eventBulletSpread, gunTier);
    }
    
    /**
     * Decrements the durability of the weapon and updates the lore for the
     * weapon. Durability calculates the condition and accuracy.
     * 
     * @param lore Lore of the gun from the item meta.
     * @param eventBulletSpread Bullet spread from event.
     * @return Bullet spread based on the condition.
     */
    private double decrementDurability(final List<String> lore,
                                       final double eventBulletSpread)
    {
        //final String specLore = HiddenStringUtils.extractHiddenString(lore.get(SPEC_IDX)); Hidden Lore
        final String[] durabilityParts = lore.get(HIDDEN_DURABILITY_IDX).split(SEPERATOR);
        final String[] statParts = lore.get(HIDDEN_POST_STAT_IDX).split(SEPERATOR);
        
        assert(durabilityParts.length == 4 && verifyLore(durabilityParts[0]));
        assert(statParts.length == 3 && verifyLore(statParts[0]));

        /* Durability lore string */
        final int durability = Integer.valueOf(durabilityParts[1]) - 1;
        final int maxDurability = Integer.valueOf(durabilityParts[2]);
        final int previousGunTier = Integer.valueOf(durabilityParts[3]);
        
        /* Stat lore string */
        final WeaponType weaponType = WeaponType.getType(statParts[1]);
        final double CSBulletSpread = Double.valueOf(statParts[2]);
        final int updatedGunTier = weaponType.getTierInt(CSBulletSpread, durability);
        
        assert(weaponType != null);
        
        final StringBuilder stb = new StringBuilder();
        
        if (updatedGunTier != previousGunTier) {
            durabilityParts[3] = String.valueOf(updatedGunTier);           
            lore.set(ACCURACY_IDX, weaponType.getAccuracy(CSBulletSpread, updatedGunTier));
            lore.set(CONDITION_IDX, weaponType.getCondition(updatedGunTier));
        }
        
        durabilityParts[1] = String.valueOf(durability);
        for (int i = 0; i < durabilityParts.length - 1; i++) {
            stb.append(durabilityParts[i]);
            stb.append(SEPERATOR);
        } 
        stb.append(durabilityParts[durabilityParts.length - 1]);
        
        //lore.set(SPEC_IDX, HiddenStringUtils.encodeString(stb.toString())); Hidden Lore
        lore.set(HIDDEN_DURABILITY_IDX, stb.toString());
        return weaponType.getBulletSpread(eventBulletSpread, updatedGunTier);
    } 
    
    /**
     * Verifies the string starts with the 'magic string'.
     * @param loreString String to verify (within the lore).
     * @return True if it verifies.
     */
    private static Boolean verifyLore(final String loreString)
    {
        return loreString.startsWith(LORE_VERIFY);
    }
    
    /**
     * Returns whether the weapon lore has been initialized with custom
     * durability and condition.
     * 
     * @param lore Lore of the weapon.
     * @return True if it has been initialized.
     */
    private boolean isInitialized(List<String> lore)
    {
        return lore.size() > HIDDEN_POST_STAT_IDX;
    }
    
    /**
     * Returns the string to display based on the weapon lore's fire mode.
     * 
     * @param fireMode Integer set in Crackshot lore for fire mode.
     * @return String to display for fire mode.
     */
    private static String getFireModeDisplay(final int fireMode)
    {
        final StringBuilder stb = new StringBuilder();
        stb.append(String.valueOf(ChatColor.DARK_RED));
        stb.append("Fire Mode: ");
        stb.append(String.valueOf(ChatColor.GOLD));
        switch(fireMode)
        {
        case 0: stb.append("Single Shot"); break;
        case 1: stb.append("Burst"); break;
        case 2: stb.append("Fully Auto"); break;
        default: return null;
        }
        return stb.toString();
    }
    
    /**
     * Returns the string to display based on the weapon lore's scope type.
     * 
     * @param scopeInt Integer set in Crackshot lore for scope type.
     * @return String to display for fire mode.
     */
    private static String getScopeDisplay(final int scopeInt)
    {
        final StringBuilder stb = new StringBuilder();
        stb.append(String.valueOf(ChatColor.DARK_RED));
        stb.append("Scope: ");
        stb.append(String.valueOf(ChatColor.GOLD));
        switch(scopeInt)
        {
        case 0: stb.append("n/a"); break;
        case 1: stb.append("ACOG"); break;
        case 2: stb.append("Tactical"); break;
        case 3: stb.append("Long-Range"); break;
        default: return null;
        }
        return stb.toString();
    }
}
