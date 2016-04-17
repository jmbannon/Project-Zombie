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
import static custom_weapons.utilities.Constants.TPS;

/**
 *
 * @author jb
 */
public class BoltSet extends GunModifierSet<BoltAttributes>
{
    private final double boltDurationMultiplier;
    private final double skeletonBoltActionDurationInTicks;
    
    public BoltSet(final GunModifier[] gunMods,
                   final double skeletonBoltActionDurationInTicks)
    {
        super("Bolt Action");
        this.boltDurationMultiplier = getBoltDurationMultiplier(gunMods);
        this.skeletonBoltActionDurationInTicks = skeletonBoltActionDurationInTicks;
    }
    
    public double getBoltDurationMultiplier() { return boltDurationMultiplier;  }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        final double newBoltSpeed = skeletonBoltActionDurationInTicks * boltDurationMultiplier;
        
        stats.add(getValueStat(newBoltSpeed / TPS, "bolt action duration in seconds"));
        stats.add(STAT_SEPERATOR);
        stats.add(getMultiplierStat(boltDurationMultiplier, "bolt action duration"));
        stats.add(getValueStat(skeletonBoltActionDurationInTicks / TPS, "stock bolt action duration"));
        
        return stats;
    }
    
    @Override
    public boolean hasStats()
    {
        return boltDurationMultiplier > 0 && skeletonBoltActionDurationInTicks > 0;
    }
    
    static
    private double getBoltDurationMultiplier(final GunModifier[] gunMods)
    {
        double boltDurationMultiplier = 1.0;
        for (BoltAttributes mod : getBoltModifiers(gunMods))
        {
            boltDurationMultiplier += mod.getBoltDurationMultiplier();
        }
        return Math.max(0.0, boltDurationMultiplier);
    }
    
    static
    private ArrayList<BoltAttributes> getBoltModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<BoltAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof BoltAttributes)
                mods.add((BoltAttributes)mod);
        }
        return mods;
    }
}
