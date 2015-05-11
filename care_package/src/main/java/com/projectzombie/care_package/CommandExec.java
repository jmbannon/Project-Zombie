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

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 * Handles all player controls for the states.
 * - Create
 * - Remove
 * - List
 * - LinkStates
 * - SwitchStates
 *
 * @author Jesse Bannon
 */
public class StateController implements CommandExecutor {

    private final Plugin plugin;
    private File dropFile;
    private FileConfiguration dropConfig;

    private static StateSwitcher state;
    private static PackageHandler chest;
    private static boolean stateChange;
    private static Random rand;

    private enum StateType { ALT, BASE };
    
    /**
     * Initializes StateChanger configuration file, StateSwitcher, and 
     * PackageHandler.
     *
     * @param plugin
     */
    public StateController(Plugin plugin) {
        this.plugin = plugin;
        this.loadConfig();
        state = new StateSwitcher(plugin, dropConfig);
        stateChange = false;
        rand = new Random();
        chest = new PackageHandler(plugin);
    }

    /**
     * Command input. MOVE TO DIFFERENT CLASS!!!!
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
            if (args[1].equalsIgnoreCase("alt"))
                this.listStates(player, StateType.ALT);
            else if (args[1].equalsIgnoreCase("base"))
                this.listStates(player, StateType.BASE);
            else
                this.listCommands(player);
            
            
        } else if (args[0].equalsIgnoreCase("create") && args.length == 3) {
            if (args[1].equalsIgnoreCase("alt"))
                createState(player, args[2], StateType.ALT);
            else if (args[1].equalsIgnoreCase("base"))
                createState(player, args[2], StateType.BASE);
            else if (args[1].equalsIgnoreCase("chest"))
                chest.createPackage(player, args[2]);
            else
                this.listCommands(player);
  
        } else if (args[0].equalsIgnoreCase("remove") && args.length == 3) {
            if (args[1].equalsIgnoreCase("base"))
                this.removeState(player, args[2], StateType.BASE, dropConfig);
            else if (args[1].equalsIgnoreCase("alt"))
                this.removeState(player, args[2], StateType.ALT, dropConfig);
            else
                this.listCommands(player);
                
        } else if (args[0].equalsIgnoreCase("basetoalt") && args.length == 3) {
            setAltState(args[1], args[2]);
            player.sendMessage("attemping to swap states" + args[1] + " and " + args[2]);
            
        } else if (args[0].equalsIgnoreCase("chest") && args.length == 3) {
            if (args[1].equalsIgnoreCase("create"))
                chest.createPackage(player, args[2]); 
            else if (args[1].equalsIgnoreCase("remove"))
                chest.removePackage(player, args[2]);
            else
                this.listCommands(player);
        } else if (args[0].equalsIgnoreCase("restore")) {
            restoreState();
            
        } else {
            this.listCommands(player);
        }
        return true;
    }

    /**
     * Initiates a random care package drop. //WIP!!!!!!
     * @param file 
     */
    private void initiateDrop(final FileConfiguration file) 
    {
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
     * Creates a new state within the configuration file.
     *
     * @param sender Command sender.
     * @param stateName State name.
     * @param stateType Type of state (base/alt).
     */
    private void createState(final Player sender,
            final String stateName,
            final StateType stateType) 
    {
        final String rootPath 
                = (stateType == StateType.ALT) ? "alt_states" : "base_states";
        final String statePath = rootPath + "." + stateName;
        final Location senderLoc = sender.getLocation();
        Vector chestSerialized = null;

        if (stateType == StateType.ALT) {
            chestSerialized = this.getChestRelative(senderLoc);
        
            if (chestSerialized == null) {
                sender.sendMessage("A chest error occured");
                return;
            }
        }

        dropConfig.set(rootPath, stateName);
        dropConfig.set(statePath + ".world", sender.getWorld().getName());
        dropConfig.set(statePath + ".coords", senderLoc.toVector());
        dropConfig.set(statePath + ".desc", "fill in desc");
        
        if (stateType == StateType.BASE)
            dropConfig.set(statePath + ".alts", null);
        else
            dropConfig.set(statePath + ".chest", chestSerialized);

        sender.sendMessage(stateName + " created.");
        this.saveConfig();
    }

    /**
     * Swaps an alternate state to a base state's location.
     *
     * @param baseName Name of base state.
     * @param altName Name of alternate state.
     */
    private void setAltState(final String baseName,
                             final String altName) 
    {
        int rv;
        if (!stateChange) {
            try {
                StateController.state = new StateSwitcher(plugin, dropConfig);
                rv = state.setState(altName, baseName);
                if (rv >= 0)
                stateChange = true;
            } catch (IOException ex) {
                Logger.getLogger(StateController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Lists all states of the specified StateType to the command sender.
     *
     * @param sender
     * @param file
     * @param stateType True - AltState. False - BaseState.
     */
    private void listStates(final Player sender,
                            final StateType stateType) 
    {
        final String stateConfigSection 
                = (stateType == StateType.ALT) ? "alt_states" : "base_states";

        sender.sendMessage(stateConfigSection);
        for (String key : dropConfig.getConfigurationSection(stateConfigSection).getKeys(false)) {
            sender.sendMessage(" - " + key);
        }
    }

    /**
     * Lists all commands to the sender.
     * @param sender Command sender.
     */
    private void listCommands(final Player sender) 
    {
        sender.sendMessage("/cp create <base/alt/chest> <name> ");
        sender.sendMessage("/cp restore		     - Restores current state");
        sender.sendMessage("/cp remove <name>	 - removes drop");
        sender.sendMessage("/cp list <base/alt>	 - lists base and alt states");
        sender.sendMessage("/cp baseToAlt <base name> <alt name> - TEMP");
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
                Logger.getLogger(StateController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Links a state //WIP!!!!
     * @param player
     * @param baseStateName
     * @param altStateName
     * @param file 
     */
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
     * Gets the vector of the alt state's single chest relative to the
     * player's location.
     * 
     * @param playerLoc Location of the player.
     * @return Vector of the offset relative to the player.
     */
    public Vector getChestRelative(final Location playerLoc) {
        final Block loc = playerLoc.getBlock();
        final int length = state.getStateLength();
        final int width = state.getStateWidth();
        final int height = state.getStateHeight();
        
        Vector chestRelative = null;
        boolean hasChest = false;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    if (loc.getRelative(i, k, j).getType() == Material.CHEST) {
                        if (!hasChest) {
                            chestRelative = new Vector(i, k, j);
                            hasChest = true;
                        }
                    }
                }
            }
        }
        return chestRelative;
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
