package net.projectzombie.crackshot_enhanced.main;

import net.projectzombie.crackshot_enhanced.windows.BlockBreakListener;
import net.projectzombie.crackshot_enhanced.custom_weapons.ShootListener;
import net.projectzombie.crackshot_enhanced.custom_weapons.ScopeZoomListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private BlockBreakListener windowListener;
    private ScopeZoomListener scopeListener;
    private OPCommandExec OPexec;
    private GunSmithCommandExec gunsmithExec;
    private ShootListener shootListener;

    @Override
    public void onEnable()
    {
        this.windowListener = new BlockBreakListener();
        this.OPexec = new OPCommandExec(this);
        this.getCommand("bw").setExecutor(OPexec);
        this.getServer().getPluginManager().registerEvents(windowListener, this);

        this.gunsmithExec = new GunSmithCommandExec();
        this.scopeListener = new ScopeZoomListener();
        this.shootListener = new ShootListener();

        this.getCommand("gunsmith").setExecutor(gunsmithExec);
        this.getCommand("gs").setExecutor(gunsmithExec);

        this.getServer().getPluginManager().registerEvents(scopeListener, this);
        this.getServer().getPluginManager().registerEvents(shootListener, this);

        this.getLogger().info("CrackshotEnhanced enabled!");

    }

    @Override
    public void onDisable()
    {
        this.scopeListener.disable();
        this.getLogger().info("CrackshotEnhanced disabled.");
    }
}
