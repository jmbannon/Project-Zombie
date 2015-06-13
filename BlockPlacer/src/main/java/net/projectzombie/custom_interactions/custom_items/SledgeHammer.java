/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.custom_interactions.custom_items;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jbannon
 */
public class SledgeHammer extends CustomItem implements InteractableItem
{
    public SledgeHammer()
    {
        super(Material.STONE_AXE,
              Material.COAL_BLOCK,
              Material.EMERALD_BLOCK,
              275,
              5,
              1,
              8.5);
    }
    
    @Override
    public boolean blockInteract(Player player,
                            ItemStack itemInHand,
                            Block clickedBlock)
    {
        if (super.canUse(clickedBlock) > 0)
        {
            super.breakWithTool(player, itemInHand, clickedBlock);
            return true;
        }
        else
            return false;
    }
}
