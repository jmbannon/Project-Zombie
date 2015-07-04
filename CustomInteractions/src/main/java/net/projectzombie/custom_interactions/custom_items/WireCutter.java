/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.custom_interactions.custom_items;

import net.projectzombie.custom_interactions.listeners.Utilities;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

/**
 *
 * @author jbannon
 */
public class WireCutter extends CustomItem implements InteractableItem
{

    public WireCutter()
    {
        super(Material.SHEARS,
              Material.IRON_FENCE,
              Material.IRON_DOOR_BLOCK,
              239,
              6,
              1,
              8.5);
    }
    
    @Override
    public boolean blockInteract(Player player, ItemStack inHand, Block clickedBlock)
    {
        if (super.canUse(clickedBlock) == 1)
        {
            super.breakWithTool(player, inHand, clickedBlock);
        }
        else if (super.canUse(clickedBlock) == 2)
        {
            this.tempOpenDoor(clickedBlock);
            super.blockWear(inHand);
        }
        else
            return false;
        
        // If it interacted with the clicked block.
        return true;
    }
    
    private void tempOpenDoor(final Block clickedBlock)
    {
        final Block door = clickedBlock.getRelative(BlockFace.DOWN).getType().equals(Material.IRON_DOOR_BLOCK) ?
                    clickedBlock.getRelative(BlockFace.DOWN) : clickedBlock;
        final BlockState doorState = door.getState();
        final MaterialData doorData = doorState.getData();
        final Openable opn = (Openable) doorData;

        if (opn.isOpen())
            return;

        opn.setOpen(true);
        doorState.setData(doorData);
        doorState.update();
        clickedBlock.getWorld().spigot().playEffect(clickedBlock.getLocation(),
                                                    Effect.DOOR_TOGGLE);

        Utilities.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Utilities.plugin, new Runnable()
        {
            @Override
            public void run()
            {
                opn.setOpen(false);
                doorState.setData(doorData);
                doorState.update();
                clickedBlock.getWorld().spigot().playEffect(clickedBlock.getLocation(),
                                                            Effect.DOOR_TOGGLE);
            }
        }, 120);
    }
    
}
