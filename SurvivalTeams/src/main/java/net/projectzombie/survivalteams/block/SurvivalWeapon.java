package net.projectzombie.survivalteams.block;

import org.bukkit.inventory.ItemStack;

/**
 * Created by maxgr on 7/20/2016.
 */
public class SurvivalWeapon
{
    private int hitToDurabilityRatio;
    private int hitPoints;

    public SurvivalWeapon(int hitToDurabilityRatio, int hitPoints)
    {
        this.hitToDurabilityRatio = hitToDurabilityRatio;
        this.hitPoints = hitPoints;
    }

    public int getHitToDurabilityRatio()
    {
        return hitToDurabilityRatio;
    }

    public int getHitPoints()
    {
        return hitPoints;
    }

    public void itemHit(ItemStack item)
    {
        short itemHealth = (short) (item.getDurability() + hitToDurabilityRatio);
        item.setDurability(itemHealth);
    }
}
