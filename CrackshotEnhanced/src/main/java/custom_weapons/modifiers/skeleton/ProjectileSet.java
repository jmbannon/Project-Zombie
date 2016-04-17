/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_weapons.modifiers.skeleton;

import java.util.ArrayList;
import custom_weapons.modifiers.GunModifier;
import custom_weapons.modifiers.GunModifierSet;

/**
 *
 * @author jb
 */
public class ProjectileSet extends GunModifierSet<ProjectileAttributes>
{
    static private final int MIN_PROJECTILE_SPEED = 1;
    
    private final int projectileRange;
    private final int projectileSpeed;
    private final int projectileAmount;
    
    public ProjectileSet(final GunModifier[] gunMods,
                       final int skeletonProjectileRange,
                       final int skeletonProjectileSpeed,
                       final int skeletonProjectileAmount)
    {
        super(getProjectileModifiers(gunMods));
        this.projectileRange = getProjectileRange(gunMods, skeletonProjectileRange);
        this.projectileSpeed = getProjectileSpeed(gunMods, skeletonProjectileSpeed);
        this.projectileAmount = getProjectileAmount(gunMods, skeletonProjectileAmount);
    }
    
    public int getProjectileRange()   { return projectileRange; }
    public int getProjectileSpeed()   { return projectileSpeed;  }
    public int getProjectileAmount()  { return projectileAmount; }
    
    static
    private int getProjectileSpeed(final GunModifier[] gunMods,
                                      int skeletonProjectileSpeed)
    {
        double projectileSpeedMultiplier = 1.0;
        for (ProjectileAttributes mod : getProjectileModifiers(gunMods))
        {
            projectileSpeedMultiplier += mod.getProjectileSpeedMultiplier();
        }
        return Math.max(MIN_PROJECTILE_SPEED, (int)Math.round((double)skeletonProjectileSpeed * projectileSpeedMultiplier));
    }
    
    static
    private int getProjectileRange(final GunModifier[] gunMods,
                                   int projectileRangeValue)
    {
        double projectileRangeMultiplier = 1.0;
        for (ProjectileAttributes mod : getProjectileModifiers(gunMods))
        {
            projectileRangeMultiplier += mod.getProjectileRangeMultiplier();
            projectileRangeValue += mod.getProjectileRangeValue();
        }
        return Math.max(1, (int)Math.round((double)projectileRangeValue * projectileRangeMultiplier));
    }
    
    static
    private int getProjectileAmount(final GunModifier[] gunMods,
                                    int skeletonProjectileAmount)
    {
        for (ProjectileAttributes mod : getProjectileModifiers(gunMods))
        {
            skeletonProjectileAmount += mod.getProjectileAmount();
        }
        return Math.max(1, skeletonProjectileAmount);
    }
    
    static
    private ArrayList<ProjectileAttributes> getProjectileModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<ProjectileAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof MagazineAttributes)
                mods.add((ProjectileAttributes)mod);
        }
        return mods;
    }
}
