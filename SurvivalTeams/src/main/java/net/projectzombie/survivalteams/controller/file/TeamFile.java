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
import java.util.UUID;
import java.util.logging.Level;
import net.projectzombie.survivalteams.player.TeamPlayer;
import net.projectzombie.survivalteams.team.Team;
import net.projectzombie.survivalteams.team.TeamRank;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
                                    final Team team)
    {
        final String teamName = team.getName();
        final boolean savedSuccessfully;
        
        TEAM_YAML.set(FilePath.getLeaderPath(teamName) + "." + creator.getUUID().toString(), creator.getPlayerName());
        savedSuccessfully = saveConfig();
        
        if (savedSuccessfully)
            ONLINE_TEAMS.put(teamName, team);
        
        return savedSuccessfully;
    }
    
    static public boolean removeTeam(final Team team)
    {
        TEAM_YAML.set(team.getPath(), null);
        return saveConfig();
    }
    
    public static boolean writeSpawn(final Team team,
                                     final Location location)
    {
        TEAM_YAML.set(team.getPath() + ".spawn", WorldCoordinate.toWorldCoordinate(location.getBlock()));
        return saveConfig();
    }
    
    static public boolean removeSpawn(final Team team)
    {
        TEAM_YAML.set(team.getPath() + ".spawn", null);
        return saveConfig();
    }
    
    static public boolean removeAllTeamSpawns()
    {
        Location spawnLocation;
        String spawnPath;
        Block bannerBlock;
        if (TEAM_YAML.contains(ROOT))
        {
            for (String teamName : TEAM_YAML.getConfigurationSection(ROOT).getKeys(false))
            {
                spawnPath = FilePath.getTeamPath(teamName) + ".spawn";
                spawnLocation = WorldCoordinate.toLocation(TEAM_YAML.getString(spawnPath));
                if (spawnLocation != null)
                {
                    bannerBlock = spawnLocation.getBlock().getRelative(BlockFace.DOWN);
                    if (bannerBlock.getType().equals(Material.STANDING_BANNER))
                        bannerBlock.setType(Material.AIR);
                }
                    
                TEAM_YAML.set(spawnPath, null);
            }
        }
        return saveConfig();
    }

    public static boolean writePlayerToTeam(final Team team,
                                            final TeamPlayer reciever,
                                            final TeamRank rank)
    {
        if (!reciever.isLeader())
        {
            TEAM_YAML.set(reciever.getPath(team) + ".rank", rank.toFileName());
            TEAM_YAML.set(reciever.getPath(team) + ".name", reciever.getPlayerName());
            return saveConfig();
        }
        else
            return false;
    }
    
    public static boolean removePlayerFromTeam(final Team team,
                                               final TeamPlayer player)
    {
        TEAM_YAML.set(player.getPath(team), null);
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
        final String memberPath = FilePath.getUUIDMemberPath(teamName) + "." + uuid.toString() + ".rank";
        return TEAM_YAML.contains(memberPath) ? TeamRank.getIntRank(TEAM_YAML.getString(memberPath)) : TeamRank.NULL;
    }
    
    static public String getMemberName(final String teamName,
                                       final UUID uuid)
    {
        final String memberPath = FilePath.getUUIDMemberPath(teamName) + "." + uuid.toString() + ".name";
        return TEAM_YAML.contains(memberPath) ? TEAM_YAML.getString(memberPath) : null;
    }
    
    static public String getLeaderName(final String teamName,
                                       final UUID uuid)
    {
        final String leaderPath = FilePath.getLeaderPath(teamName) + "." + uuid.toString();
        return TEAM_YAML.contains(leaderPath) ? TEAM_YAML.getString(leaderPath) : null;
    }
    
    /**
     * Gets a list of member UUIDs from an existing team.
     * @param teamName Existing team name within TEAM_YAML.
     * @return 
     */
    static protected ArrayList<UUID> getMemberUUIDs(final String teamName)
    {
        final ArrayList<UUID> uuids = new ArrayList<>();
        final String memberPath = FilePath.getUUIDMemberPath(teamName);
        
        if (TEAM_YAML.contains(memberPath))
        {
            for (String uuid : TEAM_YAML.getConfigurationSection(memberPath).getKeys(false))
                uuids.add(UUID.fromString(uuid));
        }
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
        if (containsTeam(teamName))
        {
            for (String leaderUUID : TEAM_YAML.getConfigurationSection(FilePath.getLeaderPath(teamName)).getKeys(false))
                return UUID.fromString(leaderUUID);
        }
        return null;
    }
    
    static protected Location getSpawn(final String teamName)
    {
        final String spawnPath = FilePath.getTeamPath(teamName) + ".spawn";
        return TEAM_YAML.contains(spawnPath) ? WorldCoordinate.toLocation(TEAM_YAML.getString(spawnPath)) : null;
    }
    
    static public TeamPlayer getPlayer(final UUID uuid)
    {
        final TeamPlayer tp = ONLINE_PLAYERS.get(uuid);
        if (tp != null && tp.getPlayer() != null)
            return tp;
        else
            return null;
    }
    
    static public Team getTeam(final String teamName)
    {
        return ONLINE_TEAMS.get(teamName);
    }
    
    static public TeamPlayer getPlayer(final String name)
    {
        final Player player = Bukkit.getPlayer(name);
        return player != null ? ONLINE_PLAYERS.get(player.getUniqueId()) : null;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Boolean functions
    //
    
    static public boolean isValidSpawnSet(final Location location)
    {
        final Block locBlock = location.getBlock();
        return locBlock.isEmpty()
                && locBlock.getRelative(0, -1, 0).isEmpty()
                && !locBlock.getRelative(0, -2, 0).isEmpty();
    }
    
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
