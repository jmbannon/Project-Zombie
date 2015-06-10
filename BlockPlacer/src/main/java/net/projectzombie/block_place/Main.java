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

package net.projectzombie.block_place;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Jesse Bannon
 * 
 * Main is used for enabling and disable the plugin on server startup/stop.
 */
public class Main extends JavaPlugin {

    private CommandExec commandExecutor;
    private BlockPlaceListener blockListener;
    
    @Override
    public void onEnable()
    {
        this.getDataFolder().mkdir();
        commandExecutor = new CommandExec(this);
        blockListener = new BlockPlaceListener(this);
        
        this.getCommand("bp").setExecutor(commandExecutor);
        this.getServer().getPluginManager().registerEvents(blockListener, this);
        this.getLogger().info("Placable Blocks enabled!");
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info("Placable Blocks disabled!");
    }
}
