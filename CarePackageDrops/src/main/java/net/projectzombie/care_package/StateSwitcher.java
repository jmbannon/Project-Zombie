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

package net.projectzombie.care_package;

import net.projectzombie.care_package.serialize.BlockSerialize;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 *
 * @author Jesse Bannon
 * 
 * Handles switching between alternate and base states. 
 * - Sets Base to Alt
 * - Restores Alt to Base
 * - Gets chest relative location
 */
public class StateSwitcher {

    private static final int ALT_STATE_LENGTH = 30;
    private static final int ALT_STATE_WIDTH = 30;
    private static final int ALT_STATE_HEIGHT = 8;
    private static PackageHandler CONTENTS;
    private static Block BASE_BLOCK = null;
    private static Block CHEST_BLOCK = null; 

    private File stateFile;
    private final Plugin plugin;
    private final FileConfiguration stateConfig; 

    /**
     * Initializes config files and PackageHandler.
     * 
     * @param plugin Bukkit plugin.
     * @param config drop_locations.yml config.
     */
    protected StateSwitcher(final Plugin plugin,
                            final FileConfiguration config)
    {
        this.plugin = plugin;
        this.stateConfig = config;
        StateSwitcher.CONTENTS = new PackageHandler(plugin);
    }
    
    /**
     * Sets an alternate state to a base state's location by serializing the 
     * base state's blocks into a text file and pasting the blocks with respect
     * to the offset location's coordinates.
     * 
     * @param altName Alternate state name.
     * @param baseName Base state name.
     * @return Negative indicates error, positive success.
     * @throws IOException
     */
    protected int setState(final String altName,
                           final String baseName) throws IOException 
    {    
        this.stateFile = new File(plugin.getDataFolder(), "buffer.txt");
        final FileWriter stateWriter = new FileWriter(stateFile);
        
        /* Initialize file paths */
        final String altStateName = altName;
        final String altPath = "alt_states." + altStateName;
        final String altWorldPath = altPath + ".world";
        final String altCoordPath = altPath + ".coords";
        final String altChestPath = altPath + ".chest";

        final String baseStateName = baseName;
        final String basePath = "base_states." + baseStateName;
        final String baseWorldPath = basePath + ".world";
        final String baseCoordPath = basePath + ".coords";
        final String baseAltDescPath = basePath + ".alts." + altStateName;
        
        // Debugging only!
        String temps = null;
        if (!stateConfig.contains(altPath)) temps = altPath;
        if (!stateConfig.contains(altWorldPath)) temps = altWorldPath;
        if ( !stateConfig.contains(altCoordPath)) temps =altCoordPath;
        if ( !stateConfig.contains(altChestPath)) temps =altChestPath;
        if ( !stateConfig.contains(basePath)) temps =basePath;
        if ( !stateConfig.contains(baseWorldPath)) temps =baseWorldPath;
        if ( !stateConfig.contains(baseCoordPath)) temps =baseCoordPath;
        if ( !stateConfig.contains(baseAltDescPath)) temps = baseAltDescPath;
        if (temps != null) {
            plugin.getServer().broadcastMessage(temps);
            return -1;
        }
                //|| !stateConfig.contains(baseAltPath)) {

        final String desc = stateConfig.getString(baseAltDescPath);

        final Vector altVector = stateConfig.getVector(altCoordPath);
        final Vector baseVector = stateConfig.getVector(baseCoordPath);
        final Vector chestVector = stateConfig.getVector(altChestPath);

        final Block altInitBlock = new Location(
                Bukkit.getWorld(stateConfig.getString(altWorldPath)),
                altVector.getX(),
                altVector.getY(),
                altVector.getZ()).getBlock();

        BASE_BLOCK = new Location(
                Bukkit.getWorld(stateConfig.getString(baseWorldPath)),
                baseVector.getX(),
                baseVector.getY(),
                baseVector.getZ()).getBlock();

        Block temp;

        for (int i = 0; i < ALT_STATE_LENGTH; i++) {
            for (int j = 0; j < ALT_STATE_WIDTH; j++) {
                for (int k = 0; k < ALT_STATE_HEIGHT; k++) {
                    temp = BASE_BLOCK.getRelative(i, k, j);
                    stateWriter.write(BlockSerialize.serialize(temp));
                    temp.setType(altInitBlock.getRelative(i, k, j).getType());
                    temp.setData(altInitBlock.getRelative(i, k, j).getData());
                }
            }
        }
        stateWriter.flush();
        stateWriter.close();
        CHEST_BLOCK = BASE_BLOCK.getRelative((int)chestVector.getBlockX(),
                                            (int)chestVector.getBlockY(),
                                            (int)chestVector.getBlockZ());
        
        if (CHEST_BLOCK.getState() instanceof Chest) {
            final Chest chest = (Chest)CHEST_BLOCK.getState();
            final ArrayList<ItemStack> items = CONTENTS.getRandPackage();
            
            if (items == null) {
                Bukkit.getLogger().info("No packages within YML. Aborting state change");
                return -1;
            }
            
            final ItemStack[] chestItems = new ItemStack[27];
            while (items.size() < 27)
                items.add(new ItemStack(Material.AIR));
            
            Collections.shuffle(items);
            for (int i = 0; i < items.size(); i++)
                chestItems[i] = items.get(i);
            
            chest.getInventory().setContents(chestItems);
            chest.update(true);
            plugin.getServer().broadcastMessage(desc);
        }
        else
        {
            restoreState();
            return -1;
        }
        
        return 1;
    }

    /**
     * Restores a base state to its original form by deserializing the text 
     * file and pasting those blocks with respect to it's offset coordinate.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    protected void restoreState() throws FileNotFoundException, IOException 
    {
        BufferedReader reader = new BufferedReader(new FileReader(stateFile));
        final String[] blocks = reader.readLine().split("#");

        for (String block : blocks) {
            BlockSerialize.deserializeAndSet(block);
        }  
        stateFile.delete();
        this.removeDroppedEntities(BASE_BLOCK);
        BASE_BLOCK = null;
        CHEST_BLOCK = null;
    }
    
    private void removeDroppedEntities(Block baseBlock) {
        final Location centerLoc 
                = baseBlock.getRelative(ALT_STATE_WIDTH/2, 
                                        ALT_STATE_HEIGHT/2, 
                                        ALT_STATE_WIDTH/2).getLocation();
        
        final Entity temp 
                = centerLoc.getWorld().spawnEntity(centerLoc, EntityType.ARROW);
        
        for (Entity entity : temp.getNearbyEntities(ALT_STATE_WIDTH/2, 
                                                    ALT_STATE_HEIGHT/2, 
                                                    ALT_STATE_WIDTH/2)) {
            if (entity.getType() == EntityType.DROPPED_ITEM)
                entity.remove();            
        }
        temp.remove();
    }
    
    /**
     * Returns Returns the state's length.
     * @return Length of all states.
     */
    protected int getStateLength() {
        return ALT_STATE_LENGTH;
    }
    
    /**
     * Returns Returns the state's width.
     * @return Width of all states.
     */
    protected int getStateWidth() {
        return ALT_STATE_WIDTH;
    }
    
    /**
     * Returns Returns the state's height.
     * @return Height of all states.
     */
    protected int getStateHeight() {
        return ALT_STATE_HEIGHT;
    }
    
    /**
     * @return Block of a care package chest. Null if it there is no drop.
     */
    static public Block getChestBlock()
    {
        return CHEST_BLOCK;
    }
}
