/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public class DropConfig implements CommandExecutor {
    private Plugin plugin;
    private File dropFile;
    private FileConfiguration dropConfig;
    
    private static AltState state;
    private static boolean stateChange;
    
    public DropConfig(Plugin plugin) {
	this.plugin = plugin;
	this.dropFile = null;
	this.dropConfig = null;
	this.state = null;
	this.stateChange = false;
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
	final Player player = (Player) cs;
	
	if (!player.isOp())							    // Change later!!!
	    return false;
	
	if (args.length == 0) 
	{
	    player.sendMessage("/cp setbase <base name>	 - creates basestate at current location (30x30x8)");
	    player.sendMessage("/cp setalt <alt name>	 - creates altstate at current location (30x30x8)");	    
	    player.sendMessage("/cp restore		 - Restores current state");
	    player.sendMessage("/cp remove <name>	 - removes drop");   
	    player.sendMessage("/cp list	         - lists base and alt states");   
	    player.sendMessage("/cp baseToAlt <base name> <alt name> - TEMP");
	} 
	else if (args[0].equalsIgnoreCase("list"))
	{
	    // List all states
	}
	else if (args[0].equalsIgnoreCase("setbase") && args.length == 2)
	{
	    createState(player, args[1], dropConfig, false);
	}
	else if (args[0].equalsIgnoreCase("setalt") && args.length == 2)
	{
	    createState(player, args[1], dropConfig, true);
	}
	else if (args[0].equalsIgnoreCase("remove") && args.length == 2)
	{
	    //remove state
	}
	else if (args[0].equalsIgnoreCase("basetoalt") && args.length == 3)
	{
	    swapStates(args[1], args[2], dropConfig);
	}
	else if (args[0].equalsIgnoreCase("restore"))
	{
	    restoreState();
	}	
	else
	{
	    
	}
	return true;
    }
    
    public void createState(final Player sender, final String stateName, final FileConfiguration file, final boolean stateType) {
	final String rootPath = stateType ? "alt_states" : "base_states";
	final String statePath = rootPath + "." + stateName;
	
	file.set(rootPath, stateName);
	file.set(statePath + ".world", sender.getWorld().getName());
	file.set(statePath + ".coords", sender.getLocation().toVector());
	file.set(statePath + ".desc", "fill in desc");
	
	sender.sendMessage(stateName + " created.");
    }
    
    public void swapStates(final String baseName, final String altName, final FileConfiguration file)
    {
	if (!stateChange)
	{
	    try {
		state = new AltState(altName, baseName, file);
		stateChange = true;
	    } catch (IOException ex) {
		Logger.getLogger(DropConfig.class.getName()).log(Level.SEVERE, null, ex);  
	    }
	}
    }
    
    public void restoreState() {
	if (stateChange)
	{
	    try {
		state.restoreState();
		stateChange = false;
	    } catch (IOException ex) {
		Logger.getLogger(DropConfig.class.getName()).log(Level.SEVERE, null, ex);
	    } 
	}
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
