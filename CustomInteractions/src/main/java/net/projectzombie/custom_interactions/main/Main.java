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

package net.projectzombie.custom_interactions.main;

import net.projectzombie.custom_interactions.listeners.BlockListener;
import net.projectzombie.custom_interactions.listeners.Utilities;
import net.projectzombie.custom_interactions.listeners.ToolListener;
import net.projectzombie.custom_interactions.temp.TreeSnow;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Jesse Bannon
 * 
 * Main is used for enabling and disable the plugin on server startup/stop.
 */
public class Main extends JavaPlugin {

    private BlockListener blockListener;
    private ToolListener toolListener;
    private TreeSnow treeSnowCommand;
    
    @Override
    public void onEnable()
    {
        Utilities.initialize(this);
        blockListener = new BlockListener();
        toolListener = new ToolListener();
        treeSnowCommand = new TreeSnow();
        
        this.getCommand("treesnow").setExecutor(treeSnowCommand);
        this.getServer().getPluginManager().registerEvents(blockListener, this);
        this.getServer().getPluginManager().registerEvents(toolListener, this);
        this.getLogger().info("Placable Blocks enabled!");
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info("Placable Blocks disabled!");
    }
}
