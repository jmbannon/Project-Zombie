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

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Jesse Bannon
 * 
 * Main is used for enabling and disable the plugin on server startup/stop.
 */
public class Main extends JavaPlugin {

    private CommandExec commandExecutor;

    @Override
    public void onEnable() {
        commandExecutor = new CommandExec(this);
        commandExecutor.onEnable();

        this.getCommand("cp").setExecutor(commandExecutor);
        this.getLogger().info("Care Package Enabled!");
    }

    @Override
    public void onDisable() {
        commandExecutor.onDisable();
        this.getLogger().info("CarePackage disabled");
    }
}
