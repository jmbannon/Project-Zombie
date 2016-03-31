/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.MagazineModifier;

/**
 *
 * @author jb
 */
public class GunMagazine extends GunModifierSet<MagazineModifier>
{
    static private final double MIN_RELOAD_SPEED = 1.0;
    
    private final int magazineSize;
    private final double reloadSpeed;
    
    public GunMagazine(final GunModifier[] gunMods,
                       final int skeletonMagSize,
                       final double skeletonReloadDuration)
    {
        super(getMagazineModifiers(gunMods));
        this.magazineSize = getMagazineSize(gunMods, skeletonMagSize);
        this.reloadSpeed = getReloadSpeed(gunMods, skeletonReloadDuration);
    }
    
    static
    private double getReloadSpeed(final GunModifier[] gunMods,
                                  double skeletonReloadDuration)
    {
        double reloadDurationMultiplier = 1.0;
        for (MagazineModifier mod : getMagazineModifiers(gunMods))
        {
            reloadDurationMultiplier += mod.getReloadSpeedMultiplier();
        }
        return Math.max(MIN_RELOAD_SPEED, skeletonReloadDuration * reloadDurationMultiplier);
    }
    
    static
    private int getMagazineSize(final GunModifier[] gunMods,
                                      int skeletonMagSize)
    {
        double magSizeMultiplier = 1.0;
        for (MagazineModifier mod : getMagazineModifiers(gunMods))
        {
            skeletonMagSize += mod.getMagazineSizeModifier();
            magSizeMultiplier += mod.getMagazineSizeMultiplier();
        }
        return Math.max(1, (int)Math.round((double)skeletonMagSize * magSizeMultiplier));
    }
    
    static
    private ArrayList<MagazineModifier> getMagazineModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<MagazineModifier> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof MagazineModifier)
                mods.add((MagazineModifier)mod);
        }
        return mods;
    }
}
