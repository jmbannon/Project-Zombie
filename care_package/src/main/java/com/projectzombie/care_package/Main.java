/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author jbannon
 */
public class Main extends JavaPlugin {
    
    private DropConfig dropExec;
    
    @Override
    public void onEnable() {
	dropExec = new DropConfig(this);
	dropExec.loadConfig();
	
	this.getCommand("cp").setExecutor(dropExec);
	
	this.getLogger().info("Care Package Enabled!");
    }
    
    @Override
    public void onDisable() {
	dropExec.restoreState();
	this.getLogger().info("CarePackage disabled");
    }
    
}
