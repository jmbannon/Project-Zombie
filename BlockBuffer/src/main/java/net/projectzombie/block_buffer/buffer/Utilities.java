/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.block_buffer.buffer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public class Utilities
{
    public static Plugin plugin;
    public static final Server SERVER = Bukkit.getServer();
    
    public static boolean isLight(final Block theBlock)
    {
        final Material blockMaterial = theBlock.getType();
        return blockMaterial.equals(Material.TORCH)
                || blockMaterial.equals(Material.GLOWSTONE)
				|| blockMaterial.equals(Material.REDSTONE_LAMP_ON)	
				|| blockMaterial.equals(Material.BEACON)
                || blockMaterial.equals(Material.REDSTONE_LAMP_OFF);
    }
}
