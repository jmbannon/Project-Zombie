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
import net.projectzombie.block_buffer.buffer.Buffer;
import net.projectzombie.block_buffer.buffer.Utilities;
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

    private CommandExec commandExecutor;
    
    @Override
    public void onEnable()
    {
        if (this.getDataFolder() == null || !this.getDataFolder().exists())
            this.getDataFolder().mkdir();
        
        Main.BLOCK_PLACED_BUFFER = new File(this.getDataFolder(), "BLOCK_PLACED_BUFFER");
        Main.LIGHT_PLACED_BUFFER = new File(this.getDataFolder(), "LIGHT_PLACED_BUFFER");
        Main.BLOCK_BROKE_BUFFER  = new File(this.getDataFolder(), "BLOCK_BROKE_BUFFER");
        Main.LIGHT_BROKE_BUFFER  = new File(this.getDataFolder(), "LIGHT_BROKE_BUFFER");
        
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
}
