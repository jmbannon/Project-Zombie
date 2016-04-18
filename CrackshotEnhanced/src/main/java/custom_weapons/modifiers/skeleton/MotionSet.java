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

/**
 *
 * @author jb
 */
public class MotionSet extends GunModifierSet<MotionAttributes>
{
    private static final double MIN_RUNNING_SPEED_MULTIPLIER = 0.3;
    private static final double MIN_SPRINTING_SPEED_MULTIPLIER = 0.3;
    
    private static final double MIN_STANDING_BS_MULTIPLIER = 0.05;
    private static final double MIN_RUNNING_BS_MULTIPLIER = 0.05;
    private static final double MIN_SPRINTING_BS_MULTIPLIER = 0.05;
    
    
    private final double runningSpeedMultiplier;
    private final double sprintingSpeedMultiplier;
    private final double crouchingBulletSpreadMultiplier;
    private final double standingBulletSpreadMultiplier;
    private final double runningBulletSpreadMultiplier;
    private final double sprintingBulletSpreadMultiplier;
    
    private final double skeletonRunningSpeedMultiplier;
    private final double skeletonSprintingSpeedMultiplier;
    private final double skeletonCrouchingBulletSpreadMultiplier;
    private final double skeletonStandingBulletSpreadMultiplier;
    private final double skeletonRunningBulletSpreadMultiplier;
    private final double skeletonSprintingBulletSpreadMultiplier;
    
    public MotionSet(final GunModifier[] gunMods,
                    final double skeletonRunningSpeedMultiplier,
                    final double skeletonSprintingSpeedMultiplier,
                    final double skeletonCrouchingBulletSpreadMultiplier,
                    final double skeletonStandingBulletSpreadMultiplier,
                    final double skeletonRunningBulletSpreadMultiplier,
                    final double skeletonSprintingBulletSpreadMultiplier)
    {
        super("Motion");
        this.skeletonRunningSpeedMultiplier = skeletonRunningSpeedMultiplier;
        this.skeletonSprintingSpeedMultiplier = skeletonSprintingSpeedMultiplier;
        this.skeletonCrouchingBulletSpreadMultiplier = skeletonCrouchingBulletSpreadMultiplier;
        this.skeletonStandingBulletSpreadMultiplier = skeletonStandingBulletSpreadMultiplier;
        this.skeletonRunningBulletSpreadMultiplier = skeletonRunningBulletSpreadMultiplier;
        this.skeletonSprintingBulletSpreadMultiplier = skeletonSprintingBulletSpreadMultiplier;
        
        this.runningSpeedMultiplier = getTotalRunningSpeedMultiplier(gunMods, skeletonRunningSpeedMultiplier);
        this.sprintingSpeedMultiplier = getTotalSprintingSpeedMultiplier(gunMods, skeletonSprintingSpeedMultiplier);
        this.crouchingBulletSpreadMultiplier = getCrouchingBulletSpreadMultiplier(gunMods, skeletonCrouchingBulletSpreadMultiplier);
        this.standingBulletSpreadMultiplier = getStandingBulletSpreadMultiplier(gunMods, skeletonStandingBulletSpreadMultiplier);
        this.runningBulletSpreadMultiplier = getRunningBulletSpreadMultiplier(gunMods, skeletonRunningBulletSpreadMultiplier);
        this.sprintingBulletSpreadMultiplier = getSprintingBulletSpreadMultiplier(gunMods, skeletonSprintingBulletSpreadMultiplier);
    }
    
    public double getTotalRunningSpeedMultiplier()          { return runningSpeedMultiplier;  }
    public double getTotalSprintingSpeedMultiplier()        { return sprintingSpeedMultiplier;  }
    public double getTotalCrouchingBulletSpreadMultiplier() { return crouchingBulletSpreadMultiplier;  }
    public double getTotalStandingBulletSpreadMultiplier()  { return standingBulletSpreadMultiplier;  }
    public double getTotalRunningBulletSpreadMultiplier()   { return runningBulletSpreadMultiplier;  }
    public double getTotalSprintingBulletSpreadMultiplier() { return sprintingBulletSpreadMultiplier;  }
    
