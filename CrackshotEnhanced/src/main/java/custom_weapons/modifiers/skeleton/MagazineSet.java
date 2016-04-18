/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_weapons.modifiers.skeleton;

import java.util.ArrayList;
import custom_weapons.modifiers.GunModifier;
import custom_weapons.modifiers.GunModifierSet;
import static custom_weapons.modifiers.ModifierLoreBuilder.STAT_SEPERATOR;
import static custom_weapons.modifiers.ModifierLoreBuilder.getMultiplierStat;
import static custom_weapons.modifiers.ModifierLoreBuilder.getValueStat;

/**
 *
 * @author jb
 */
public class MagazineSet extends GunModifierSet<MagazineAttributes>
{
    static private final double MIN_RELOAD_SPEED = 1.0;
    
    private final int extraMagSize;
    private final int skeletonMagSize;
    private final int totalMagSize;
    private final double reloadSpeed;
    
    public MagazineSet(final GunModifier[] gunMods,
                       final int skeletonMagSize,
                       final double skeletonReloadDuration)
    {
        super("Magazine");
        this.skeletonMagSize = skeletonMagSize;
        this.extraMagSize = getMagazineSize(gunMods, skeletonMagSize);
        this.reloadSpeed = getReloadSpeed(gunMods, skeletonReloadDuration);
        this.totalMagSize = skeletonMagSize + extraMagSize;
    }
    
    public int getMagazineSize()   { return extraMagSize; }
    public double getReloadDuration() { return reloadSpeed;  }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getValueStat(totalMagSize, "total magazine size"));
        stats.add(STAT_SEPERATOR);
        stats.add(getValueStat(extraMagSize, "extra mag size"));
        stats.add(getValueStat(skeletonMagSize, "stock mag size"));
        return stats;
    }

    @Override
    public boolean hasStats()
    {
        return extraMagSize > 0 && reloadSpeed > 0;
    }
    
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
