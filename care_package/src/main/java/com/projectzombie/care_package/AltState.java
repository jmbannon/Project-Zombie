/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import com.projectzombie.care_package.serialize.BlockSerialize;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

/**
 *
 * @author jbannon
 */
public class AltState {

    private static final int ALT_STATE_LENGTH = 30;
    private static final int ALT_STATE_WIDTH = 30;
    private static final int ALT_STATE_HEIGHT = 8;
    
    private Location altStateLoc;
    private Location baseStateLoc;

    private final String altStateName;
    private final String baseStateName;

    private String altStateDesc;
    private String baseStateDesc;

    private final File stateFile;
    
    private Chest packageChest;

    /**
     * Swaps a base state with an alt state.
     *
     * @param altStateName
     * @param baseStateName
     * @param file
     * @param stateConfig
     * @throws IOException
     */
    public AltState(final String altStateName,
            final String baseStateName,
            final File file,
            final FileConfiguration stateConfig) throws IOException {
        
        stateFile = new File(file.getParentFile().getAbsolutePath(), "buffer.txt");
        final FileWriter stateWriter = new FileWriter(stateFile);

        /* Initialize file paths */
        this.altStateName = altStateName;
        final String altPath = "alt_states." + this.altStateName;
        final String altWorldPath = altPath + ".world";
        final String altCoordPath = altPath + ".coords";
        final String altDescPath = altPath + ".desc";
        final String altChestPath = altPath + ".chest";

        this.baseStateName = baseStateName;
        final String basePath = "base_states." + this.baseStateName;
        final String baseWorldPath = basePath + ".world";
        final String baseCoordPath = basePath + ".coords";
        final String baseDescPath = basePath + ".desc";
        final String baseAltPath = basePath + ".alts";

        if (!stateConfig.contains(altPath)
                || !stateConfig.contains(altWorldPath)
                || !stateConfig.contains(altCoordPath)
                || !stateConfig.contains(altDescPath)
                || !stateConfig.contains(altChestPath)
                || !stateConfig.contains(basePath)
                || !stateConfig.contains(baseWorldPath)
                || !stateConfig.contains(baseCoordPath)
                || !stateConfig.contains(baseDescPath)
                || !stateConfig.contains(baseAltPath)) {
            return;
        }

        altStateDesc = stateConfig.getString(altDescPath);
        baseStateDesc = stateConfig.getString(baseDescPath);

        final Vector altVector = stateConfig.getVector(altCoordPath);
        final Vector baseVector = stateConfig.getVector(baseCoordPath);
        final Vector chestVector = stateConfig.getVector(altChestPath);

        altStateLoc = new Location(
                Bukkit.getWorld(stateConfig.getString(altWorldPath)),
                altVector.getX(),
                altVector.getY(),
                altVector.getZ());

        baseStateLoc = new Location(
                Bukkit.getWorld(stateConfig.getString(baseWorldPath)),
                baseVector.getX(),
                baseVector.getY(),
                baseVector.getZ());

        final Block altInitBlock = altStateLoc.getBlock();
        final Block baseInitBlock = baseStateLoc.getBlock();
        Block temp;

        for (int i = 0; i < ALT_STATE_LENGTH; i++) {
            for (int j = 0; j < ALT_STATE_WIDTH; j++) {
                for (int k = 0; k < ALT_STATE_HEIGHT; k++) {
                    temp = baseInitBlock.getRelative(i, k, j);
                    if (temp.getType() == Material.AIR) {
                        stateWriter.write(BlockSerialize.serialize(temp));
                        temp.setType(altInitBlock.getRelative(i, k, j).getType());
                        temp.setData(altInitBlock.getRelative(i, k, j).getData());
                    }
                }
            }
        }
        stateWriter.flush();
        stateWriter.close();
    }

    /**
     * Restores a base state to its original form.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void restoreState() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(stateFile));
        final String[] blocks = reader.readLine().split("\n");

        for (String block : blocks) {
            BlockSerialize.deserializeAndSet(block);
        }

        reader.close();
        stateFile.delete();
    }
    
    public Vector getChestRelative(final Location playerLoc) {
        final Block loc = playerLoc.getBlock();
        Vector chestRelative = null;
        boolean hasChest = false;

        for (int i = 0; i < ALT_STATE_LENGTH; i++) {
            for (int j = 0; j < ALT_STATE_WIDTH; j++) {
                for (int k = 0; k < ALT_STATE_HEIGHT; k++) {
                    if (loc.getRelative(i, k, j).getType() == Material.CHEST) {
                        if (!hasChest) {
                            chestRelative = new Vector(i, j, k);
                            hasChest = true;
                        }
                        else
                            return null;
                    }
                   
                }
            }
        }
        return chestRelative;
    }
}