    /**
     * TODO, may need to add current durability.
     * @return 
     */
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        stats.add(getMultiplierStat(runningSpeedMultiplier, "total running speed"));
        stats.add(getMultiplierStat(sprintingSpeedMultiplier, "total sprinting speed"));
        stats.add(getMultiplierStat(crouchingBulletSpreadMultiplier, "total bullet spread when crouching"));
        stats.add(getMultiplierStat(standingBulletSpreadMultiplier, "total bullet spread when standing"));
        stats.add(getMultiplierStat(runningBulletSpreadMultiplier, "total bullet spread when running"));
        stats.add(getMultiplierStat(sprintingBulletSpreadMultiplier, "total bullet spread when sprinting"));
        stats.add(STAT_SEPERATOR);
        stats.add(getMultiplierStat(skeletonRunningSpeedMultiplier, "stock running speed"));
        stats.add(getMultiplierStat(skeletonSprintingSpeedMultiplier, "stock sprinting speed"));
        stats.add(getMultiplierStat(skeletonCrouchingBulletSpreadMultiplier, "stock bullet spread when crouching"));
        stats.add(getMultiplierStat(skeletonStandingBulletSpreadMultiplier, "stock bullet spread when standing"));
        stats.add(getMultiplierStat(skeletonRunningBulletSpreadMultiplier, "stock bullet spread when running"));
        stats.add(getMultiplierStat(skeletonSprintingBulletSpreadMultiplier, "stock bullet spread when sprinting"));
        
        return stats;
    }
    
    @Override
    public boolean hasStats()
    {
        return true; // Every gun has motion stats
    }
    
    static
    private double getTotalRunningSpeedMultiplier(final GunModifier[] gunMods,
                                                  double skeletonRunningSpeedMultiplier)
    {
        for (MotionAttributes mod : getSpeedModifiers(gunMods))
            skeletonRunningSpeedMultiplier += mod.getRunningSpeedMultiplier();
        
        return Math.max(MIN_RUNNING_SPEED_MULTIPLIER, skeletonRunningSpeedMultiplier);
    }
    
    static
    private double getTotalSprintingSpeedMultiplier(final GunModifier[] gunMods,
                                                    double skeletonSprintingSpeedMultiplier)
    {
        for (MotionAttributes mod : getSpeedModifiers(gunMods))
            skeletonSprintingSpeedMultiplier += mod.getSprintingSpeedMultiplier();
        
        return Math.max(MIN_SPRINTING_SPEED_MULTIPLIER, skeletonSprintingSpeedMultiplier);
    }
    
    static
    private double getCrouchingBulletSpreadMultiplier(final GunModifier[] gunMods,
                                                     double skeletonCrouchingBSMultiplier)
    {
        for (MotionAttributes mod : getSpeedModifiers(gunMods))
            skeletonCrouchingBSMultiplier += mod.getStandingBulletSpreadMultiplier();
        
        return Math.max(MIN_STANDING_BS_MULTIPLIER, skeletonCrouchingBSMultiplier);
    }
    
    static
    private double getStandingBulletSpreadMultiplier(final GunModifier[] gunMods,
                                                     double skeletonStandingBSMultiplier)
    {
        for (MotionAttributes mod : getSpeedModifiers(gunMods))
            skeletonStandingBSMultiplier += mod.getStandingBulletSpreadMultiplier();
        
        return Math.max(MIN_STANDING_BS_MULTIPLIER, skeletonStandingBSMultiplier);
    }
    
    static
    private double getRunningBulletSpreadMultiplier(final GunModifier[] gunMods,
                                                    double skeletonRunningBSMultiplier)
    {
        for (MotionAttributes mod : getSpeedModifiers(gunMods))
            skeletonRunningBSMultiplier += mod.getRunningBulletSpreadMultiplier();
        
        return Math.max(MIN_RUNNING_BS_MULTIPLIER, skeletonRunningBSMultiplier);
    }
    
    static
    private double getSprintingBulletSpreadMultiplier(final GunModifier[] gunMods,
                                                      double sprintingBSMultiplier)
    {
        for (MotionAttributes mod : getSpeedModifiers(gunMods))
            sprintingBSMultiplier += mod.getSprintingBulletSpreadMultiplier();
        
        return Math.max(MIN_SPRINTING_BS_MULTIPLIER, sprintingBSMultiplier);
    }
    
    static
    private ArrayList<MotionAttributes> getSpeedModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<MotionAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof MotionAttributes)
                mods.add((MotionAttributes)mod);
        }
        return mods;
    }
}
