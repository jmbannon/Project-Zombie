/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.care_package.controller;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 *
 * @author com.gmail.jbann1994
 */
public class StateFile
{
    static private Plugin plugin;
    static private File stateFile;
    static private FileConfiguration stateConfig;

    
    private StateFile() { /* Do nothing. */ }
    
    /**
     * Initializes configuration file on server start-up.
     * @param pluginOnEnable JavaPlugin in Main.
     */
    static public void onEnable(final Plugin pluginOnEnable)
    {
        plugin = pluginOnEnable;
        loadConfig();
    }
    
    /**
     * Restores any states changed and saves configuration file.
     */
    static public void onDisable()
    {
        ActiveStateChanges.onDisable();
        saveConfig();
    }

    /**
     * Saves all changes to file and file configuration.
     */
    static public void saveConfig()
    {
        try
        {
            if (stateFile != null && stateConfig != null)
            stateConfig.save(stateFile);
        }
        catch (IOException e)
        {
            StateFile.getPlugin().getLogger().log(Level.SEVERE, "Could not save config to " + stateConfig, e);
        }
    }

    /**
     * Loads file from plugin folder.
     */
    static public void loadConfig()
    {
        if (stateFile == null)
        {
            stateFile = new File(StateFile.getFolder(), "drop_locations.yml");
        }
        stateConfig = new YamlConfiguration();
        stateConfig = YamlConfiguration.loadConfiguration(stateFile);
        saveConfig();
    }
    
    static public void set(final String path,
                           final Object value)
    {
        stateConfig.set(path, value);
    }
    
    static public Vector getVector(final String path)
    {
        return stateConfig.getVector(path);
    }
    
    
    static public String getString(final String path)
    {
        return stateConfig.getString(path);
    }

    static public boolean contains(final String path)
    {
        return stateConfig.contains(path);
    }
    
    static public Set<String> getSection(final String path)
    {
        return stateConfig.getConfigurationSection(path).getKeys(false);
    }

    static public Plugin getPlugin()
    {
        return plugin;
    }
    
    static public File getFolder()
    {
        return plugin.getDataFolder();
    }
}
