/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.main;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author jesse
 */
public class Main extends JavaPlugin
{
    @Override
    public void onEnable()
    {
//        this.getDataFolder().mkdir();
//        Utilities.initialize(this);
//        commandExecutor = new CommandExec();
//        blockListener = new BlockListener();
//        toolListener = new ToolListener();
//        
//        this.getCommand("bp").setExecutor(commandExecutor);
//        this.getCommand("engrave").setExecutor(commandExecutor);
//        this.getServer().getPluginManager().registerEvents(blockListener, this);
//        this.getServer().getPluginManager().registerEvents(toolListener, this);
//        this.getLogger().info("Placable Blocks enabled!");
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info("Realistic Zombies disabled!");
        
    }
}
