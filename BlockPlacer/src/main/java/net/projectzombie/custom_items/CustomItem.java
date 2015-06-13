/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.custom_items;

import net.projectzombie.listeners.Utilities;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jbannon
 */
public abstract class CustomItem {
    
    private final Material itemType, blockTypeOne, blockTypeTwo;
    private final short maxDurability, blockWear, entityWear;
    private final double entityDamage;
    
    CustomItem(final Material itemType,
                final Material blockType,
                final Material additionalBlockType,
                final int maxDurability,
                final int blockWear,
                final int entityWear,
                final double entityDamage)
    {
        this.itemType = itemType;
        this.blockTypeOne = blockType;
        this.blockTypeTwo = additionalBlockType;
        this.maxDurability = (short)maxDurability;
        this.blockWear = (short)blockWear;
        this.entityWear = (short) entityWear;
        this.entityDamage = entityDamage;
    }
    
    public boolean damageEntityOnHit(final Player player,
                                     final ItemStack customItem,
                                     final Entity entity)
    {
        if (entity instanceof LivingEntity)
        {
            LivingEntity temp = (LivingEntity)entity;
            temp.damage(this.entityDamage);
            if (this.entityWear(customItem))
                player.getInventory().remove(customItem);
            return true;
        }
        else
            return false;
    }
    
    protected boolean isInteractable(final ItemStack tool,
                                     final Block clickedBlock)
    {
        return isTool(tool) && canUse(clickedBlock) > 0;
    }
    
    protected void breakWithTool(final Player player,
                               final ItemStack inHand,
                               final Block clickedBlock)
    {
        if (Utilities.writeToBuffer(clickedBlock))
        {
            if (this.breakBlock(inHand, clickedBlock))
                player.getInventory().remove(inHand);
        }
        else
            player.sendMessage("An error has occured. Please consult an admin.");
    }
    
    /**
     * Checks to see if the tool can break the block. If it can it decreases
     * its durability.
     * 
     * @param tool Item in hand
     * @param clickedBlock Event clicked block
     * @return True if it tool should be removed.
     */
    protected boolean breakBlock(final ItemStack tool,
                                 final Block clickedBlock)
    {
        if (canUse(clickedBlock) != 0)
        {
            clickedBlock.breakNaturally();
            return blockWear(tool);
        }
        return false;
    }
    
    public boolean isTool(final ItemStack inHand)
    {
        return this.itemType.equals(inHand.getType());
    }
    
    protected boolean entityWear(final ItemStack tool)
    {
        tool.setDurability((short)(tool.getDurability() + this.entityWear));
        return tool.getDurability() > this.maxDurability;
    }
    
    protected boolean blockWear(final ItemStack tool)
    {
        tool.setDurability((short)(tool.getDurability() + this.blockWear));
        return tool.getDurability() > this.maxDurability;
    }
    
    protected int canUse(final Block clickedBlock)
    {
        Material clickedType = clickedBlock.getType();
        if (clickedType == null)
            return 0;
        else if (clickedType.equals(blockTypeOne))
            return 1;
        else if (clickedType.equals(this.blockTypeTwo))
            return 2;
        else
            return 0;
    }
    
    protected Material getMaterial()
    {
        return this.itemType;
    }
}
