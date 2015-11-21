/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.yaml_generator;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment.EXT;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment.INC;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment.LUB;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment.REL;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment.SO;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireMode;
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
            return (gun.getAttatchment().equals(LUB)) ? openDuration/2 : openDuration;
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
            return (gun.getAttatchment().equals(LUB)) ? closeDuration/2 : closeDuration;
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
            return (gun.getAttatchment().equals(LUB)) ? closeShootDelay/2 : closeShootDelay;
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
        final int reloadAmount = gun.getSkeleton().getReloadAmount();
        return (gun.getAttatchment().equals(EXT)) ? (reloadAmount*5)/3 : reloadAmount;
    }
    
    public int getReloadDuration()
    {
        final int reloadDuration = gun.getSkeleton().getReloadDuration();
        return (gun.getAttatchment().equals(REL)) ? reloadDuration/2 : reloadDuration;
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
        return (gun.getAttatchment().equals(Attatchment.SUP)) ? 
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
        final int projectileAmount = gun.getWeaponType().getProjectileAmount();
        return (GunUtils.isShotgun(gun) && gun.getAttatchment().equals(SO)) ?
            projectileAmount*2 : projectileAmount;
    }
    
    public String getRemovalOrDragDelay()
    {
        if (GunUtils.isShotgun(gun))
            return (gun.getAttatchment().equals(SO)) ? "7-true" : "13-true";
        else
            return null;
    }
    
    public int getDamage()
    {
        final int damage = gun.getSkeleton().getDamage();
        return (gun.getAttatchment().equals(INC)) ? (damage * 4)/3 : damage;
    }
}
