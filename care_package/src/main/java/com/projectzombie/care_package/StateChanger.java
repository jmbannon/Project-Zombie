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
 * Controls the states being changed amongst each other.
 * @author jbannon
 */
public class StateChanger implements CommandExecutor 
{
    private final Plugin plugin;
    private File dropFile;
    private FileConfiguration dropConfig;
    
    private static AltState state;
    private static boolean stateChange;
    
	/**
	 * Initializes StateChanger variables.
	 * @param plugin 
	 */
    public StateChanger(Plugin plugin) 
	{
		this.plugin = plugin;
		this.dropFile = null;
		this.dropConfig = null;
		this.state = null;
		this.stateChange = false;
    }
    
	/**
	 * Command input. WIP
	 * @param cs
	 * @param cmd
	 * @param label
	 * @param args
	 * @return 
	 */
    @Override
    public boolean onCommand(CommandSender cs, 
	                         Command cmd, 
			                 String label, 
                	         String[] args) 
	{
		final Player player = (Player) cs;
	
		if (!player.isOp())							    // Change later!!!
			return false;
	
		if (args.length == 0) 
			this.listCommands(player);
		
		else if (args[0].equalsIgnoreCase("list") && args.length == 2)
		{
			if (args[1].equalsIgnoreCase("alt"))
				this.listStates(player, dropConfig, true);		
			else if (args[1].equalsIgnoreCase("base"))
				this.listStates(player, dropConfig, false);			
			else
				this.listCommands(player);	
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
			player.sendMessage("attemping to swap states" + args[1] + " and " + args[2]);
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
    
	/**
	 * Creates a new state within the YAML configuration.
	 * 
	 * @param sender
	 * @param stateName
	 * @param file
	 * @param stateType True - AltState. False - BaseState.
	 */
    private void createState(final Player sender, 
			                final String stateName, 
			                final FileConfiguration file, 
							final boolean stateType) 
	{
		final String rootPath = stateType ? "alt_states" : "base_states";
		final String statePath = rootPath + "." + stateName;
	
		file.set(rootPath, stateName);
		file.set(statePath + ".world", sender.getWorld().getName());
		file.set(statePath + ".coords", sender.getLocation().toVector());
		file.set(statePath + ".desc", "fill in desc");
	
		sender.sendMessage(stateName + " created.");
		this.saveConfig();
    }
    
	/**
	 * Swaps a base state with an alt state.
	 * 
	 * @param baseName
	 * @param altName
	 * @param file 
	 */
    private void swapStates(final String baseName, 
			               final String altName, 
						   final FileConfiguration file)
    {
		if (!stateChange)
		{
			try {
				state = new AltState(altName, baseName, dropFile, file);
				stateChange = true;
			} catch (IOException ex) {
				Logger.getLogger(StateChanger.class.getName()).log(Level.SEVERE, null, ex);  
			}
		}
    }
    
	/**
	 * Lists all of the state type to the sender.
	 * 
	 * @param sender
	 * @param file
	 * @param stateType True - AltState. False - BaseState.
	 */
	private void listStates(final Player sender,
			                final FileConfiguration file,
							final boolean stateType)
	{
		final String stateConfigSection = stateType ? "alt_states" : "base_states";
	
		sender.sendMessage("States: ");
		for (String key : file.getConfigurationSection(stateConfigSection).getKeys(false))
		{
			sender.sendMessage(" - " + key);
		}
	}
	
	private void listCommands(final Player player)
	{
		player.sendMessage("/cp setbase <base name>	 - creates basestate at current location (30x30x8)");
		player.sendMessage("/cp setalt <alt name>	 - creates altstate at current location (30x30x8)");	    
		player.sendMessage("/cp restore		     - Restores current state");
		player.sendMessage("/cp remove <name>	 - removes drop");   
		player.sendMessage("/cp list <base/alt>	 - lists base and alt states");   
		player.sendMessage("/cp baseToAlt <base name> <alt name> - TEMP");
	}
	
	/**
	 * Restores a base state back to its original form.
	 */
    private void restoreState() 
	{
		if (stateChange)
		{
			try {
				state.restoreState();
				stateChange = false;
			} catch (IOException ex) {
				Logger.getLogger(StateChanger.class.getName()).log(Level.SEVERE, null, ex);
			}	 
		}
    }
    
    /**
    * Loads file from plugin folder.
    */
    private void loadConfig() 
	{
		if (dropFile == null)
			dropFile = new File(plugin.getDataFolder(), "drop_locations.yml");
		
		dropConfig = new YamlConfiguration();
		dropConfig = YamlConfiguration.loadConfiguration(dropFile);
		this.saveConfig();
    }
    
    /**
    * Saves all changes to file and file configuration.
    */
    private void saveConfig() 
	{
		if (dropFile == null || dropConfig == null)
			return;
		try {
			dropConfig.save(dropFile);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " 
				+ dropConfig, e);
		}
	}
	
	/**
	 * Initializes configuration file on server start-up.
	 */
	public void onEnable()
	{
		this.loadConfig();
	}
	
	/**
	 * Restores any states changed and saves configuration file.
	 */
	public void onDisable()
	{
		this.restoreState();
		this.saveConfig();
	}
}
