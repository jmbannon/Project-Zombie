/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import net.projectzombie.survivalteams.player.TeamPlayer;
import net.projectzombie.survivalteams.team.Team;
import net.projectzombie.survivalteams.team.TeamRank;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
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
    
    private static final String BASE      = "teams";
    private static final String BASE_PATH = BASE + ".";
    
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
    
    public static String findPlayerTeam(final Player player)
    {
        final UUID playerUUID = player.getUniqueId();
        
        for (String teamName : teamYAML.getConfigurationSection(BASE).getKeys(false))
            for (String uuid : getTeamPlayers(teamName))
                if (UUID.fromString(uuid).equals(playerUUID))
                    return teamName;
        return null;
    }
    
    public static TeamPlayer getLeader(final String teamName)
    {
        return null;
    }
    
    public static boolean teamExists(final String teamFileName)
    {
        return teamYAML.contains(BASE_PATH + teamFileName.trim());
    }
    
    public static Team getTeam(final String teamFileName)
    {
        /*
        final Team team = new Team()
        final ArrayList<TeamPlayer> players = new ArrayList<>();
        final TeamPlayer leader;
        TeamPlayer player;
        TeamRank rank;
        for (String playerUUID : getTeamPlayers(teamFileName))
        {
            rank = TeamRank.getRank(teamYAML.getInt(BASE_PATH + teamFileName + "." + playerUUID));
            player = new TeamPlayer(null, )
        }
        */
        return null;
    }
            

// ===========================================================================
//            
//    Private
//            
// ===========================================================================         
    
    private static Set<String> getTeamPlayers(final String teamName)
    {
        return teamYAML.getConfigurationSection(BASE_PATH + teamName).getKeys(false);
    }
    
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
