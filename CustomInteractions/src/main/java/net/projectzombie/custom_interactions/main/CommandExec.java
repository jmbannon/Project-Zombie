/*
 * BlockPlacer
 *
 * Version:     0.5
 * MC Build:    1.8.3
 * Date:        06-10-2015
 *
 * Authur:      Jesse Bannon
 * Server:      Project Zombie
 * Website:     www.projectzombie.net
 * 
 * Allows players to place and break particular blocks within a WorldGuard
 * region whos build flag is set to allowed. Stores these blocks within two
 * buffers: blocks and lights. Restores all blocks by iterating through the
 * buffer and setting the blocks to air.  For light blocks, a player must
 * send the command to be able to teleport to each light block location to
 * remove it allowing light to update correctly.
 *
 */

package net.projectzombie.custom_interactions.main;

import net.projectzombie.custom_interactions.custom_items.Engraver;
import net.projectzombie.custom_interactions.listeners.BlockListener;
import net.projectzombie.custom_interactions.listeners.RestoreController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class CommandExec implements CommandExecutor {

    private final BlockListener blockPlace;
    private final RestoreController restoreController;
    
    /**
     * Initializes blockPlace listener methods.
     */
    public CommandExec()
    {
        blockPlace = new BlockListener();
        restoreController = new RestoreController();
    }

    /**
     * Commands the plugin has. Can only execute if the command sender is
     * an actual player and is OP. List of commands in listCommands;
     * 
     * @param cs
     * @param cmd
     * @param label
     * @param args
     * @return 
     */
    @Override
    public boolean onCommand(CommandSender cs,
                             Command cmd,
                             String label,
                             String[] args)
    {
        if (!(cs instanceof Player))
			return true;
		
        final Player sender = (Player)cs;
        
		if (cs.isOp() && label.equalsIgnoreCase("bp"))
        {
            if (args.length == 1 && args[0].equalsIgnoreCase("restore"))
                restoreController.restoreAll(sender);
            else
                this.listAdminCommands(sender);
        }
        else if (label.equalsIgnoreCase("engrave"))
        {
            if (args.length == 1)
                Engraver.engraveItem(sender, args[0]);
            else
                this.listPlayerCommands(sender);
        }
        return true;
    }
    
    /**
     * Lists all available admin commands for block place.
     * 
     * @param player Command list sent to this player. 
     */
    public void listAdminCommands(final Player player)
    {
        player.sendMessage("/bp restore");
    }
    
    /**
     * Lists all available commands for players.
     * 
     * @param player Command list sent to this player. 
     */
    public void listPlayerCommands(final Player player)
    {
        player.sendMessage("/engrave <name>");
        player.sendMessage(" - Renames the current item in your hand. Must have an engraver.");
    }
    
}
