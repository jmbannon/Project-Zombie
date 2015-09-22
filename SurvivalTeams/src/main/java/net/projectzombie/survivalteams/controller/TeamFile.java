/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import net.projectzombie.survivalteams.player.TeamPlayer;
import net.projectzombie.survivalteams.team.Team;
import net.projectzombie.survivalteams.team.TeamRank;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jb
 * 
 * To only handle files; nothing more.
 */
public class TeamFile
{
    private static Plugin PLUGIN;
    private static File TEAM_FILE;
    private static FileConfiguration TEAM_YAML;
    
    private static final String BASE      = "teams";
    public static  final String BASE_PATH = BASE + ".";
    
    private static final HashMap<String, Team>     ONLINE_TEAMS     = new HashMap<>();
    private static final HashMap<UUID, TeamPlayer> RELEVANT_PLAYERS = new HashMap<>();

    private TeamFile() { /* Do nothing. */ }
    
    public static boolean initialize(final Plugin plugin)
    {
        PLUGIN = plugin;
        loadConfig();
        return saveConfig();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Write functions
    //
    public static boolean writeTeam(final TeamPlayer creator,
                                    final String teamName)
    {
        TEAM_YAML.set(getLeaderPath(creator, teamName), TeamRank.LEADER.getRank());
        return saveConfig();
    }
    
    static public boolean removeTeam(final Team team)
    {
        TEAM_YAML.set(team.getPath(), null);
        return saveConfig();
    }
    
    public static boolean writeSpawn(final Team team,
                                     final Location location)
    {
        TEAM_YAML.set(team.getPath() + ".spawn", location);
        return saveConfig();
    }
    
    static public boolean removeSpawn(final Team team)
    {
        TEAM_YAML.set(team.getPath() + ".spawn", null);
        return saveConfig();
    }

    public static boolean writePromotion(final TeamPlayer reciever,
                                         final TeamRank rank)
    {
        TEAM_YAML.set(reciever.getPath(), rank.getRank());
        return saveConfig();
    }
    
    public static boolean writePlayerToTeam(final Team team,
                                            final TeamPlayer player,
                                            final TeamRank rank)
    {
        TEAM_YAML.set(player.getPath(), rank.toFileName());
        return saveConfig();
    }
    
    public static boolean removePlayerFromTeam(final Team team,
                                               final TeamPlayer player)
    {
        TEAM_YAML.set(player.getPath(), null);
        return saveConfig();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Read functions
    //
    
    
    
    static TeamRank getMemberRank(final String teamName,
                                  final UUID uuid)
    {
        final String memberPath = getUUIDMemberPath(teamName) + "." + uuid.toString();
        return TEAM_YAML.contains(memberPath) ? TeamRank.getRank(TEAM_YAML.getInt(memberPath)) : TeamRank.NULL;
    }
    
    /**
     * Gets a list of member UUIDs from an existing team.
     * @param teamName Existing team name within TEAM_YAML.
     * @return 
     */
    static private ArrayList<UUID> getMemberUUIDs(final String teamName)
    {
        final ArrayList<UUID> uuids = new ArrayList<>();
        for (String uuid : TEAM_YAML.getConfigurationSection(getUUIDMemberPath(teamName)).getKeys(false))
            uuids.add(UUID.fromString(uuid));
        return uuids;
    }
    
    static private boolean containsMember(final String teamName,
                                          final UUID uuid)
    {
        return TEAM_YAML.contains(getTeamPath(teamName) + ".members." + uuid.toString());
    }
    
    static private UUID getLeaderUUID(final String teamName)
    {
        return teamExists(teamName) ?
            UUID.fromString(TEAM_YAML.getString(getLeaderUUIDpath(teamName))) : null;
    }
    
    
    static public TeamPlayer getOnlineTeamPlayer(final UUID uuid)
    {
        return RELEVANT_PLAYERS.get(uuid);
    }
   
    
    ////////////////////////////////////////////////////////////////////////////
    // Path functions
    // 
    
    public static boolean teamExists(String teamName)
    {
        return TEAM_YAML.contains(getTeamPath(teamName));
    }
    
     public static String getTeamPath(final String teamName)
    { 
        return BASE_PATH + teamName;
    }
    
    public static String getLeaderUUIDpath(final String teamName)
    {
        return getTeamPath(teamName) + ".leader";
    }
     
    public static String getUUIDMemberPath(final String teamName)
    {
        return getTeamPath(teamName) + ".members";
    }
    
    public static String getPlayerPath(final TeamPlayer player,
                                       final String teamName) 
    { 
        return player.hasTeam() ? getUUIDMemberPath(teamName) + player.getUUID().toString() : null; 
    }
    public static String getLeaderPath(final TeamPlayer player,
                                       final String teamName)
    {
        return player.hasTeam() ? getLeaderUUIDpath(teamName) : null;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // File functions
    //
    
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
