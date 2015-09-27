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

import static net.projectzombie.survivalteams.controller.CMDText.*;
import net.projectzombie.survivalteams.controller.file.TeamFile;
import net.projectzombie.survivalteams.player.TeamPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author jb
 */
public class PlayerCommands implements CommandExecutor
{

    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String label, String[] args)
    {
        if (!(cs instanceof Player && isCommand(label, COMMAND)))
            return true;
		
        final Player player = (Player)cs;
        final TeamPlayer sender = TeamFile.getOnlineTeamPlayer(player.getUniqueId());
        
        
        if (args.length == 1)
        {
            if (isCommand(args[0], ARG_LEAVE))
                sender.quitTeam();
            
            else if (isCommand(args[0], ARG_DISBAND))
                sender.disbandTeam();
            
            else if (isCommand(args[0], ARG_LIST))
                sender.listOnlineTeamMembers();
            
            else
                listCommands(player);
            
        }
        
        else if (args.length == 2)
        {
            if (isCommand(args[0], ARG_CREATE))
                sender.createTeam(args[1]);
            
            else if (isCommand(args[0], ARG_INVITE))
                sender.invitePlayerToTeam(args[1]);
            
            
            
            else if (isCommand(args[0], ARG_KICK))
                sender.kickPlayerFromTeam(args[1]);
            
            else if (isCommand(args[0], ARG_ACCEPT))
                sender.acceptTeamInvite(args[1]);
            else
                listCommands(player);
        }
        
        else if (args.length == 3)
        {
            if (isCommand(args[0], ARG_PROMOTE))
                sender.promotePlayer(args[1], args[2]);
            if (isCommand(args[0], ARG_DEMOTE))
                ; // DO STUFF
            else
                listCommands(player);
        }
        
        return true;
    }
    
    private boolean isCommand(final String label,
                              final String[] commands)
    {
        for (String command : commands)
            if (label.equalsIgnoreCase(command))
                return true;
        return false;
    }
    
    public void listCommands(final Player player)
    {
        player.sendMessage("SurvivalParty Commands:");
        player.sendMessage("/party create <Team Name>");
        player.sendMessage("/party invite <Player Name>");
    }
    
}
