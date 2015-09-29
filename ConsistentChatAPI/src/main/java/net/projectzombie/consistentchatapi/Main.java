/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.consistentchatapi;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author com.gmail.jbann1994
 */
public class Main extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        this.getLogger().info("ConsistentChatAPI Enabled.");
        
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info("ConsistentChatAPI disabled.");
    }
}
