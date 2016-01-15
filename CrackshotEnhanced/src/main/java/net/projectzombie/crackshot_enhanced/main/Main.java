package net.projectzombie.crackshot_enhanced.main;

import net.projectzombie.crackshot_enhanced.custom_weapons.crafting.Recipes;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchments;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrels;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolts;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireModes;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazines;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.ModifierSets;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Sights;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stocks;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.FirearmActions;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.WeaponTypes;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.GunSkeletons;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns2;
import net.projectzombie.crackshot_enhanced.windows.BlockBreakListener;
import net.projectzombie.crackshot_enhanced.listeners.ShootListener;
import net.projectzombie.crackshot_enhanced.listeners.ScopeZoomListener;
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
        this.scopeListener.disable();
        this.getLogger().info("CrackshotEnhanced disabled.");
    }
    
    private boolean initializeGuns()
    {
        CSVReader.initializePlugin(this);
        if (!isInitialized("Attatchments", Attatchments.getInstance().initialize()))
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
        if (!isInitialized("Weapon Types", WeaponTypes.getInstance().initialize()))
            return false;
        if (!isInitialized("Gun Skeletons", GunSkeletons.getInstance().initialize()))
            return false;
        
        Guns2.initialize();
        return true;
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
