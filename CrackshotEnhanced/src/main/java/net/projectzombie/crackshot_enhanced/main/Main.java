package net.projectzombie.crackshot_enhanced.main;

import net.projectzombie.crackshot_enhanced.windows.BlockBreakListener;
import net.projectzombie.crackshot_enhanced.custom_weapons.ShootListener;
import net.projectzombie.crackshot_enhanced.custom_weapons.ScopeZoomListener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin
{
	private BlockBreakListener window;
	private ScopeZoomListener scope;
	private OPCommandExec OPexec;
    private GunSmithCommandExec gunsmithExec;
    private ShootListener decay;
	
	@Override
	public void onEnable()
    {
            this.window = new BlockBreakListener();
            this.OPexec = new OPCommandExec(this);
            this.getCommand("bw").setExecutor(OPexec);
            this.getServer().getPluginManager().registerEvents(window, this);

        
        this.gunsmithExec = new GunSmithCommandExec();
		this.scope = new ScopeZoomListener();
        this.decay = new ShootListener();
		
		this.getCommand("gunsmith").setExecutor(gunsmithExec);
        this.getCommand("gs").setExecutor(gunsmithExec);
        
		this.getServer().getPluginManager().registerEvents(scope, this);
        this.getServer().getPluginManager().registerEvents(decay, this);
		
		this.getLogger().info("CrackshotEnhanced enabled!");

	}
	
	@Override
	public void onDisable()
    {
		this.scope.disable();
		this.getLogger().info("CrackshotEnhanced disabled.");
	}	
}
