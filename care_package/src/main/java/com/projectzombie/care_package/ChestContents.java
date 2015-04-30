/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public class ChestContents {
    private File chestFile;
    private FileConfiguration chestConfig;
    private final Plugin plugin;
    
    public ChestContents(Plugin plugin) {
        this.plugin = plugin;   
    }
    
    private void createChest(final Player sender,
                             final String chestName) 
    {
        final StringBuilder temp = new StringBuilder();
        
        if (chestConfig == null) {
            sender.sendMessage("The file is null! Please contact the server administrator.");
            return;
        }
 
        chestConfig.set("chest_configs", chestName);
        
        for (ItemStack item : sender.getInventory()) {
            
        }
        chestConfig.set("chest_configs." + chestName, sender); //set with loot!
        
        
    }
    
    /**
     * Loads file from plugin folder.
     */
    private void loadConfig() {
        if (chestFile == null) {
            chestFile = new File(plugin.getDataFolder(), "drop_locations.yml");
        }

        chestConfig = new YamlConfiguration();
        chestConfig = YamlConfiguration.loadConfiguration(chestFile);
        this.saveConfig();
    }

    /**
     * Saves all changes to file and file configuration.
     */
    private void saveConfig() {
        if (chestFile == null || chestConfig == null) {
            return;
        }
        try {
            chestConfig.save(chestFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to "
                    + chestConfig, e);
        }
    }
}
