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
package net.projectzombie.survivalteams.controller;

import java.util.*;

import net.md_5.bungee.api.ChatColor;
import net.projectzombie.survivalteams.block.SurvivalBlock;
import net.projectzombie.survivalteams.file.FileRead;
import net.projectzombie.survivalteams.file.buffers.PlayerBuffer;
import net.projectzombie.survivalteams.file.buffers.SBlockBuffer;
import net.projectzombie.survivalteams.file.buffers.TeamBuffer;
import net.projectzombie.survivalteams.player.TeamPlayer;
import net.projectzombie.survivalteams.team.Team;
import net.projectzombie.survivalteams.team.TeamRank;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

/**
 *
 * @author jb
 */
public class PlayerListener implements Listener
{
    private static Set<PotionEffectType> bannedPVPPotionEffects;
    private static JavaPlugin plugin;

    public static void setPlugin(JavaPlugin pluginN)
    {
        plugin = pluginN;
    }

    @EventHandler
    public void showSBlockInfo(PlayerInteractEvent event)
    {
        if (event.getMaterial() == SBlockBuffer.getTool())
        {
            if (event.getPlayer().hasPermission(CMDText.SB_PERMS))
            {
                Player player = event.getPlayer();
                Set<Material> transparentB = null;
                Block block = player.getTargetBlock(transparentB, 4);

                if ((SBlockBuffer.getPlaceableBlocks()).contains(block.getType()))
                {
                    SurvivalBlock sB = SBlockBuffer.getSB(block.getLocation());
                    if (sB != null)
                    {
                        player.sendMessage("[" + ChatColor.BLUE + "Block"  + ChatColor.RESET + "]: ");
                        player.sendMessage("    Health: " + sB.getHealth());
                        player.sendMessage("    Team: " + sB.getTeamName());
                    }
                    else
                        player.sendMessage("This is not a survival block.");
                }
            }
        }
    }

    @EventHandler
    public void trackSBlocks(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if ((SBlockBuffer.getPlaceableBlocks()).contains(block.getType()))
        {
            TeamPlayer tP = PlayerBuffer.get(event.getPlayer().getUniqueId());
            if (tP != null)
            {
                Team team = tP.getTeam();
                if (team != null)
                {
                    Location spawn = team.getSpawn();
                    if (spawn != null &&
                        Math.abs(spawn.distance(block.getLocation())) <= SBlockBuffer.getBuildRadius())
                    {
                        event.setCancelled(false);
                        SurvivalBlock.createSBlock(block, team.getName());
                    }
                }
            }
        }
    }

    @EventHandler
    public void trackSPlayerDamage(BlockDamageEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if ((SBlockBuffer.getPlaceableBlocks()).contains(block.getType()))
        {
            SurvivalBlock sB = SBlockBuffer.getSB(block.getLocation());
            if (sB != null)
            {
                sB.takeDamage(player.getInventory());
            }
        }
    }

    @EventHandler
    public void trackSCreatureDamage(EntityTargetEvent event)
    {
        if (event.getEntity() instanceof Zombie)
        {
            Zombie zombie = (Zombie) event.getEntity();
            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(plugin,
                         new CreatureChase(zombie, scheduler, plugin),
                         SBlockBuffer.getAttackDelay());
        }
    }

    @EventHandler
    public void correctPVPHit(EntityDamageByEntityEvent event)
    {
        //TODO Once updated to 1.10, area effect clouds will need to be taken into account.
        if (event.getEntity() instanceof Player)
        {

            // Handles all pvp with direct harm.
            TeamPlayer p2 = PlayerBuffer.get(((Player) event.getEntity()).getUniqueId());
            if (event.getDamager() instanceof Player)
            {
                TeamPlayer p1 = PlayerBuffer.get(((Player) event.getDamager()).getUniqueId());

                // Check if event should be cancelled, must be in if,
                // or will always cancel or always enable.
                // Allows other plugins to change event state.
                if (p1 != null && p1.getTeam() == p2.getTeam())
                {
                    event.setCancelled(true);
                }
            }
            else if (event.getDamager() instanceof Projectile ||
                        event.getDamager() instanceof ThrownPotion) { // Handles non-direct pvp.
                TeamPlayer p1 = PlayerBuffer.get(((Player) ((Projectile) event.getDamager())
                                .getShooter()).getUniqueId());

                // More potion effects will have to be added in 1.10
                if (bannedPVPPotionEffects == null)
                {
                    bannedPVPPotionEffects = new HashSet<>();
                    bannedPVPPotionEffects.add(PotionEffectType.BLINDNESS);
                    bannedPVPPotionEffects.add(PotionEffectType.CONFUSION);
                    bannedPVPPotionEffects.add(PotionEffectType.HARM);
                    bannedPVPPotionEffects.add(PotionEffectType.HUNGER);
                    bannedPVPPotionEffects.add(PotionEffectType.POISON);
                    bannedPVPPotionEffects.add(PotionEffectType.SLOW);
                    bannedPVPPotionEffects.add(PotionEffectType.SLOW_DIGGING);
                    bannedPVPPotionEffects.add(PotionEffectType.WEAKNESS);
                    bannedPVPPotionEffects.add(PotionEffectType.WITHER);
                }
                if (event.getDamager() instanceof ThrownPotion)
                {
                    Collection<PotionEffect> effects = ((ThrownPotion) event.getDamager()).getEffects();
                    for (PotionEffect effect : effects)
                    {
                        if (bannedPVPPotionEffects.contains(effect.getType()) &&
                                 p1 != null && p1.getTeam() == p2.getTeam())
                        {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }

                if (p1 != null && p1.getTeam() == p2.getTeam())
                {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void spawnToCorrectBase(PlayerRespawnEvent event)
    {
        Player player = event.getPlayer();
        Location loc = PlayerBuffer.getSpawnLocation(player);
        if (loc != null)
        {
            event.setRespawnLocation(loc);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void AssignTeamPlayerOnLoogin(final PlayerLoginEvent event)
    {
        initializePlayer(event.getPlayer());
    }
    
    public void removeTeamPlayerOnLogout(final PlayerQuitEvent event)
    {
        PlayerBuffer.remove(event.getPlayer().getUniqueId());
    }
    
    /**
     * To be called when a player logs into the server.
     * @param player
     * @return The player's initialized TeamPlayer object.
     */
    static public TeamPlayer initializePlayer(final Player player)
    {
        final UUID uuid = player.getUniqueId();
        final String teamName;
        final TeamPlayer teamPlayer;

        if ((teamName = FileRead.getTeamName(uuid)) != null)
        {
            Team team = getTeam(teamName);
            if (FileRead.getLeaderUUID(teamName).equals(uuid))
                teamPlayer = new TeamPlayer(player, team, TeamRank.LEADER);
            else
                teamPlayer = new TeamPlayer(player, team, FileRead.getMemberRank(teamName, uuid));
        }
        else
            teamPlayer = new TeamPlayer(player);
        
        PlayerBuffer.add(teamPlayer);
        return teamPlayer;
    }
    
    /**
     * Gets the team by name if one of its players are online and the team is
     * within ONLINE_TEAMS.
     * 
     * @param teamName Name of the team.
     * @return  Team of the given name if one of the players is online.
     */
    static private Team getTeam(final String teamName)
    {
        final Team team;
        if (TeamBuffer.contains(teamName))
            team = TeamBuffer.get(teamName);
        else
            team = new Team(teamName, FileRead.getLeaderUUID(teamName), FileRead.getMemberUUIDs(teamName), FileRead.getSpawn(teamName));
        
        TeamBuffer.add(team);
        return team;
    }
    
}
