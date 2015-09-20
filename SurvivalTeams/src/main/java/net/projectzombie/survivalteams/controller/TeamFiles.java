/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import net.projectzombie.survivalteams.player.TeamPlayer;
import net.projectzombie.survivalteams.team.Team;
import net.projectzombie.survivalteams.team.TeamRank;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jb
 */
public class TeamFiles
{
    private static Plugin PLUGIN;
    private static File teamFile;
    private static FileConfiguration teamYAML;
    
    private static final String BASE_PATH = "teams.";
    
    private TeamFiles() { /* Do nothing. */ }
    
    public static boolean initialize(final Plugin plugin)
    {
        PLUGIN = plugin;
        loadConfig();
        return saveConfig();
    }
    
    public static boolean writePlayerToTeam(final Team team,
                                            final TeamPlayer player,
                                            final TeamRank rank)
    {
        teamYAML.set(getPlayerPath(team, player), rank.toFileName());
        return saveConfig();
    }
    
    public static boolean removePlayerFromTeam(final Team team,
                                               final TeamPlayer player)
    {
        teamYAML.set(getPlayerPath(team, player), null);
        return saveConfig();
    }
    
    public static boolean removeTeam(final Team team)
    {
        teamYAML.set(BASE_PATH + team.toFileName(), null);
        return saveConfig();
    }
    
    public static boolean teamExists(final Team team)
    {
        return teamYAML.contains(BASE_PATH + team.toFileName());
    }
            

// ===========================================================================
//            
//    Private
//            
// ===========================================================================         

    
    private static String getPlayerPath(final Team team,
                                        final TeamPlayer player)
    {
        return BASE_PATH + team.toFileName() + "." + player.toFileName();
    }
    
     /**
     * Loads file from plugin folder.
     */
    private static void loadConfig()
    {
        if (teamFile == null)
            teamFile = new File(PLUGIN.getDataFolder(), "survival_teams.yml");

        teamYAML = new YamlConfiguration();
        teamYAML = YamlConfiguration.loadConfiguration(teamFile);
        saveConfig();
    }

    /**
     * Saves all changes to file and file configuration.
     */
    private static boolean saveConfig()
    {
        if (teamFile == null || teamYAML == null)
            return false;
        
        try
        {
            teamYAML.save(teamFile);
            return true;
        } catch (IOException e)
        {
            PLUGIN.getLogger().log(Level.SEVERE, "Could not save config to " + teamYAML.getName(), e);
            return false;
        }
    }
}
