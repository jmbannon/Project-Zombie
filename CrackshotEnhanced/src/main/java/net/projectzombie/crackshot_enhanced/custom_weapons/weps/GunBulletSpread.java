/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.BulletSpreadModifier;

/**
 *
 * @author jb
 */
public class GunBulletSpread extends GunModifierSet<BulletSpreadModifier>
{
    static private final double MIN_BULLET_SPREAD = 0.05;
    private final double bulletSpread;
    
    public GunBulletSpread(final GunModifier[] gunMods,
                           final double skeletonBulletSpread)
    {
        super(getBulletSpreadModifiers(gunMods));
        this.bulletSpread = getBulletSpread(gunMods, skeletonBulletSpread);
    }
    
    static
    private double getBulletSpread(final GunModifier[] gunMods,
                                   final double skeletonBulletSpread)
    {
        double bulletSpreadMult = 1.0;
        for (BulletSpreadModifier mod : getBulletSpreadModifiers(gunMods))
        {
            bulletSpreadMult += mod.getBulletSpreadMultiplier();
        }
        return Math.max(MIN_BULLET_SPREAD, skeletonBulletSpread * bulletSpreadMult);
    }
    
    /**
     * @return Returns all BulletSpreadModifiers on the gun.
     */
    static
    private ArrayList<BulletSpreadModifier> getBulletSpreadModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<BulletSpreadModifier> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof BulletSpreadModifier)
                mods.add((BulletSpreadModifier)mod);
        }
        return mods;
   }
}
