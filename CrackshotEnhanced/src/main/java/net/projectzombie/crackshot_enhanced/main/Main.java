package net.projectzombie.crackshot_enhanced.main;

import net.projectzombie.crackshot_enhanced.custom_weapons.crafting.Recipes;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrel2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolt2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireMode2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazine2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.ModifierSet2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stock2;
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
        initializeGuns();
        
        
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
    
    private void initializeGuns()
    {
        CSVReader.initializePlugin(this);
        Attatchment2.initializeAttatchments();
        Barrel2.initializeBarrels();
        Bolt2.initializeBolts();
        FireMode2.initializeFireModes();
        Magazine2.initializeFireModes();
        Scope2.initializeScopes();
        Stock2.initializeStocks();
        ModifierSet2.initializeModifierSets();
    }
}
