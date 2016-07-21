package net.projectzombie.survivalteams.file.buffers;

import net.projectzombie.survivalteams.block.SurvivalWeapon;
import net.projectzombie.survivalteams.file.FileRead;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by maxgr on 7/20/2016.
 */
public class SBWeaponBuffer
{
    private static Map<Material, SurvivalWeapon> weapons;
    private static int defaultDamage;
    private static int defaultDurability;

    public static SurvivalWeapon getWeapon(Material material)
    {
        return weapons.get(material);
    }

    public static SurvivalWeapon getWeaponDamage(Material material)
    {
        return weapons.get(material);
    }

    public static int getDefaultDamage()
    {
        return defaultDamage;
    }

    public static int getDefaultDurability()
    {
        return defaultDurability;
    }

    public static Set<Material> getWeapons()
    {
        return weapons.keySet();
    }

    public static void readInDefaults()
    {
        defaultDamage = FileRead.getDefaultDamage();
        defaultDurability = FileRead.getSBDefaultDurability();

        //Read in weapons.
        if (weapons == null)
            weapons = new HashMap<Material, SurvivalWeapon>();
        Set<String> tWeapons = FileRead.getSBWeapons();
        if (tWeapons != null)
        {
            for (String tWeapon : tWeapons)
            {
                int damage = FileRead.getSBWeaponDamage(Material.valueOf(tWeapon));
                int durability = FileRead.getSBWeaponDurability(Material.valueOf(tWeapon));
                weapons.put(Material.valueOf(tWeapon), new SurvivalWeapon(durability, damage));
            }
        }
    }
}
