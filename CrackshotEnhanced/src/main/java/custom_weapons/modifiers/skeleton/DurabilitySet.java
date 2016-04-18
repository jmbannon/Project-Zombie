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
public class DurabilitySet extends GunModifierSet<DurabilityAttributes>
{
    private static final int MAX_DURABILITY = 5;
    
    private final int extraDurability;
    private final double durabilityMultiplier;
    private final int skeletonDurability;
    private final int totalDurability;
    
    public DurabilitySet(final GunModifier[] gunMods,
                         final int skeletonMaxDurability)
    {
        super("Durability");
        this.durabilityMultiplier = getDurabilityMultiplier(gunMods);
        this.extraDurability = getDurabilityModifier(gunMods);
        this.skeletonDurability = skeletonMaxDurability;
        this.totalDurability = (int)Math.max(MAX_DURABILITY, Math.round((skeletonMaxDurability + extraDurability) * durabilityMultiplier));
    }
    
    public double getTotalDurability() { return totalDurability;  }
    
    /**
     * TODO, may need to add current durability.
     * @return 
     */
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        stats.add(getValueStat(totalDurability, "total max durability"));
        stats.add(STAT_SEPERATOR);
        stats.add(getValueStat(extraDurability, "extra durability"));
        stats.add(getValueStat(skeletonDurability, "max stock durability"));
        stats.add(getMultiplierStat(durabilityMultiplier, "durability"));
        
        return stats;
    }
    
    @Override
    public boolean hasStats()
    {
        return true; // Every gun has durability
    }
    
    static
    private double getDurabilityMultiplier(final GunModifier[] gunMods)
    {
        double boltDurationMultiplier = 1.0;
        for (DurabilityAttributes mod : getDurabilityModifiers(gunMods))
        {
            boltDurationMultiplier += mod.getDurabilityMultiplier();
        }
        return Math.max(0.0, boltDurationMultiplier);
    }
    
    static
    private int getDurabilityModifier(final GunModifier[] gunMods)
    {
        int durabilityModifier = 0;
        for (DurabilityAttributes mod : getDurabilityModifiers(gunMods))
        {
            durabilityModifier += mod.getDurabilityModifier();
        }
        return Math.max(0, durabilityModifier);
    }
    
    static
    private ArrayList<DurabilityAttributes> getDurabilityModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<DurabilityAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof DurabilityAttributes)
                mods.add((DurabilityAttributes)mod);
        }
        return mods;
    }
}
