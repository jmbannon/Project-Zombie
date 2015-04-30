/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import com.projectzombie.care_package.serialize.ItemSerialize;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
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
    private static final Random rand = new Random();
    private final Plugin plugin;
    
    public ChestContents(Plugin plugin)
    {
        this.plugin = plugin;   
    }
    
    public void setChest(final Chest chest)
    {   
        final String path = "chest_configs";
        final String buffer;
        String chestName = null;
        
        int i = 0, j = 0;
        for (String key : chestConfig.getConfigurationSection(path).getKeys(false)) {
            ++i;
        }
      
        if (i == 0) {
            Bukkit.getServer().getLogger().info("[CarePackage] No chests exist. Cannot initate drop.");
            return;
        }
        
        j = rand.nextInt(i) + 1;
        
        for (String key : chestConfig.getConfigurationSection(path).getKeys(false)) {
            if (j == i) {
                chestName = key;
            }
            --i;
        }
        if (chestName == null) return;
        buffer = chestConfig.getString(path + "." + chestName);
        String split[] = buffer.split("#");
        
        for (i = 0; i < split.length; i++) {
            chest.getInventory().addItem(ItemSerialize.deserialize(split[i]));
        }
        chest.update();
    }
    
    public void createChest(final Player sender,
                            final String chestName) 
    {
        final StringBuilder temp = new StringBuilder();
        
        if (chestConfig == null) {
            sender.sendMessage("The file is null! Please contact the server administrator.");
            return;
        }
 
        chestConfig.set("chest_configs", chestName);
        
        for (ItemStack item : sender.getInventory()) {
            temp.append(ItemSerialize.serialize(item));
        }
        chestConfig.set("chest_configs." + chestName, temp.toString());
        this.saveConfig();
        sender.sendMessage("Your inventory has been saved as " + chestName);
    }
    
    public void removeChest(final Player sender,
                             final String chestName)
    {
        if (chestConfig == null) {
            sender.sendMessage("The file is null! Please contact the server administrator.");
            return;
        } 
        if (chestConfig.contains("chest_configs." + chestName)) {
            chestConfig.set("chest_configs." + chestName, null);
            this.saveConfig();
            sender.sendMessage(chestName + " has been deleted.");
        } else
            sender.sendMessage(chestName + " does not exist");
    }
    
    /**
     * Loads file from plugin folder.
     */
    public void loadConfig()
    {
        if (chestFile == null) 
            chestFile = new File(plugin.getDataFolder(), "drop_locations.yml");

        chestConfig = new YamlConfiguration();
        chestConfig = YamlConfiguration.loadConfiguration(chestFile);
        this.saveConfig();
    }

    /**
     * Saves all changes to file and file configuration.
     */
    public void saveConfig()
    {
        if (chestFile == null || chestConfig == null) 
            return;
        try {
            chestConfig.save(chestFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to "
                    + chestConfig, e);
        }
    }
}
