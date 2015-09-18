/*
 * BlockPlacer
 *
 * Version:     0.5
 * MC Build:    1.8.3
 * Date:        06-10-2015
 *
 * Authur:      Jesse Bannon
 * Server:      Project Zombie
 * Website:     www.projectzombie.net
 * 
 * Allows players to place and break particular blocks within a WorldGuard
 * region whos build flag is set to allowed. Stores these blocks within two
 * buffers: blocks and lights. Restores all blocks by iterating through the
 * buffer and setting the blocks to air.  For light blocks, a player must
 * send the command to be able to teleport to each light block location to
 * remove it allowing light to update correctly.
 *
 */

package net.projectzombie.block_buffer.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.projectzombie.block_buffer.buffer.Buffer;
import net.projectzombie.block_buffer.controller.RestoreController;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Jesse Bannon
 * 
 * Main is used for enabling and disable the plugin on server startup/stop.
 */
public class Main extends JavaPlugin
{
    public static File BLOCK_BROKE_BUFFER;
    public static File LIGHT_PLACED_BUFFER;
    public static File LIGHT_BROKE_BUFFER;
    public static File BLOCK_PLACED_BUFFER;
    public static File BUFFER_FOLDER;
    
    private CommandExec commandExecutor;
    
    @Override
    public void onEnable()
    {
        BUFFER_FOLDER = this.getDataFolder();
        
        if (BUFFER_FOLDER == null || !BUFFER_FOLDER.exists())
            BUFFER_FOLDER.mkdir();
        
        createBuffers();
        
        Buffer.initialize(this);
        commandExecutor = new CommandExec();
        
        this.getCommand("bb").setExecutor(commandExecutor);
        this.getLogger().info("BlockBuffer enabled!");
    }

    @Override
    public void onDisable()
    {
        // Do not disable until restore is complete
        while (RestoreController.isRestoring());

        this.getLogger().info("BlockBuffer disabled!");
    }
    
    static
    public void createBuffers()
    {
        BLOCK_PLACED_BUFFER = createBuffer("BLOCK_PLACED_BUFFER");
        LIGHT_PLACED_BUFFER = createBuffer("LIGHT_PLACED_BUFFER");
        BLOCK_BROKE_BUFFER  = createBuffer("BLOCK_BROKE_BUFFER");
        LIGHT_BROKE_BUFFER  = createBuffer("LIGHT_BROKE_BUFFER");
    }
    
    static
    public void deleteBuffers()
    {
        BLOCK_PLACED_BUFFER.delete();
        LIGHT_PLACED_BUFFER.delete();
        BLOCK_BROKE_BUFFER.delete();
        LIGHT_BROKE_BUFFER.delete();
    }
    
    static File createBuffer(final String fileName)
    {
        final File newBuffer = new File(BUFFER_FOLDER, fileName);
        final FileWriter blockWriter;  
        try {
            blockWriter = new FileWriter(newBuffer, true);
            blockWriter.append("");
            blockWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newBuffer;
    }
}
