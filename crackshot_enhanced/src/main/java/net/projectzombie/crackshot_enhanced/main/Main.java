package net.projectzombie.crackshot_enhanced.main;

import net.projectzombie.crackshot_enhanced.windows.BlockBreakListener;
import net.projectzombie.crackshot_enhanced.custom_weapons.ShootListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.projectzombie.crackshot_enhanced.custom_weapons.ScopeZoomListener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
	
	private BlockBreakListener window;
	private ScopeZoomListener scope;
	private CommandExec exec;
    private ShootListener decay;
	
	@Override
	public void onEnable() {
        try {
            this.window = new BlockBreakListener(this);
            this.exec = new CommandExec(this);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
		this.scope = new ScopeZoomListener();
        this.decay = new ShootListener();
		
		this.getCommand("bw").setExecutor(exec);
		
		this.getServer().getPluginManager().registerEvents(window, this);
		this.getServer().getPluginManager().registerEvents(scope, this);
        this.getServer().getPluginManager().registerEvents(decay, this);
		
		this.getLogger().info("Breakable Windows enabled!");

	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("Restoring blocks...");
        try {
            this.window.restoreGlass();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
		this.scope.disable();
		this.getLogger().info("Restored blocks! Breakable Windows disabled.");
	}	
}
