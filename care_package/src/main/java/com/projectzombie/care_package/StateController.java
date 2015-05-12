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
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
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
public class StateController {

    private final Plugin plugin;
    private File dropFile;
    private FileConfiguration dropConfig;

    private static StateSwitcher state;
    
    private static boolean stateChange;
    private static Random rand;

    public enum StateType { ALT, BASE };
    private static final String BASE_ROOT = "base_states";
    private static final String ALT_ROOT = "alt_states";
    
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
    }

    /**
     * Initiates a random care package drop. //WIP!!!!!!
     * @param file 
     */
    public void initiateDrop() 
    {
        final String altState, baseState, baseAltsPath;
        final ArrayList<String> baseStates = new ArrayList<>();
        final ArrayList<String> altStates = new ArrayList<>();
        
        int randIndex;
        for (String key : dropConfig.getConfigurationSection(BASE_ROOT).getKeys(false))
            baseStates.add(key);
      
        if (baseStates.isEmpty()) {
            Bukkit.getServer().getLogger().info("[CarePackage] No base states exist. Cannot initiate drop.");
            return;
        }
        randIndex = rand.nextInt(baseStates.size());
        baseState = baseStates.get(randIndex);
        baseAltsPath = BASE_ROOT + "." + baseState + ".alts";
        
        for (String key : dropConfig.getConfigurationSection(baseAltsPath).getKeys(false))
            altStates.add(key);
        
        if (altStates.isEmpty()) {
            Bukkit.getServer().getLogger().log(Level.INFO, "[CarePackage] No alt states exist for base {0}. Cannot initiate drop.", baseState);
            return;
        }
        randIndex = rand.nextInt(altStates.size());
        altState = altStates.get(randIndex);
        
        this.setAltState(baseState, altState);
    }
    
    /**
     * Creates a new state within the configuration file.
     *
     * @param sender Command sender.
     * @param stateName State name.
     * @param stateType Type of state (base/alt).
     */
    public void createState(final Player sender,
            final String stateName,
            final StateType stateType) 
    {
        final String statePath = getPath(stateName, stateType);
        final Location senderLoc = sender.getLocation();
        Vector chestSerialized = null;

        if (stateType == StateType.ALT) {
            chestSerialized = this.getChestRelative(senderLoc);
        
            if (chestSerialized == null) {
                sender.sendMessage("A chest error occured");
                return;
            }
        }

        //dropConfig.set(rootPath, stateName);
        dropConfig.set(statePath + ".world", sender.getWorld().getName());
        dropConfig.set(statePath + ".coords", senderLoc.toVector());
        
        if (stateType == StateType.ALT)
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
    public void setAltState(final String baseName,
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
    public void listStates(final Player sender,
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
     * Restores a base state back to its original form.
     */
    public void restoreState() {
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
     * @param description 
     */
    public void linkState(final Player player,
                          final String baseStateName,
                          final String altStateName,
                          String description)
    {
        final String basePath = "base_states." + baseStateName;
        final String altPath = "alt_states." + altStateName;
        if (description.isEmpty()) description = "NEEDS DESCRIPTION";
        
        if (!dropConfig.contains(basePath)) {
            player.sendMessage(baseStateName + " does not exist.");
            return;
        } else if (!dropConfig.contains(altPath)) {
            player.sendMessage(altStateName + " does not exist.");
            return;
        }
        
        dropConfig.set(basePath + ".alts." + altStateName, description);
        this.saveConfig();
        player.sendMessage(baseStateName + " linked to " + altStateName);
    }
    
    private String getPath(final String stateName,
                         final StateType type)
    {
        return (type == StateType.ALT)
                ? "alt_states."+stateName : "base_states."+stateName;
    }
    
    /**
     * Removes state of the given name.
     * @param player
     * @param stateName
     * @param stateType
     * @param file 
     */
    public void removeState(final Player player,
                            final String stateName,
                            final StateType stateType)
    {
        final String path = getPath(stateName, stateType);
        
        if (dropConfig.contains(path)) {
            dropConfig.set(path, null);
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
    
    public void teleportToState(final Player sender,
                                final String stateName,
                                final StateType type)
    {
        final String coordPath = this.getPath(stateName, type) + ".coords";
        final String worldPath = this.getPath(stateName, type) + ".world";
        
        if (!dropConfig.contains(coordPath)
                || !dropConfig.contains(worldPath)) {
            sender.sendMessage(stateName + " does not exist.");
            return;
        }
        final Vector vec = dropConfig.getVector(coordPath);
        final World world = Bukkit.getWorld(dropConfig.getString(worldPath));       
        final Location loc = new Location(world, vec.getBlockX(),
                                                 vec.getBlockY(),
                                                 vec.getBlockZ());
        sender.teleport(loc);
    }
    
    public void checkYaw(final Player sender)
    {
        final float yaw = sender.getLocation().getYaw();
        if (yaw >= -67.5 && yaw < -22.5)
            sender.sendMessage("Correct direction! States always point SE.");
        else
            sender.sendMessage("Wrong direction! States always point SE.");
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
