/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Controls the states being changed amongst each other.
 *
 * @author jbannon
 */
public class StateControl implements CommandExecutor {

    private final Plugin plugin;
    private File dropFile;
    private FileConfiguration dropConfig;

    private static AltState state;
    private static boolean stateChange;
    private static Random rand;

    private enum StateType { ALT, BASE };
    
    /**
     * Initializes StateChanger variables.
     *
     * @param plugin
     */
    public StateControl(Plugin plugin) {
        this.plugin = plugin;
        this.dropFile = null;
        this.dropConfig = null;
        StateControl.state = null;
        StateControl.stateChange = false;
        StateControl.rand = new Random();
    }

    /**
     * Command input. WIP
     *
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
            String[] args) {
        final Player player = (Player) cs;

        if (!player.isOp()) // Change later!!!
        {
            return false;
        }

        if (args.length == 0) {
            this.listCommands(player);
            
        } else if (args[0].equalsIgnoreCase("list") && args.length == 2) {
            if (args[1].equalsIgnoreCase("alt")) {
                this.listStates(player, dropConfig, StateType.ALT);
            } else if (args[1].equalsIgnoreCase("base")) {
                this.listStates(player, dropConfig, StateType.BASE);
            } else {
                this.listCommands(player);
            }
            
        } else if (args[0].equalsIgnoreCase("setbase") && args.length == 2) {
            createState(player, args[1], dropConfig, StateType.BASE);
            
        } else if (args[0].equalsIgnoreCase("setalt") && args.length == 2) {
            createState(player, args[1], dropConfig, StateType.ALT);
            
        } else if (args[0].equalsIgnoreCase("remove") && args.length == 3) {
            if (args[1].equalsIgnoreCase("base"))
                this.removeState(player, args[2], StateType.BASE, dropConfig);
            else if (args[1].equalsIgnoreCase("alt"))
                this.removeState(player, args[2], StateType.ALT, dropConfig);
            else
                this.listCommands(player);
                
        } else if (args[0].equalsIgnoreCase("basetoalt") && args.length == 3) {
            swapStates(args[1], args[2], dropConfig);
            player.sendMessage("attemping to swap states" + args[1] + " and " + args[2]);
            
        } else if (args[0].equalsIgnoreCase("restore")) {
            restoreState();
            
        } else {

        }
        return true;
    }

    private void initiateDrop(final FileConfiguration file) {
        final String basePath = "base_states";
        final String altPath = "alt_states";
        String baseName;
        String altName;
        
        int i = 0, j = 0;
        for (String key : file.getConfigurationSection(basePath).getKeys(false)) {
            ++i;
        }
      
        if (i == 0) {
            Bukkit.getServer().getLogger().info("[CarePackage] No base states exist. Cannot initiate drop.");
            return;
        }
        
        j = rand.nextInt(i) + 1;
        
        for (String key : file.getConfigurationSection(basePath).getKeys(false)) {
            if (j == i) {
                baseName = key;
            }
            --i;
        }
        
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
            final StateType stateType) {

        final String rootPath 
                = (stateType == StateType.ALT) ? "alt_states" : "base_states";
        final String statePath = rootPath + "." + stateName;
        final Location senderLoc = sender.getLocation();

        file.set(rootPath, stateName);
        file.set(statePath + ".world", sender.getWorld().getName());
        file.set(statePath + ".coords", senderLoc.toVector());
        file.set(statePath + ".desc", "fill in desc");
        
        if (stateType == StateType.BASE)
            file.set(statePath + ".alts", null);
        else 
            file.set(statePath + ".chest", state.getChestRelative(senderLoc));

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
            final FileConfiguration file) {
        
        if (!stateChange) {
            try {
                state = new AltState(altName, baseName, dropFile, file, plugin);
                stateChange = true;
            } catch (IOException ex) {
                Logger.getLogger(StateControl.class.getName()).log(Level.SEVERE, null, ex);
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
            final StateType stateType) {
        final String stateConfigSection 
                = (stateType == StateType.ALT) ? "alt_states" : "base_states";

        sender.sendMessage("States: ");
        for (String key : file.getConfigurationSection(stateConfigSection).getKeys(false)) {
            sender.sendMessage(" - " + key);
        }
    }

    private void listCommands(final Player player) {
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
    private void restoreState() {
        if (stateChange) {
            try {
                state.restoreState();
                stateChange = false;
            } catch (IOException ex) {
                Logger.getLogger(StateControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void linkState(final Player player,
                           final String baseStateName,
                           final String altStateName,
                           final FileConfiguration file) {
        final String basePath = "base_states." + baseStateName + ".alts";
        final String altPath = "alt_states." + altStateName;
        
        if (!file.contains(basePath)) {
            player.sendMessage(baseStateName + " does not exist.");
            return;
        } else if (!file.contains(altPath)) {
            player.sendMessage(altStateName + " does not exist.");
            return;
        }
        
        file.set(basePath, altStateName);
        player.sendMessage(baseStateName + " linked to " + altStateName);
    
    }
    
    /**
     * Removes state of the given name.
     * @param player
     * @param stateName
     * @param stateType
     * @param file 
     */
    private void removeState(final Player player,
                             final String stateName,
                             final StateType stateType,
                             final FileConfiguration file) {
        
        final String stateConfigSection 
                = (stateType == StateType.ALT) ? "alt_states." : "base_states.";
        final String path = stateConfigSection+stateName;
        
        if (file.contains(path)) {
            file.set(path, null);
            player.sendMessage(stateName + " deleted.");
        } else
            player.sendMessage(stateName + " does not exist.");       
    }

    /**
     * Loads file from plugin folder.
     */
    private void loadConfig() {
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
        if (dropFile == null || dropConfig == null) {
            return;
        }
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
    public void onEnable() {
        this.loadConfig();
    }

    /**
     * Restores any states changed and saves configuration file.
     */
    public void onDisable() {
        this.restoreState();
        this.saveConfig();
    }
}
