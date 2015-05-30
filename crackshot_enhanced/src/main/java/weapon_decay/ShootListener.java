/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weapon_decay;

import com.shampaggon.crackshot.events.WeaponPreShootEvent;
import java.util.List;
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
 * PZ==<durability>==<maxDurability>==<gunTier>
 * 
 * Stat lore string:
 * PZ==<gunType>==<CSBulletSpread>
 * 
 * 
 * 
 * 
 */
public class ShootListener implements Listener
{
    private static final String LORE_VERIFY = "PZ";
    private static final String SEPERATOR = "==";
    
    private static final int ACCURACY_IDX = 1;
    private static final int CONDITION_IDX = 2;
    
    private static final int HIDDEN_DURABILITY_IDX = 3;
    
    private static final int HIDDEN_PRE_STAT_IDX = 2;
    private static final int HIDDEN_POST_STAT_IDX = 4;
    
    public ShootListener() { /* Do nothing */ }
    
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
    
    private static double initializeSpecLore(final List<String> lore,
                                             final double eventBulletSpread)
    {
        final String[] statParts = lore.get(HIDDEN_PRE_STAT_IDX).split(SEPERATOR);
        assert(statParts.length == 3 && verifyLore(statParts[0]));
        
        /* Stat lore string */
        final WeaponType weaponType = WeaponType.getType(statParts[1]);
        final double CSBulletSpread = Double.valueOf(statParts[2]);
        
        final int initialDurability = weaponType.getInitialDurability(CSBulletSpread);
        final int maxDurability = weaponType.getMaxDurability(CSBulletSpread);
        final int gunTier = weaponType.getTierInt(CSBulletSpread, initialDurability);
        final String accuracyDisplay = weaponType.getAccuracy(CSBulletSpread, gunTier);
        final String conditionDisplay = weaponType.getCondition(gunTier);
        
        assert(initialDurability > 0);
        assert(maxDurability > 0);
        assert(gunTier > 0);
        assert(accuracyDisplay != null);
        assert(conditionDisplay != null);
        
        lore.set(ACCURACY_IDX, accuracyDisplay);
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
     * 
     * @param lore Lore of the gun from the item meta.
     * @param eventBulletSpread Bullet spread from event.
     * @return 
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
    
    private static Boolean verifyLore(final String loreString)
    {
        return loreString.startsWith(LORE_VERIFY);
    }
    
    private boolean isInitialized(List<String> lore)
    {
        return lore.size() > HIDDEN_POST_STAT_IDX;
    }
}
