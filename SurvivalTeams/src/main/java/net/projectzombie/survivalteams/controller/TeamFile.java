/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
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
public class TeamFile
{
    private static Plugin PLUGIN;
    private static File TEAM_FILE;
    private static FileConfiguration TEAM_YAML;
    
    private static final String BASE      = "teams";
    private static final String BASE_PATH = BASE + ".";
    
    private static final HashMap<String, Team>     ONLINE_TEAMS   = new HashMap<>();
    private static final HashMap<UUID, TeamPlayer> ONLINE_PLAYERS = new HashMap<>();
    private TeamFile() { /* Do nothing. */ }
    
    public static boolean initialize(final Plugin plugin)
    {
        PLUGIN = plugin;
        loadConfig();
        return saveConfig();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Team create/disband functions
    //
    public static boolean writeTeam(final TeamPlayer creator,
                                    final String teamName)
    {
        return false;
    }
    
    static public boolean removeTeam(final Team team)
    {
        TEAM_YAML.set(BASE_PATH + team.toFileName(), null);
        return saveConfig();
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Leader functions
    //
    
    ////////////////////////////////////////////////////////////////////////////
    // Officer functions
    //
    public static boolean writeSpawn(final Team team)
    {
        return false;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Team functions
    //
    public static boolean writePromotion(final TeamPlayer sender,
                                         final TeamPlayer reciever,
                                         final TeamRank rank)
    {
        return false;
    }
    


    
    public static boolean writePlayerToTeam(final Team team,
                                            final TeamPlayer player,
                                            final TeamRank rank)
    {
        TEAM_YAML.set(getPlayerPath(team, player), rank.toFileName());
        return saveConfig();
    }
    
    public static boolean removePlayerFromTeam(final Team team,
                                               final TeamPlayer player)
    {
        TEAM_YAML.set(getPlayerPath(team, player), null);
        return saveConfig();
    }
    
    public static String findPlayerTeam(final Player player)
    {
        final UUID playerUUID = player.getUniqueId();
        
        for (String teamName : TEAM_YAML.getConfigurationSection(BASE).getKeys(false))
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
        return TEAM_YAML.contains(BASE_PATH + teamFileName.trim());
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
    
    private static Set<String> getTeamPlayers(final String teamName)
    {
        return TEAM_YAML.getConfigurationSection(BASE_PATH + teamName).getKeys(false);
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
        if (TEAM_FILE == null)
            TEAM_FILE = new File(PLUGIN.getDataFolder(), "survival_teams.yml");

        TEAM_YAML = new YamlConfiguration();
        TEAM_YAML = YamlConfiguration.loadConfiguration(TEAM_FILE);
        saveConfig();
    }

    /**
     * Saves all changes to file and file configuration.
     */
    private static boolean saveConfig()
    {
        if (TEAM_FILE == null || TEAM_YAML == null)
            return false;
        
        try
        {
            TEAM_YAML.save(TEAM_FILE);
            return true;
        } catch (IOException e)
        {
            PLUGIN.getLogger().log(Level.SEVERE, "Could not save config to " + TEAM_YAML.getName(), e);
            return false;
        }
    }
}
