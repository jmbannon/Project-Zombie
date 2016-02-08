/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.custom_interactions.temp;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author jesse
 */
public class TreeSnow implements CommandExecutor
{
    public TreeSnow() { /* Do nothing. */ }
    
    @Override
    public boolean onCommand(CommandSender cs,
                             Command cmnd,
                             String string,
                             String[] args)
    {
        final Player sender;
        if (cs instanceof Player)
            sender = (Player)cs;
        else
            return true;
        
        if (sender.isOp() && args.length >= 2 && args[0].equalsIgnoreCase("snow"))
        {
            final int rad = Integer.valueOf(args[1]);
            if (rad <= 0)
                sender.sendMessage("Must specify radius > 0");
            else
            {
                sender.sendMessage("Let it snow!");
                treeSnow(sender, rad);
            }
        }
        return true;
    }
    
    static public void treeSnow(final Player player,
                                final int radius)
    {
        final Block playerBlock = player.getLocation().getBlock();
        
        final int rad2 = radius / 2;
        
        final int x1 = -rad2;
        final int z1 = -rad2;
        final int y1 = -player.getLocation().getBlockY();
        
        final int x2 = +rad2;
        final int z2 = +rad2;
        final int y2 = 0;
        
        
        for (int i = x1; i <= x2; i++)
        {
            for (int j = y1; j < y2; j++)
            {
                for (int k = z1; k <= z2; k++)
                {
                    checkAndPlaceSnow(playerBlock.getRelative(i, j, k));
                }
            }
        }
    }
    
    static private boolean isLeaves(final Material type)
    {
        return type.equals(Material.LEAVES)
                || type.equals(Material.LEAVES_2);
    }
    
    static private boolean hasOpenBlockFaceUp(final Block block)
    {
        return block.getRelative(BlockFace.UP).isEmpty();
    }
    
    static private void checkAndPlaceSnow(final Block block)
    {
        if (isLeaves(block.getType()) && hasOpenBlockFaceUp(block))
            placeSnow(block);
    }
    
    static private void placeSnow(final Block block)
    {
        block.getRelative(BlockFace.UP).setType(Material.SNOW);
    }

    
}
