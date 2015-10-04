/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compassnavigation;
import org.bukkit.plugin.java.JavaPlugin;
/**
 *
 * @author sam
 */
public class Main extends JavaPlugin {
    
    @Override
    public void onEnable()
    {
        this.getLogger().info("CompassNavigation Enabled");
        
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info("CompassNavigation disabled.");
    }

}
