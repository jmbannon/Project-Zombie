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

package net.projectzombie.care_package.controller;

import net.projectzombie.care_package.serialize.BlockSerialize;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import net.projectzombie.care_package.state.StateType;
import net.projectzombie.care_package.state.AltState;
import net.projectzombie.care_package.state.BaseState;
import net.projectzombie.care_package.state.State;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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
public class StateController
{

    static private final Random RAND = new Random();
    
    static private File COPY_FILE;
    static private boolean COPY_IN_PROGRESS = false;
    
    private StateController() { /* Do nothing */ }
    
    
    /**
     * Initiates a random care package drop.
     */
    static public void initiateDrop() 
    {
        final ArrayList<String> baseStateNames = new ArrayList<>();
        final ArrayList<String> altStateNames = new ArrayList<>();
        final BaseState base;
        int randIndex;
        
        if (!StateFile.contains(StateType.ALT.getPath())
                || !StateFile.contains(StateType.BASE.getPath()))
        {
            Bukkit.getLogger().info("Drop config is empty. Aborting drop.");
            return;
        }

        for (String key : StateFile.getSection(BaseState.path()))
            baseStateNames.add(key);
      
        if (baseStateNames.isEmpty())
        {
            Bukkit.getServer().getLogger().info("[CarePackage] No base states exist. Cannot initiate drop.");
            return;
        }
        
        randIndex = RAND.nextInt(baseStateNames.size());
        base = new BaseState(baseStateNames.get(randIndex));
        
        if (!StateFile.contains(base.getAltPath()))
        {
            Bukkit.getLogger().info("Alt state path does not exist. Aborting drop.");
            return;
        }
        
        for (String key : StateFile.getSection(base.getAltPath()))
            altStateNames.add(key);
        
        if (altStateNames.isEmpty())
        {
            Bukkit.getServer().getLogger().log(Level.INFO, "[CarePackage] No alt states exist for base {0}. Cannot initiate drop.", base.getName());
            return;
        }
        randIndex = RAND.nextInt(altStateNames.size());
        
        setAltState(base.getName(), altStateNames.get(randIndex));
    }
    
    /**
     * Creates a new state within the configuration file.
     *
     * @param sender Command sender.
     * @param stateName State name.
     * @param stateType Type of state (base/alt).
     */
    static public void createState(final Player sender,
                                   final String stateName,
                                   final StateType stateType) 
    {
        final State state = State.create(stateType, stateName);
        final Location senderLoc = sender.getLocation();
        Vector chestSerialized = null;
        
        if (stateType == StateType.ALT)
        {
            chestSerialized = getChestRelative(senderLoc);
            if (chestSerialized != null)
            {
                StateFile.set(state.toAltState().getPathChestVector(), chestSerialized);
            }
            else
            {
                sender.sendMessage("A chest error occured");
                return;
            }
        }
        
        StateFile.set(state.getPathWorld(), sender.getWorld().getName());
        StateFile.set(state.getPathVector(), senderLoc.toVector());
        sender.sendMessage(stateName + " created.");
        StateFile.saveConfig();
    }

    /**
     * Swaps an alternate state to a base state's location.
     *
     * @param baseName Name of base state.
     * @param altName Name of alternate state.
     */
    static public void setAltState(final String baseName,
                                   final String altName) 
    {
        if (!ActiveStateChanges.contains(baseName))
        {
            final StateChange stateChange = new StateChange(baseName, altName);
            if (stateChange.isValid())
            {
                stateChange.setState();
                ActiveStateChanges.put(stateChange);
            }
        }
        
    }

    /**
     * Lists all states of the specified StateType to the command sender.
     *
     * @param sender
     * @param stateType
     */
    static public void listStates(final Player sender,
                                  final StateType stateType) 
    {
        final String statePath = stateType.getPath();
        sender.sendMessage(statePath);
        for (String key : StateFile.getSection(statePath))
        {
            sender.sendMessage(" - " + key);
        }
    }

    /**
     * Restores a base state back to its original form.
     * @param sender
     * @param baseName
     */
    static public void restoreState(final Player sender,
                                    final String baseName)
    {
        ActiveStateChanges.removeAndRestore(baseName);
    }
    
    /**
     * Links a state //WIP!!!!
     * @param player
     * @param baseStateName
     * @param altStateName 
     * @param description 
     */
    static public void linkState(final Player player,
                                 final String baseStateName,
                                 final String altStateName,
                                       String description)
    {
        final BaseState base = new BaseState(baseStateName);
        final AltState alt = new AltState(altStateName);
        
        if (description.isEmpty())
            description = "NEEDS DESCRIPTION";
        
        if (!StateFile.contains(base.getPath()))
        {
            player.sendMessage("Base state " + baseStateName + " does not exist.");
            return;
        }
        if (!StateFile.contains(alt.getPath()))
        {
            player.sendMessage("Alt state " + altStateName + " does not exist.");
            return;
        }
        
        StateFile.set(base.getPathAltDescription(altStateName), description);
        StateFile.saveConfig();
        player.sendMessage(baseStateName + " linked to " + altStateName);
    }
    
