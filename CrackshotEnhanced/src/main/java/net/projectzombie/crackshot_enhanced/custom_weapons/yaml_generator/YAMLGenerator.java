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
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment.INCENDIARY;
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
    private final GunGenerator gen;
    
    private YAMLGenerator(final CrackshotGun gun)
    {
        this.gen = new GunGenerator(gun);
        
    }
    
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
        YAMLGenerator gunGen = new YAMLGenerator(gun);
        gunGen.writeItemInformation();
        gunGen.writeShooting();
        gunGen.writeScope();
        gunGen.writeSneak();
        gunGen.writeBurstfire();
        gunGen.writeFullyAutomatic();
        gunGen.writeReload();
        gunGen.writeFirearmAction();
        gunGen.writeHeadshot();
        gunGen.writeAmmo();
        gunGen.writeHitEvents();
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
    
    
    private void writeItemInformation()
    {
        final String path = gen.getCSWeaponName() + ".Item_Information.";
        
        wepsYAML.set(path + "Item_Name",         gen.getItemName());
        wepsYAML.set(path + "Item_Type",         gen.getItemType());
        wepsYAML.set(path + "Item_Lore",         gen.getItemLore());
        wepsYAML.set(path + "Inventory_Control", gen.getInventoryControl());
        wepsYAML.set(path + "Sounds_Acquired",   "BAT_TAKEOFF-1-1-0");
        wepsYAML.set(path + "Skip_Name_check",    false);
        wepsYAML.set(path + "Remove_Unused_Tag",  true);
    }
    
    
    private void writeShooting()
    {
        final String path = gen.getCSWeaponName() + ".Shooting.";
        
        wepsYAML.set(path + "Right_Click_To_Shoot",            true);
        wepsYAML.set(path + "Cancel_Left_Click_Block_Damage",  true);
        wepsYAML.set(path + "Cancel_Right_Click_Interactions", true);
        wepsYAML.set(path + "Delay_Between_Shots",             gen.getDelayBetweenShots());
        wepsYAML.set(path + "Recoil_Amount",                   gen.getSkeleton().getRecoil());
        wepsYAML.set(path + "Projectile_Amount",               gen.getProjectileAmount());
        wepsYAML.set(path + "Projectile_Type",                 "snowball");
        wepsYAML.set(path + "Projectile_Speed",                gen.getProjectileSpeed());
        wepsYAML.set(path + "Projectile_Damage",               gen.getDamage());
        wepsYAML.set(path + "Removal_Or_Drag_Delay",           gen.getRemovalOrDragDelay());
        wepsYAML.set(path + "Bullet_Spread",                   gen.getGun().getCSBulletSpread());
        wepsYAML.set(path + "Sounds_Shoot",                    gen.getSoundsShoot());
        
        if (gen.getGun().getAttatchment().equals(INCENDIARY))
            this.writeProjectileIncendiary();
    }
    
    private void writeScope()
    {
        if (!GunUtils.hasScope(gen.getGun())) return;
        
        final String path = gen.getCSWeaponName() + ".Scope.";
        
        wepsYAML.set(path + "Enable",             true);
        wepsYAML.set(path + "Zoom_Amount",        gen.getGun().getScope().getZoomAmount());
        wepsYAML.set(path + "Zoom_Bullet_Spread", gen.getGun().getInitBulletSpread());
        wepsYAML.set(path + "Sounds_Toggle_Zoom", "NOTE_STICKS-1-2-0");
    }
    
    
    private void writeSneak()
    {
        if (GunUtils.hasScope(gen.getGun())) return;
        
        final String path = gen.getCSWeaponName() + ".Sneak.";
        
        wepsYAML.set(path + "Enable",        true);
        wepsYAML.set(path + "Bullet_Spread", gen.getGun().getSneakCSBulletSpread());
    }
    
    private void writeBurstfire()
    {
        if (!GunUtils.isBurstFire(gen.getGun())) return;
        
        final String path = gen.getCSWeaponName() + ".Burstfire.";
        
        wepsYAML.set(path + "Enable",                       true);
        wepsYAML.set(path + "Shots_Per_Burst",              3);
        wepsYAML.set(path + "Delay_Between_Shots_In_Burst", 3);
    }
    
    
    private void writeFullyAutomatic()
    {
        if (!GunUtils.isAutomatic(gen.getGun())) return;
        final String path = gen.getCSWeaponName() + ".Fully_Automatic.";
        
        wepsYAML.set(path + "Enable",    true);
        wepsYAML.set(path + "Fire_Rate", 3);
    }
    
    private void writeReload()
    {
        final String path = gen.getCSWeaponName() + ".Reload.";
        
        wepsYAML.set(path + "Enable",                      true);
        wepsYAML.set(path + "Reload_Amount",               gen.getReloadAmount());
        wepsYAML.set(path + "Reload_Duration",             gen.getReloadDuration());
        wepsYAML.set(path + "Take_Ammo_On_Reload",         true);
        wepsYAML.set(path + "Reload_Bullets_Individually", gen.reloadBulletsIndividually());
        wepsYAML.set(path + "Sounds_Out_Of_Ammo",          "ITEM_BREAK-1-1-0");
        wepsYAML.set(path + "Sounds_Reloading",            gen.getSkeleton().getReloadSound());
    }
    
    private void writeFirearmAction()
    {
        final FirearmAction action = gen.getSkeleton().getWeaponType().getAction();
        final String path = gen.getCSWeaponName() + ".Firearm_Action.";
        
        if (action == null) return;

        wepsYAML.set(path + "Type",              action.toString());
        wepsYAML.set(path + "Open_Duration",     gen.getOpenDuration());
        wepsYAML.set(path + "Close_Duration",    gen.getCloseDuration());
        wepsYAML.set(path + "Close_Shoot_Delay", gen.getCloseShootDelay());
        wepsYAML.set(path + "Sound_Open",        action.getSoundOpen());
        wepsYAML.set(path + "Sound_Close",       action.getSoundClose());
    }
    

    private void writeHeadshot()
    {
        final String path = gen.getCSWeaponName() + ".Headshot.";
        
        wepsYAML.set(path + "Enable",        true);
        wepsYAML.set(path + "Bonus_Damage",  gen.getSkeleton().getDamage() * 2);
        wepsYAML.set(path + "Sounds_Victim", "VILLAGER_NO-1-1-0");
    }
    
    private void writeAmmo()
    {
        final String path = gen.getCSWeaponName() + ".Ammo.";
        
        wepsYAML.set(path + "Enable",       true);
        wepsYAML.set(path + "Ammo_Item_ID", gen.getAmmoID());
    }
    
    private void writeHitEvents()
    {
        final String path = gen.getCSWeaponName() + ".Hit_Events.";
        
        wepsYAML.set(path + "Enable",        true);
        wepsYAML.set(path + "Sounds_Victim", "VILLAGER_IDLE-1-1-0");
    }
    
    private void writeProjectileIncendiary()
    {
        final String path = gen.getCSWeaponName() + ".Shooting.Projectile_Incendiary.";
        
        wepsYAML.set(path + "Enable",   true);
        wepsYAML.set(path + "Duration", 3);
    }
    
}
