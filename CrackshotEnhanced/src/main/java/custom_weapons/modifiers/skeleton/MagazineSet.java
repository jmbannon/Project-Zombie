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
public class MagazineSet extends GunModifierSet<MagazineAttributes>
{
    static private final double MIN_RELOAD_SPEED = 1.0;
    
    private final int magazineSize;
    private final double reloadSpeed;
    
    public MagazineSet(final GunModifier[] gunMods,
                       final int skeletonMagSize,
                       final double skeletonReloadDuration)
    {
        super(getMagazineModifiers(gunMods));
        this.magazineSize = getMagazineSize(gunMods, skeletonMagSize);
        this.reloadSpeed = getReloadSpeed(gunMods, skeletonReloadDuration);
    }
    
    public int getMagazineSize()   { return magazineSize; }
    public double getReloadDuration() { return reloadSpeed;  }
    
    static
    private double getReloadSpeed(final GunModifier[] gunMods,
                                  double skeletonReloadDuration)
    {
        double reloadDurationMultiplier = 1.0;
        for (MagazineAttributes mod : getMagazineModifiers(gunMods))
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
        for (MagazineAttributes mod : getMagazineModifiers(gunMods))
        {
            skeletonMagSize += mod.getMagazineSizeModifier();
            magSizeMultiplier += mod.getMagazineSizeMultiplier();
        }
        return Math.max(1, (int)Math.round((double)skeletonMagSize * magSizeMultiplier));
    }
    
    static
    private ArrayList<MagazineAttributes> getMagazineModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<MagazineAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof MagazineAttributes)
                mods.add((MagazineAttributes)mod);
        }
        return mods;
    }
}
