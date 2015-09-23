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
        if (!(cs instanceof Player))
			return true;
		
        final Player sender = (Player)cs;
        
        if (isCommand(label, COMMAND))
        {
            
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
        
    }
    
}
