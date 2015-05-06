/*
 * CarePackage
 *
 * Version:     0.5
 * MC Build:    1.8.3
 * Date:        05-03-2015
 *
 * Authur:      Jesse Bannon
 * Server:      Project Zombie
 * Website:     www.projectzombie.net
 * 
 * Initiates random care package drops by combining an alternate state of the
 * map with a base state on the actual player map. Stores the base state blocks
 * within a text buffer and pastes the alt state to the location of the base
 * state. Finds single chest within the pasted alt state and sets a randomly
 * define set of items made by the administrator.  Restores the state on a
 * timer.
 *
 */
package com.projectzombie.care_package;

import com.projectzombie.care_package.serialize.ItemSerialize;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * @author Jesse Bannon
 * 
 * This class is to handle everything related to chest configurations
 * within the care package drop.
 * 
 * Creates
 * Removes
 * Lists   *** TODO
 * getRandomPackage
 * getChest *** TODO
 */
public class PackageHandler {
    
    private static final Random RAND = new Random();
    
    private File chestFile;
    private FileConfiguration chestConfig;
    private final Plugin plugin;
    
    /**
     * Initializes config file.
     * @param plugin Bukkit plugin.
     */
    public PackageHandler(Plugin plugin)
    {
        this.plugin = plugin;
        this.loadConfig();
    }
    
    /**
     * Returns random package defined within the configuration file.
     * @return Random ItemStack from serialized string in config.
     */
    ItemStack[] getRandPackage()
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
            return null;
        }
        
        j = RAND.nextInt(i) + 1;
        
        for (String key : chestConfig.getConfigurationSection(path).getKeys(false)) {
            if (j == i) {
                chestName = key;
            }
            --i;
        }
        if (chestName == null) {
            plugin.getServer().broadcastMessage("no chest found");
            return null;
        }
        buffer = chestConfig.getString(path + "." + chestName);
        String split[] = buffer.split("#");
        plugin.getServer().broadcastMessage(chestName + " " + split.length);
        ItemStack items[] = new ItemStack[27];
        
        for (i = 0; i < 27; i++) {
            items[i] = ItemSerialize.deserialize(split[i]);
            plugin.getServer().broadcastMessage(items[i].getType().toString() + " " + items[i].getAmount());
        }
        return items;
    }
    
    /**
     * Creates a package based on the command sender's inventory (excluding
     * their hot-bar!) and stores it serialized within the config.
     * @param sender Command sender.
     * @param packageName Name of the package.
     */
    public void createPackage(final Player sender,
                              final String packageName) 
    {
        final StringBuilder serialBuffer = new StringBuilder();
        
        if (chestConfig == null) {
            sender.sendMessage("The file is null! Please contact the server administrator.");
            return;
        }
 
        chestConfig.set("chest_configs", packageName);
        
        for (int i = 9; i <= 35; i++)
            serialBuffer.append(ItemSerialize.serialize(sender.getInventory().getItem(i)));

        chestConfig.set("chest_configs." + packageName, serialBuffer.toString());
        this.saveConfig();
        sender.sendMessage("Your inventory has been saved as " + packageName);
    }

    /**
     * Removes a package of the given name called by the command sender.
     * @param sender Command sender.
     * @param packageName Name of the package.
     */
    public void removePackage(final Player sender,
                              final String packageName)
    {
        if (chestConfig == null) {
            sender.sendMessage("The file is null! Please contact the server administrator.");
            return;
        } 
        if (chestConfig.contains("chest_configs." + packageName)) {
            chestConfig.set("chest_configs." + packageName, null);
            this.saveConfig();
            sender.sendMessage(packageName + " has been deleted.");
        } else
            sender.sendMessage(packageName + " does not exist");
    }
    
    /**
     * Loads configuration file from plugin data folder.
     */
    public void loadConfig()
    {
        if (chestFile == null) 
            chestFile = new File(plugin.getDataFolder(), "chest_configs.yml");

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
