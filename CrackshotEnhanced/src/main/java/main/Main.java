package main;

import custom_weapons.crafting.Recipes;
import custom_weapons.csv.CSVReader;
import custom_weapons.modifiers.Attachments;
import custom_weapons.modifiers.ProjectileAttachments;
import custom_weapons.modifiers.Barrels;
import custom_weapons.modifiers.Bolts;
import custom_weapons.modifiers.FireModes;
import custom_weapons.modifiers.Magazines;
import custom_weapons.modifiers.ModifierSets;
import custom_weapons.modifiers.Sights;
import custom_weapons.modifiers.Stocks;
import custom_weapons.skeleton.FirearmActions;
import custom_weapons.skeleton.SkeletonTypes;
import custom_weapons.skeleton.GunSkeletons;
import custom_weapons.weps.Guns;
import windows.BlockBreakListener;
import listeners.ShootListener;
import listeners.ScopeZoomListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private BlockBreakListener windowListener;
    private ScopeZoomListener scopeListener;
    private OPCommandExec OPexec;
    private GunSmithCommandExec gunsmithExec;
    private ShootListener shootListener;
    private Recipes recipes;

    @Override
    public void onEnable()
    {
        if (!initializeGuns())
            return;
        
        this.windowListener = new BlockBreakListener();
        this.OPexec = new OPCommandExec(this);
        this.getCommand("bw").setExecutor(OPexec);
        this.getServer().getPluginManager().registerEvents(windowListener, this);

        this.gunsmithExec = new GunSmithCommandExec();
        this.scopeListener = new ScopeZoomListener();
        this.shootListener = new ShootListener();
        this.recipes = new Recipes();
        
        this.getCommand("gunsmith").setExecutor(gunsmithExec);
        this.getCommand("gs").setExecutor(gunsmithExec);

        this.getServer().getPluginManager().registerEvents(scopeListener, this);
        this.getServer().getPluginManager().registerEvents(shootListener, this);
        this.getServer().getPluginManager().registerEvents(recipes, this);

        Recipes.initializeCraftingRecipes();
        this.getLogger().info("CrackshotEnhanced enabled!");

    }

    @Override
    public void onDisable()
    {
        if (this.scopeListener != null)
            this.scopeListener.disable();
        this.getLogger().info("CrackshotEnhanced disabled.");
    }
    
    private boolean initializeGuns()
    {
        CSVReader.initializePlugin(this);
        if (!isInitialized("Attatchments", Attachments.getInstance().initialize()))
            return false;
        if (!isInitialized("Barrels", Barrels.getInstance().initialize()))
            return false;
        if (!isInitialized("Bolts", Bolts.getInstance().initialize()))
            return false;
        if (!isInitialized("FireModes", FireModes.getInstance().initialize()))
            return false;
        if (!isInitialized("Magazines", Magazines.getInstance().initialize()))
            return false;
        if (!isInitialized("Sights", Sights.getInstance().initialize()))
            return false;
        if (!isInitialized("Stocks", Stocks.getInstance().initialize()))
            return false;
        if (!isInitialized("Modifier Sets", ModifierSets.getInstance().initialize()))
            return false;
        if (!isInitialized("Firearm Action", FirearmActions.getInstance().initialize()))
            return false;
        if (!isInitialized("Weapon Types", SkeletonTypes.getInstance().initialize()))
            return false;
        if (!isInitialized("Gun Skeletons", GunSkeletons.getInstance().initialize()))
            return false;
        
        return isInitialized("Guns", Guns.initialize());
    }
    
    private boolean isInitialized(final String toInitialize,
                                  final int returned)
    {
        if (returned >= 0)
        {
            System.out.println("[Crackshot Enhanced] Initialized " + returned + " " + toInitialize);
            return true;
        }
        else
        {
            System.out.println("[Crackshot Enhanced] FATAL: Could not initialize " + toInitialize + ". Disabling plugin.");
            return false;
        }
    }
}
