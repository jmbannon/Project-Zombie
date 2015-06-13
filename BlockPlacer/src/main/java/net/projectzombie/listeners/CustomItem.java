/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jbannon
 */
public enum CustomItem {
    
    WireCutter(Material.SHEARS, 
               Material.IRON_FENCE,
               Material.IRON_DOOR_BLOCK,
               239, 6, 1, 8.5),
    
    Chainsaw(Material.DIAMOND_SWORD,
             Material.HARD_CLAY,
             null,
             1562, 5, 10, 8.5),
    
    SledgeHammer(Material.STONE_AXE,
                 Material.COAL_BLOCK,
                 Material.EMERALD_BLOCK,
                 275, 5, 1, 8.5),
    
    Engraver(Material.NAME_TAG,
             null, null, 0, 0, 0, 0);
    
    private final Material itemType, blockType, additionalBlockType;
    private final short maxDurability, blockWear, entityWear;
    private final double entityDamage;
   
    private CustomItem(final Material itemType,
                       final Material blockType,
                       final Material additionalBlockType,
                       final int maxDurability,
                       final int blockWear,
                       final int entityWear,
                       final double entityDamage)
    {
        this.itemType = itemType;
        this.maxDurability = (short)maxDurability;
        this.blockType = blockType;
        this.additionalBlockType = additionalBlockType;
        this.blockWear = (short)blockWear;
        this.entityWear = (short) entityWear;
        this.entityDamage = entityDamage;
    }
    
    /**
     * Checks to see if the tool can break the block. If it can it decreases
     * its durability.
     * 
     * @param tool Item in hand
     * @param clickedBlock Event clicked block
     * @return True if it tool should be removed.
     */
    public boolean breakBlock(final ItemStack tool,
                              final Block clickedBlock)
    {
        if (canUse(clickedBlock) != 0)
        {
            clickedBlock.breakNaturally();
            return decreaseDurability(tool);
        }
        return false;
    }
    
    public boolean isTool(final ItemStack inHand)
    {
        return this.itemType.equals(inHand.getType());
    }
    
    public boolean decreaseDurability(final ItemStack tool)
    {
        tool.setDurability((short)(tool.getDurability() + this.blockWear));
        return tool.getDurability() > this.maxDurability;
    }
    
    public int canUse(final Block clickedBlock)
    {
        Material clickedType = clickedBlock.getType();
        if (clickedType.equals(this.blockType))
            return 1;
        else if (clickedType.equals(this.additionalBlockType) && clickedType != null)
            return 2;
        else
            return 0;
    }
    
    public Material getMaterial()
    {
        return this.itemType;
    }

    public static CustomItem getCustomItem(final ItemStack inHand)
    {
        final Material itemType = inHand.getType();
        
        for (CustomItem tool : CustomItem.values())
            if (itemType.equals(tool.getMaterial()))
                return tool;
        
        return null;
    }
}
