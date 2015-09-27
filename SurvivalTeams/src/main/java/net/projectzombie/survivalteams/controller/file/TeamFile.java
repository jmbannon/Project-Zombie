/*
 * SurvivalTeams
 *
 * Version:     0.5
 * MC Build:    1.8.3
 * Date:        09-22-2015
 *
 * Author:      Jesse Bannon
 * Email:       jbann1994@gmail.com
 * Server:      Project Zombie
 * Website:     www.projectzombie.net
 *
 * Allows players to create rank-based Teams. Includes features such as no
 * team PVP and a group spawn.
 *
*/
package net.projectzombie.survivalteams.controller.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
    
    private static final String ROOT      = "teams";
    public static  final String ROOT_PATH = ROOT + ".";
    
    protected static final HashMap<String, Team>     ONLINE_TEAMS     = new HashMap<>();
    protected static final HashMap<UUID, TeamPlayer> ONLINE_PLAYERS   = new HashMap<>();

    private TeamFile() { /* Do nothing. */ }
    
    public static boolean initialize(final Plugin plugin)
    {
        PLUGIN = plugin;
        loadConfig();
        return saveConfig();
    }
    
    public static boolean onDisable()
    {
        return saveConfig();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Write functions
    //
    public static boolean writeTeam(final TeamPlayer creator,
                                    final String teamName)
    {
        TEAM_YAML.set(FilePath.getLeaderPath(creator, teamName), TeamRank.LEADER.getRank());
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

    public static boolean writeRank(final TeamPlayer reciever,
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
    // Accessor functions
    //
    
    static public ArrayList<Team> getOnlineTeams()
    {
        final ArrayList<Team> sortedTeams = new ArrayList<>();
        final Collection<Team> teams = ONLINE_TEAMS.values();
        
        for (Team team : teams)
            sortedTeams.add(team);
        
        Collections.sort(sortedTeams);
        return sortedTeams;
    }
    
    static public TeamRank getMemberRank(final String teamName,
                                         final UUID uuid)
    {
        final String memberPath = FilePath.getUUIDMemberPath(teamName) + "." + uuid.toString();
        return TEAM_YAML.contains(memberPath) ? TeamRank.getRank(TEAM_YAML.getInt(memberPath)) : TeamRank.NULL;
    }
    
    /**
     * Gets a list of member UUIDs from an existing team.
     * @param teamName Existing team name within TEAM_YAML.
     * @return 
     */
    static protected ArrayList<UUID> getMemberUUIDs(final String teamName)
    {
        final ArrayList<UUID> uuids = new ArrayList<>();
        for (String uuid : TEAM_YAML.getConfigurationSection(FilePath.getUUIDMemberPath(teamName)).getKeys(false))
            uuids.add(UUID.fromString(uuid));
        return uuids;
    }
    
    static protected String getTeamName(final UUID uuid)
    {
        if (TEAM_YAML.contains(ROOT))
        {
            for (String teamName : TEAM_YAML.getConfigurationSection(ROOT).getKeys(false))
            {
                 if (containsMemberUUID(teamName, uuid) || getLeaderUUID(teamName).equals(uuid))
                     return teamName;
            }
        }
        return null;
    }
    
    static protected UUID getLeaderUUID(final String teamName)
    {
        return containsTeam(teamName) ?
            UUID.fromString(TEAM_YAML.getString(FilePath.getLeaderUUIDpath(teamName))) : null;
    }
    
    static protected Location getSpawn(final String teamName)
    {
        return WorldCoordinate.toLocation(TEAM_YAML.getString(FilePath.getTeamPath(teamName) + ".spawn"));
    }
    
    static public TeamPlayer getPlayer(final UUID uuid)
    {
        return ONLINE_PLAYERS.get(uuid);
    }
    
    static public Team getTeam(final String teamName)
    {
        return ONLINE_TEAMS.get(teamName);
    }
    
    static public TeamPlayer getPlayer(final String name)
    {
        final Player player = Bukkit.getPlayer(name);
        return ONLINE_PLAYERS.get(player.getUniqueId());
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Boolean functions
    //
    
    static protected boolean containsMemberUUID(final String teamName,
                                                final UUID uuid)
    {
        return TEAM_YAML.contains(FilePath.getTeamPath(teamName) + ".members." + uuid.toString());
    }
    
    
    public static boolean containsTeam(String teamName)
    {
        return TEAM_YAML.contains(FilePath.getTeamPath(teamName));
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    // File functions
    //
    
     /**
     * Loads file from plugin folder.
     */
    protected static void loadConfig()
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
    protected static boolean saveConfig()
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
