/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public class DropConfig implements CommandExecutor {
    private Plugin plugin;
    private File dropFile;
    private FileConfiguration dropConfig;
    
    public DropConfig(Plugin plugin) {
	this.plugin = plugin;
	this.dropFile = null;
	this.dropConfig = null;
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void loadAltState() {
	
	
	     
	
    }
    
    /**
    * Loads file from plugin folder.
    */
    public void loadConfig() {
	if (dropFile == null) {
	    dropFile = new File(plugin.getDataFolder(), "drop_locations.yml");
	}
	dropConfig = new YamlConfiguration();
	dropConfig = YamlConfiguration.loadConfiguration(dropFile);
	this.saveConfig();
    }
    
    /**
    * Saves all changes to file and file configuration.
    */
    private void saveConfig() {
	if (dropFile == null || dropConfig == null)
	    return;
	try {
	    dropConfig.save(dropFile);
	} catch (IOException e) {
	    plugin.getLogger().log(Level.SEVERE, "Could not save config to " 
		    + dropConfig, e);
	}
    }

}
