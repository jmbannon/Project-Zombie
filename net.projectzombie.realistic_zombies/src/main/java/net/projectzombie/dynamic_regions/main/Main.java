/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.main;

import net.projectzombie.dynamic_regions.regions.Controller;
import net.projectzombie.dynamic_regions.utilities.Plugins;
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
        Plugins.initialize(this);
        Controller.initiate();
        this.getLogger().info("DynamicRegions enabled!");
        
        /*
        TODO:
        
        SksycraperSpawns
        Recurrent checking
        Multithreading (p/player)
        HARDCORE: Machine Learning spawn offsets
        
        */
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info("DynamicRegions disabled!");
    }
}
