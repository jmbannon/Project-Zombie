/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.yaml_generator;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireModes.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.MagazineModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.skeleton.FirearmActions.FirearmAction;
import net.projectzombie.crackshot_enhanced.custom_weapons.skeleton.SkeletonTypes.SkeletonType;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.HiddenStringUtils;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns.CrackshotGun;
import org.bukkit.ChatColor;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.ProjectileModifier;

/**
 *
 * @author jesse
 */
public class GunGenerator extends CrackshotGun
{
    public static final ChatColor ITEM_COLOR  = ChatColor.YELLOW;
    
    public GunGenerator(final CrackshotGun gun)
    {
        super(gun);
    }

    
    public String getAmmoID()
    {
        final SkeletonType weaponType = super.getWeaponType();
        final int ammoData = weaponType.getAmmoData();
        
        if (ammoData != 0)
        {
            return String.valueOf(weaponType.getAmmoID() + "~" + ammoData);
        }
        else
        {
            return String.valueOf(weaponType.getAmmoID());
        }
    }
    
    /**
     * Gets the bullet spread. Takes the skeleton's base bullet spread and multiplies
     * it by the sum of all bulletSpreadModifiers.
     * @return Bullet spread after modifiers.
     */
    public double getBulletSpread()
    {
        final double baseBulletSpread = super.getSkeletonBulletSpread();
        final double modifiedBulletSpread;
        double bulletSpreadModifier = 1.0;
        
        for (BulletSpreadModifier mod : super.getBulletSpreadModifiers())
            bulletSpreadModifier += mod.getBulletSpreadMultiplier();
        
        modifiedBulletSpread = (int)Math.round(baseBulletSpread * bulletSpreadModifier);
        
        if (modifiedBulletSpread < .01)
            return 0.1;
        else
            return modifiedBulletSpread;
    }
    
    /**
     * Gets the open duration in ticks. Takes the action's duration and multiplies
     * it by the sum of all duration multipliers.
     * @return Open duration in ticks after multipliers taken into account.
     */
    public int getOpenDuration()
    {
        final FirearmAction action = super.getWeaponType().getAction();
        if (action != null)
        {
            final int openDuration = action.getOpenDuration();
            final int modifiedOpenDuration;
            double durationMultiplier = 1.0;
            
            for (BoltModifier mod : super.getBoltModifiers())
                durationMultiplier += mod.getBoltDurationMultiplier();
            
            modifiedOpenDuration = (int)Math.round(openDuration * durationMultiplier);
            
            if (modifiedOpenDuration <= 1)
                return 1;
            else
                return modifiedOpenDuration;
        }
        else
            return 0;
    }
    
    /**
     * Gets the close duration in ticks. Takes the action's duration and multiplies
     * it by the sum of all duration multipliers.
     * @return Close duration in ticks after multipliers taken into account.
     */
    public int getCloseDuration()
    {
        final FirearmAction action = super.getWeaponType().getAction();
        if (action != null)
        {
            final int closeDuration = action.getCloseDuration();
            final int modifiedCloseDuration;
            double durationMultiplier = 1.0;
            
            for (BoltModifier mod : super.getBoltModifiers())
                durationMultiplier += mod.getBoltDurationMultiplier();
           
            modifiedCloseDuration = (int)Math.round(closeDuration * durationMultiplier);
            
            if (modifiedCloseDuration <= 1)
                return 1;
            else
                return modifiedCloseDuration;
        }
        else
            return 0;
    }
    
    /**
     * Gets the close shoot delay duration in ticks. Takes the action's duration and multiplies
     * it by the sum of all duration multipliers.
     * @return Close shoot delay in ticks after multipliers taken into account.
     */
    public int getCloseShootDelay()
    {
        final FirearmAction action = super.getWeaponType().getAction();
        if (action != null)
        {
            final int closeShootDelay = action.getCloseShootDelay();
            final int modifiedCloseShootDelay;
            double durationMultiplier = 1.0;
            
            for (BoltModifier mod : gun.getBoltModifiers())
                durationMultiplier += mod.getBoltDurationMultiplier();
            
            modifiedCloseShootDelay = (int)Math.round(closeShootDelay * durationMultiplier);
            
            if (modifiedCloseShootDelay <= 1)
                return 1;
            else
                return modifiedCloseShootDelay;
        }
        else
            return 0;
    }
    
