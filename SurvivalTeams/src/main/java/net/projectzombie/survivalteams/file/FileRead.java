/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.file;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import static net.projectzombie.survivalteams.file.FileContents.*;
import static net.projectzombie.survivalteams.file.FilePath.*;
import net.projectzombie.survivalteams.team.Team;
import net.projectzombie.survivalteams.team.TeamRank;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author com.gmail.jbann1994
 */
public class FileRead
{

    public static final int ATTACK_DEFAULT = 10;

    static public int getSBWeaponDamage(Material material)
    {
        return TEAM_YAML.contains(FilePath.sBWeaponDamage(material)) ?
                TEAM_YAML.getInt(FilePath.sBWeaponDamage(material)) : 0;
    }

    static public int getSBDefaultDurability()
    {
        return TEAM_YAML.contains(FilePath.sBDefaultDurability()) ?
                TEAM_YAML.getInt(FilePath.sBDefaultDurability()) : 0;
    }

    static public short getSBWeaponDurability(Material material)
    {
        return TEAM_YAML.contains(FilePath.sBWeaponDurability(material)) ?
                (short) (TEAM_YAML.getInt(FilePath.sBWeaponDurability(material))) : 0;
    }

    static public Set<String> getSBWeapons()
    {
        return TEAM_YAML.contains(FilePath.sBWeapons()) ?
                TEAM_YAML.getConfigurationSection(FilePath.sBWeapons()).getKeys(false) : null;
    }

    static public int getDefaultDamage()
    {
        return TEAM_YAML.contains(FilePath.sBDefaultDamage()) ?
                TEAM_YAML.getInt(FilePath.sBDefaultDamage()) : 0;
    }

    static public Material getSBTool()
    {
        return TEAM_YAML.contains(FilePath.sBCheckTool()) ?
                Material.valueOf(TEAM_YAML.getString(FilePath.sBCheckTool())) : null;
    }

    static public boolean getBreakNaturally()
    {
        return TEAM_YAML.contains(FilePath.breakNaturally()) ?
                TEAM_YAML.getBoolean(FilePath.breakNaturally()) : false;
    }

    static public int getAttackDelay()
    {
        return TEAM_YAML.contains(FilePath.attackDelay()) ?
                TEAM_YAML.getInt(FilePath.attackDelay()) : ATTACK_DEFAULT;
    }

    static public int getBuildRadius() {
        return TEAM_YAML.contains(FilePath.buildRadius()) ?
                TEAM_YAML.getInt(FilePath.buildRadius()) : -1;
    }

    static public Set<String> getDefaultSBlocks() {
        return TEAM_YAML.contains(FilePath.defaultBlocks()) ?
                TEAM_YAML.getConfigurationSection(FilePath.defaultBlocks()).getKeys(false) : null;
    }

    static public int getDefaultSBlockHealth(String material) {
        return TEAM_YAML.contains(FilePath.defaultBlockHealth(material)) ?
                TEAM_YAML.getInt(FilePath.defaultBlockHealth(material)) : -1;
    }

    static public Set<String> getTeamSBlocks() {
        return TEAM_YAML.contains(FilePath.rootTeamBlocks()) ?
                TEAM_YAML.getConfigurationSection(FilePath.rootTeamBlocks()).getKeys(false) : null;
    }

    static public int getTeamSBlockHealth(String teamName, Location loc) {
        return TEAM_YAML.contains(FilePath.teamBlockHealth(teamName, loc)) ?
                TEAM_YAML.getInt(FilePath.teamBlockHealth(teamName, loc)) : -1;
    }

    static public int getTeamSBlockHealth(String iD) {
        return TEAM_YAML.contains(FilePath.teamBlockHealth(iD)) ?
                TEAM_YAML.getInt(FilePath.teamBlockHealth(iD)) : -1;
    }

    static public TeamRank getMemberRank(final String teamName,
                                         final UUID uuid)
    {
        final String memberPath = FilePath.members(teamName) + "." + uuid.toString() + ".rank";
        return TEAM_YAML.contains(memberPath) ? TeamRank.getIntRank(TEAM_YAML.getString(memberPath)) : TeamRank.NULL;
    }
    
    static public String getMemberName(final String teamName,
                                       final UUID uuid)
    {
        final String memberPath = FilePath.members(teamName) + "." + uuid.toString() + ".name";
        return TEAM_YAML.contains(memberPath) ? TEAM_YAML.getString(memberPath) : null;
    }
    
    static public String getLeaderName(final String teamName,
                                       final UUID uuid)
    {
        final String leaderPath = FilePath.leader(teamName) + "." + uuid.toString();
        return TEAM_YAML.contains(leaderPath) ? TEAM_YAML.getString(leaderPath) : null;
    }
    
    /**
     * Gets a list of member UUIDs from an existing team.
     * @param teamName Existing team name within TEAM_YAML.
     * @return 
     */
    public static ArrayList<UUID> getMemberUUIDs(final String teamName)
    {
        final ArrayList<UUID> uuids = new ArrayList<>();
        final String memberPath = FilePath.members(teamName);
        
        if (TEAM_YAML.contains(memberPath))
        {
            for (String uuid : TEAM_YAML.getConfigurationSection(memberPath).getKeys(false))
                uuids.add(UUID.fromString(uuid));
        }
        return uuids;
    }
    
    static protected boolean containsMemberUUID(final String teamName,
                                                final UUID uuid)
    {
        return TEAM_YAML.contains(FilePath.member(teamName, uuid));
    }
        
    public static String getTeamName(final UUID uuid)
    {
        final String teamsPath = FilePath.teams();
        if (TEAM_YAML.contains(teamsPath))
        {
            for (String teamName : TEAM_YAML.getConfigurationSection(teamsPath).getKeys(false))
            {
                 if (containsMemberUUID(teamName, uuid) || getLeaderUUID(teamName).equals(uuid))
                     return teamName;
            }
        }
        return null;
    }
    
    public static UUID getLeaderUUID(final String teamName)
    {
        if (containsTeam(teamName))
        {
            for (String leaderUUID : TEAM_YAML.getConfigurationSection(FilePath.leader(teamName)).getKeys(false))
                return UUID.fromString(leaderUUID);
        }
        return null;
    }
    
    public static Location getSpawn(final String teamName)
    {
        final String spawnPath = FilePath.team(teamName) + ".spawn";
        return TEAM_YAML.contains(spawnPath) ? WorldCoordinate.toLocation(TEAM_YAML.getString(spawnPath)) : null;
    }
    
    public static boolean containsTeam(final String teamName)
    {
        return TEAM_YAML.contains(FilePath.team(teamName));
    }
}
