/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.yaml_generator;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment.INC;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.FirearmAction;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.GunUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public class YAMLGenerator
{
    static private File weps;
    static private FileConfiguration wepsYAML;
    
    private YAMLGenerator() { /* Do nothing */ }
    
    static
    public void generateDefaultWeapons(final Plugin plugin)
    {
        loadWeapons(plugin);
        
        for (CrackshotGun gun : Guns.getGuns())
            writeWeapon(gun);
        
        saveWeapons();
    }
    
    static
    private void writeWeapon(final CrackshotGun gun)
    {
        writeItemInformation(gun);
        writeShooting(gun);
        writeScope(gun);
        writeSneak(gun);
        writeBurstfire(gun);
        writeFullyAutomatic(gun);
        writeReload(gun);
        writeFirearmAction(gun);
        writeHeadshot(gun);
        writeAmmo(gun);
        writeHitEvents(gun);
    }
    
    static
    private void writeItemInformation(final CrackshotGun gun)
    {
        final String path = gun.getCSWeaponName() + ".Item_Information.";
        
        wepsYAML.set(path + "Item_Name",         ItemInformationGen.getItemName(gun));
        wepsYAML.set(path + "Item_Type",         ItemInformationGen.getItemType(gun));
        wepsYAML.set(path + "Item_Lore",         ItemInformationGen.getItemLore(gun));
        wepsYAML.set(path + "Inventory_Control", ItemInformationGen.getInventoryControl(gun));
        wepsYAML.set(path + "Sounds_Acquired",   "BAT_TAKEOFF-1-1-0");
        wepsYAML.set(path + "Skip_Name_check",    false);
        wepsYAML.set(path + "Remove_Unused_Tag",  true);
    }
    
    static
    private void writeShooting(final CrackshotGun gun)
    {
        final String path = gun.getCSWeaponName() + ".Shooting.";
        
        wepsYAML.set(path + "Right_Click_To_Shoot",            true);
        wepsYAML.set(path + "Cancel_Left_Click_Block_Damage",  true);
        wepsYAML.set(path + "Cancel_Right_Click_Interactions", true);
        wepsYAML.set(path + "Delay_Between_Shots",             ShootingGen.getDelayBetweenShots(gun));
        wepsYAML.set(path + "Recoil_Amount",                   gun.getSkeleton().getRecoil());
        wepsYAML.set(path + "Projectile_Amount",               ShootingGen.getProjectileAmount(gun));
        wepsYAML.set(path + "Projectile_Type",                 "snowball");
        wepsYAML.set(path + "Projectile_Speed",                ShootingGen.getProjectileSpeed(gun));
        wepsYAML.set(path + "Projectile_Damage",               ShootingGen.getDamage(gun));
        wepsYAML.set(path + "Removal_Or_Drag_Delay",           ShootingGen.getRemovalOrDragDelay(gun));
        wepsYAML.set(path + "Bullet_Spread",                   gun.getCSBulletSpread());
        wepsYAML.set(path + "Sounds_Shoot",                    ShootingGen.getSoundsShoot(gun));
        
        if (gun.getAttatchment().equals(INC))
            writeProjectileIncendiary(gun);
    }
    
    static
    private void writeScope(final CrackshotGun gun)
    {
        if (!GunUtils.hasScope(gun)) return;
        
        final String path = gun.getCSWeaponName() + ".Scope.";
        
        wepsYAML.set(path + "Enable",             true);
        wepsYAML.set(path + "Zoom_Amount",        ScopeGen.getZoomAmount(gun));
        wepsYAML.set(path + "Zoom_Bullet_Spread", gun.getInitBulletSpread());
        wepsYAML.set(path + "Sounds_Toggle_Zoom", "NOTE_STICKS-1-2-0");
    }
    
    static
    private void writeSneak(final CrackshotGun gun)
    {
        if (GunUtils.hasScope(gun)) return;
        
        final String path = gun.getCSWeaponName() + ".Sneak.";
        
        wepsYAML.set(path + "Enable",        true);
        wepsYAML.set(path + "Bullet_Spread", gun.getSneakCSBulletSpread());
    }
    
    static
    private void writeBurstfire(final CrackshotGun gun)
    {
        if (!GunUtils.isBurstFire(gun)) return;
        
        final String path = gun.getCSWeaponName() + ".Burstfire.";
        
        wepsYAML.set(path + "Enable",                       true);
        wepsYAML.set(path + "Shots_Per_Burst",              3);
        wepsYAML.set(path + "Delay_Between_Shots_In_Burst", 3);
    }
    
    static
    private void writeFullyAutomatic(final CrackshotGun gun)
    {
        if (!GunUtils.isAutomatic(gun)) return;
        final String path = gun.getCSWeaponName() + ".Fully_Automatic.";
        
        wepsYAML.set(path + "Enable",    true);
        wepsYAML.set(path + "Fire_Rate", 3);
    }
    
    static
    private void writeReload(final CrackshotGun gun)
    {
        final String path = gun.getCSWeaponName() + ".Reload.";
        
        wepsYAML.set(path + "Enable",                      true);
        wepsYAML.set(path + "Reload_Amount",               ReloadGen.getReloadAmount(gun));
        wepsYAML.set(path + "Reload_Duration",             ReloadGen.getReloadDuration(gun));
        wepsYAML.set(path + "Take_Ammo_On_Reload",         true);
        wepsYAML.set(path + "Reload_Bullets_Individually", ReloadGen.reloadBulletsIndividually(gun));
        wepsYAML.set(path + "Sounds_Out_Of_Ammo",          "ITEM_BREAK-1-1-0");
        wepsYAML.set(path + "Sounds_Reloading",            gun.getSkeleton().getReloadSound());
    }
    
    static
    private void writeFirearmAction(final CrackshotGun gun)
    {
        final FirearmAction action = gun.getSkeleton().getWeaponType().getAction();
        final String path = gun.getCSWeaponName() + ".Firearm_Action.";
        
        if (action == null) return;

        wepsYAML.set(path + "Type",              action.toString());
        wepsYAML.set(path + "Open_Duration",     FirearmActionGen.getOpenDuration(gun));
        wepsYAML.set(path + "Close_Duration",    FirearmActionGen.getCloseDuration(gun));
        wepsYAML.set(path + "Close_Shoot_Delay", FirearmActionGen.getCloseShootDelay(gun));
        wepsYAML.set(path + "Sound_Open",        action.getSoundOpen());
        wepsYAML.set(path + "Sound_Close",       action.getSoundClose());
    }
    
    static
    private void writeHeadshot(final CrackshotGun gun)
    {
        final String path = gun.getCSWeaponName() + ".Headshot.";
        
        wepsYAML.set(path + "Enable",        true);
        wepsYAML.set(path + "Bonus_Damage",  gun.getSkeleton().getDamage() * 2);
        wepsYAML.set(path + "Sounds_Victim", "VILLAGER_NO-1-1-0");
    }
    
    static
    private void writeAmmo(final CrackshotGun gun)
    {
        final String path = gun.getCSWeaponName() + ".Ammo.";
        
        wepsYAML.set(path + "Enable",       true);
        wepsYAML.set(path + "Ammo_Item_ID", AmmoGen.getAmmoID(gun));
    }
    
    static
    private void writeHitEvents(final CrackshotGun gun)
    {
        final String path = gun.getCSWeaponName() + ".Hit_Events.";
        
        wepsYAML.set(path + "Enable",        true);
        wepsYAML.set(path + "Sounds_Victim", "VILLAGER_IDLE-1-1-0");
    }
    
    static
    private void writeProjectileIncendiary(final CrackshotGun gun)
    {
        final String path = gun.getCSWeaponName() + ".Shooting.Projectile_Incendiary.";
        
        wepsYAML.set(path + "Enable",   true);
        wepsYAML.set(path + "Duration", 3);
    }
    
    static
    private void loadWeapons(final Plugin plugin)
    {
        final String defaultWeaponsPath = plugin.getDataFolder().getParent() + "/CrackShot/weapons/";
        
        if (weps == null)
            weps = new File(defaultWeaponsPath, "defaultWeapons.yml");
        
        wepsYAML = new YamlConfiguration();
        wepsYAML = YamlConfiguration.loadConfiguration(weps);
        saveWeapons();
    }
    
    static
    private void saveWeapons()
    {
        if (weps == null || wepsYAML == null) return;
        
        try   { wepsYAML.save(weps); }
        catch (IOException ex)
        {
            Logger.getLogger(YAMLGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