    public String getItemName()
    {
        final String ID = String.valueOf(super.getUniqueID());
        final StringBuilder stb = new StringBuilder();
        
        stb.append(ITEM_COLOR);
        for (char c : ID.toCharArray())
        {
            stb.append('&');
            stb.append(c);
        }
        stb.append(ITEM_COLOR);
        stb.append(super.getName());
        return stb.toString().replace(ChatColor.COLOR_CHAR, '&');
    }
    
    public String getItemType()
    {
        final int materialData = super.getItemData();
        final StringBuilder stb = new StringBuilder();
        
        stb.append(super.getItemID());
        if (materialData != 0)
        {
            stb.append('~');
            stb.append(materialData);
        }
        return stb.toString();
    }
    
    public String getItemLore()
    {
        final StringBuilder stbVerify = new StringBuilder();
        stbVerify.append(CrackshotLore.seperator);
        stbVerify.append(CrackshotLore.verification);
        stbVerify.append(CrackshotLore.seperator);
        stbVerify.append(super.getUniqueID());
        
        final StringBuilder stb = new StringBuilder();
        stb.append(CrackshotLore.statsLine);
        stb.append(HiddenStringUtils.encodeString(stbVerify.toString()).replace(ChatColor.COLOR_CHAR, '&'));
        stb.append('|');
        stb.append(CrackshotLore.preShotVerification);
        
        return stb.toString();
    }
    
    public String getInventoryControl()
    {
        return super.getWeaponType().getInventoryControl();
    }
    
    /**
     * Gets the mag size by adding the skeleton amount with the sum
     * of all magazineModifier's magazine boost.
     * @return Mag size after all modifiers have been added to it.
     */
    public int getReloadAmount()
    {
        int reloadAmount = super.getReloadAmount();
        
        for (MagazineModifier mod : super.getMagazineModifiers())
            reloadAmount += mod.getMagazineSizeModifier();
        
        if (reloadAmount < 1)
            return 1;
        else
            return reloadAmount;
    }
    
    /**
     * Gets the reload duration in ticks by multiplying the skeleton amount with the sum
     * of all magazineModifier's reload multipliers.
     * @return Reload duration in ticks after modifiers.
     */
    public int getReloadDuration()
    {
        final int reloadDuration = super.getReloadDuration();
        final int modifiedReloadDuration;
        double reloadMultiplier = 1.0;
        
        for (MagazineModifier mod : super.getMagazineModifiers())
            reloadMultiplier += mod.getReloadSpeedMultiplier();
                    
        modifiedReloadDuration = (int)Math.round(reloadDuration * reloadMultiplier);
        
        if (modifiedReloadDuration < 1)
            return 1;
        else
            return modifiedReloadDuration;
    }
    
    public String getSoundsShoot()
    {
        return (super.getBarrel().isSilencer()) ? 
                super.getSilencedSound() : super.getShootSound();
    }
    
    public int getDelayBetweenShots()
    {
        final int shootDelay = super.getShootDelay();
        final FireMode fireMode = super.getFireMode();
        final SkeletonType weaponType = super.getWeaponType();
        
        if (shootDelay == 0 || fireMode.isAutomatic())
            return 0;
        
        else if (fireMode.isBurstFire())
            return 9 + shootDelay;
        else
            return shootDelay;
    }
    
    public int getProjectileSpeed()
    {
        return super.getWeaponType().getProjectileSpeed();
    }
    
    /**
     * Gets the projectile amount by adding all projectileModifiers.
     * @return Projectile amount after modifiers.
     */
    public int getProjectileAmount()
    {
        int projectileAmount = super.getWeaponType().getProjectileAmount();
        for (ProjectileModifier mod : super.getProjectileAmountModifiers())
        {
            projectileAmount += mod.getProjectileValue();
        }
        
        if (projectileAmount < 1)
            return 1;
        else
            return projectileAmount;
    }
    
    public String getRemovalOrDragDelay()
    {
        return super.getWeaponType().getRemovalDragDelay();
    }
}
