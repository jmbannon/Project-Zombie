/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.yaml_generator;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment.INCENDIARY;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrel;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.MagazineModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.ProjectileAmountModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.FirearmAction;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon.PISTOL;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon.REVOLVER;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.GunUtils;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.HiddenStringUtils;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.GunSkeleton;
import org.bukkit.ChatColor;

/**
 *
 * @author jesse
 */
public class GunGenerator
{
    private final CrackshotGun gun;
    private final String gunFileName;
    
    public GunGenerator(final CrackshotGun gun)
    {
        this.gun = gun;
        this.gunFileName = String.valueOf(gun.getUniqueId() + "_" + gun.getSkeleton().getFileName());
    }
    
    
    public GunSkeleton getSkeleton()
    {
        return gun.getSkeleton();
    }
    
    public CrackshotGun getGun()
    {
        return gun;
    }
    
    public String getCSWeaponName()
    {
        return gunFileName;
    }
    
    public String getAmmoID()
    {
        final Weapon weaponType = gun.getSkeleton().getWeaponType();
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
    
    public int getOpenDuration()
    {
        final FirearmAction action = gun.getWeaponType().getAction();
        if (action != null)
        {
            final int openDuration = action.getOpenDuration();
            int modifiedOpenDuration = openDuration;
            
            for (BoltModifier mod : gun.getBoltModifiers())
                modifiedOpenDuration -= Math.floor(openDuration * mod.getDurationMultiplier());
            
            if (modifiedOpenDuration <= 1)
                return 1;
            else
                return modifiedOpenDuration;
        }
        else
            return 0;
    }
    
    public int getCloseDuration()
    {
        final FirearmAction action = gun.getWeaponType().getAction();
        if (action != null)
        {
            final int closeDuration = action.getCloseDuration();
            int modifiedCloseDuration = closeDuration;
            
            for (BoltModifier mod : gun.getBoltModifiers())
                modifiedCloseDuration -= Math.floor(closeDuration * mod.getDurationMultiplier());
            
            if (modifiedCloseDuration <= 1)
                return 1;
            else
                return modifiedCloseDuration;
        }
        else
            return 0;
    }
    
    public int getCloseShootDelay()
    {
        final FirearmAction action = gun.getWeaponType().getAction();
        if (action != null)
        {
            final int closeShootDelay = action.getCloseShootDelay();
            int modifiedCloseShootDelay = closeShootDelay;
            
            for (BoltModifier mod : gun.getBoltModifiers())
                modifiedCloseShootDelay -= Math.floor(closeShootDelay * mod.getDurationMultiplier());
            
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
        final String ID = String.valueOf(gun.getUniqueId() * 13);
        final StringBuilder stb = new StringBuilder();
        
        stb.append(CrackshotLore.ITEM_COLOR);
        for (char c : ID.toCharArray())
        {
            stb.append('&');
            stb.append(c);
        }
        stb.append(CrackshotLore.ITEM_COLOR);
        stb.append(gun.getSkeleton().getName());
        return stb.toString().replace(ChatColor.COLOR_CHAR, '&');
    }
    
    public String getItemType()
    {
        final GunSkeleton base = gun.getSkeleton();
        final int materialData = base.getItemData();
        final StringBuilder stb = new StringBuilder();
        
        stb.append(base.getItemID());
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
        stbVerify.append(gun.getUniqueId());
        
        final StringBuilder stb = new StringBuilder();
        stb.append(CrackshotLore.line);
        stb.append(HiddenStringUtils.encodeString(stbVerify.toString()).replace(ChatColor.COLOR_CHAR, '&'));
        stb.append('|');
        stb.append(CrackshotLore.preShotVerification);
        
        return stb.toString();
    }
    
    public String getInventoryControl()
    {
        return (gun.getSkeleton().getWeaponType().equals(PISTOL)
                || gun.getSkeleton().getWeaponType().equals(REVOLVER)) ? 
            "Group_Sidearm" : "Group_Primary";
    }
    
    public int getReloadAmount()
    {
        int reloadAmount = gun.getSkeleton().getReloadAmount();
        for (MagazineModifier mod : gun.getMagazineModifiers())
            reloadAmount += mod.getMagazineBoost();
        
        return reloadAmount;
    }
    
    public int getReloadDuration()
    {
        final int reloadDuration = gun.getSkeleton().getReloadDuration();
        int modifiedReloadDuration = reloadDuration;
        for (MagazineModifier mod : gun.getMagazineModifiers())
            modifiedReloadDuration -= Math.floor(reloadDuration * mod.getReloadSpeedMultiplier());
        
        return modifiedReloadDuration;
    }
    
    public Boolean reloadBulletsIndividually()
    {
        final FirearmAction action = gun.getSkeleton().getWeaponType().getAction();
        return action != null &&
                (  action.equals(FirearmAction.HUNTING_BOLT)
                || action.equals(FirearmAction.PUMP)
                || action.equals(FirearmAction.BREAK));
    }
    
    
    
    public String getSoundsShoot()
    {
        final GunSkeleton base = gun.getSkeleton();
        return (gun.getAttatchment().equals(Attatchment.SUPPRESOR)) ? 
                base.getSilencedSound() : base.getShootSound();
    }
    
    public int getDelayBetweenShots()
    {
        final int shootDelay = gun.getSkeleton().getShootDelay();
        final FireMode fireMode = gun.getFireMode();
        final Weapon weaponType = gun.getSkeleton().getWeaponType();
        
        if (shootDelay == 0 || fireMode.equals(FireMode.AUTO)
                || weaponType.equals(Weapon.SNIPER)
                || weaponType.equals(Weapon.HUNTING))
            return 0;
        
        else if (fireMode.equals(FireMode.BURST))
            return 9 + shootDelay;
        else
            return shootDelay;
    }
    
    public int getProjectileSpeed()
    {
        return (GunUtils.isShotgun(gun)) ? 35 : 1000;
    }
    
    public int getProjectileAmount()
    {
        int projectileAmount = gun.getWeaponType().getProjectileAmount();
        for (ProjectileAmountModifier mod : gun.getProjectileAmountModifiers())
        {
            projectileAmount += mod.getAdditionalProjectileAmount();
        }
        return projectileAmount;
    }
    
    public String getRemovalOrDragDelay()
    {
        if (GunUtils.isShotgun(gun))
            return (gun.getBarrel().equals(Barrel.SAWED_OFF)) ? "7-true" : "13-true";
        else
            return null;
    }
    
    public int getDamage()
    {
        final int damage = gun.getSkeleton().getDamage();
        return (gun.getAttatchment().equals(INCENDIARY)) ? (damage * 4)/3 : damage;
    }
}
