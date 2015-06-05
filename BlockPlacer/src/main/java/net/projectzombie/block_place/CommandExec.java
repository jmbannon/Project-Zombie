/*
 * CarePackage
 *
 * Version:     0.5
 * MC Build:    1.8.3
 * Date:        05-03-2015
 *
 * Authur:      Jesse Bannon
 * Server:      Project Zombie
 * Website:     www.projectzombie.net
 * 
 * Initiates random care package drops by combining an alternate state of the
 * map with a base state on the actual player map. Stores the base state blocks
 * within a text buffer and pastes the alt state to the location of the base
 * state. Finds single chest within the pasted alt state and sets a randomly
 * define set of items made by the administrator.  Restores the state on a
 * timer.
 *
 */

package net.projectzombie.block_place;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public class CommandExec implements CommandExecutor {

    private final BlockPlaceListener blockPlace;
    private final Plugin plugin;
    
    public CommandExec(final Plugin plugin) {
        this.plugin = plugin;
        blockPlace = new BlockPlaceListener(plugin);
    }

    @Override
    public boolean onCommand(CommandSender cs,
                             Command cmd,
                             String label,
                             String[] args)
    {
        if (!(cs instanceof Player) || !cs.isOp())
			return true;
		
        final Player sender = (Player)cs;
        
		if (cmd.getName().equalsIgnoreCase("bp") && args.length > 0)
        {
            if (args.length == 1 & args[0].equalsIgnoreCase("restore")) {
                int restoredCount = blockPlace.removePlacedBlocks();
                if (restoredCount >= 0)
                    sender.sendMessage("Removed " + restoredCount + " placed blocks.");
                else
                    sender.sendMessage("An error has occured. Please consult an admin.");
            }
        } else
            this.listCommands(sender);
        
        return true;
    }
    
    public void listCommands(final Player player)
    {
        player.sendMessage("/bp restore");
    }
}
