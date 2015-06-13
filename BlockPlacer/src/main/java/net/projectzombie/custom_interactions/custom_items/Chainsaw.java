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
public class Chainsaw extends CustomItem implements InteractableItem
{

    public Chainsaw()
    {
        super(Material.DIAMOND_SWORD,
              Material.HARD_CLAY,
              null,
              1562,
              5,
              10,
              8.5);
    }
    
    @Override
    public boolean blockInteract(Player player,
                            ItemStack itemInHand,
                            Block clickedBlock)
    {
        if (super.canUse(clickedBlock) == 1)
        {
            super.breakWithTool(player, itemInHand, clickedBlock);
            return true;
        }
        else
            return false;
    }
    
}