    /**
     * Removes state of the given name.
     * @param player
     * @param stateName
     * @param stateType
     */
    static public void removeState(final Player player,
                                   final String stateName,
                                   final StateType stateType)
    {
        final State state = State.create(stateType, stateName);
        final String statePath = state.getPath();
        BaseState linkedBaseState;
        
        if (StateFile.contains(statePath))
        {
            StateFile.set(statePath, null);
            if (stateType.equals(StateType.ALT))
            {
                for (String baseStateName : StateFile.getSection(BaseState.path()))
                {
                    linkedBaseState = new BaseState(baseStateName);
                    StateFile.set(linkedBaseState.getPathAltDescription(stateName), null);
                }
            }
            player.sendMessage(stateName + " deleted.");
        }
        else
        {
            player.sendMessage(stateName + " does not exist.");
        }
    }
    
    /**
     * Gets the vector of the alt state's single chest relative to the
     * player's location.
     * 
     * @param playerLoc Location of the player.
     * @return Vector of the offset relative to the player.
     */
    static public Vector getChestRelative(final Location playerLoc)
    {
        final Block loc = playerLoc.getBlock();
        final int length = StateChange.getStateLength();
        final int width  = StateChange.getStateWidth();
        final int height = StateChange.getStateHeight();
        
        Vector chestRelative = null;
        boolean hasChest = false;

        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < width; j++)
            {
                for (int k = 0; k < height; k++)
                {
                    if (loc.getRelative(i, k, j).getType() == Material.CHEST)
                    {
                        if (!hasChest)
                        {
                            chestRelative = new Vector(i, k, j);
                            hasChest = true;
                        }
                    }
                }
            }
        }
        return chestRelative;
    }
    
    static public void teleportToState(final Player sender,
                                       final String stateName,
                                       final StateType type)
    {
        final State state = State.create(type, stateName);
        final String worldPath = state.getPathWorld();
        final String vectorPath = state.getPathVector();
        
        if (!StateFile.contains(worldPath) || !StateFile.contains(vectorPath))
        {
            sender.sendMessage(stateName + " does not exist.");
            return;
        }
        
        final Vector locVector = StateFile.getVector(vectorPath);
        final World world = Bukkit.getWorld(StateFile.getString(worldPath));       
        final Location loc = new Location(world, locVector.getBlockX(),
                                                 locVector.getBlockY(),
                                                 locVector.getBlockZ());
        sender.teleport(loc);
    }
    
    static public void checkYaw(final Player sender)
    {
        final float yaw = sender.getLocation().getYaw();
        if (yaw >= -67.5 && yaw < -22.5)
            sender.sendMessage("Correct direction! States always point SE.");
        else
            sender.sendMessage("Wrong direction! States always point SE.");
    }
    
    static public void pasteBaseState(final Player sender,
                                      final String baseStateName) throws IOException
    {
        final BaseState base = new BaseState(baseStateName);

        if (!StateFile.contains(base.getPath()))
        {
            sender.sendMessage(baseStateName + " does not exist.");
            return;
        }
        
        COPY_FILE = new File(StateFile.getFolder(), "buffer.copy_buffer");
        final FileWriter stateWriter = new FileWriter(COPY_FILE);
        final Vector baseVector = StateFile.getVector(base.getPathVector());
        
        final Block playerBlock = sender.getLocation().getBlock();
        final Block baseBlock = new Location(
                Bukkit.getWorld(StateFile.getString(base.getPathWorld())),
                baseVector.getX(),
                baseVector.getY(),
                baseVector.getZ()).getBlock();
        
        Block temp;
        for (int i = 0; i < StateChange.getStateLength(); i++)
        {
            for (int j = 0; j < StateChange.getStateWidth(); j++)
            {
                for (int k = 0; k < StateChange.getStateHeight(); k++)
                {
                    temp = playerBlock.getRelative(i, k, j);
                    stateWriter.write(BlockSerialize.serialize(temp));
                    temp.setType(baseBlock.getRelative(i, k, j).getType());
                    temp.setData(baseBlock.getRelative(i, k, j).getData());
                }
            }
        }
        stateWriter.flush();
        stateWriter.close();
        COPY_IN_PROGRESS = true;
        sender.sendMessage(baseStateName + " pasted.");
    }
    
    static public void undoPaste(Player sender) throws IOException
    {
        if (!COPY_IN_PROGRESS)
        {
            sender.sendMessage("Nothing pasted.");
            return;
        }
        
        BufferedReader reader = new BufferedReader(new FileReader(COPY_FILE));
        final String[] blocks = reader.readLine().split("#");

        for (String block : blocks)
        {
            BlockSerialize.deserializeAndSet(block);
        }  
        COPY_FILE.delete();
        COPY_IN_PROGRESS = false;
        sender.sendMessage("Undid paste.");
    }
    
    static public void reloadConfig(final Player sender)
    {
        StateFile.loadConfig();
        sender.sendMessage("Care Package config reloaded.");
    }



}
