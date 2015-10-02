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

package net.projectzombie.block_buffer.main;

import net.projectzombie.block_buffer.controller.RestoreController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class CommandExec implements CommandExecutor
{
    public CommandExec() { /* Do nothing */ }

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
        
        if (cs.isOp() && label.equalsIgnoreCase("bb"))
        {
            if (args.length == 1 && args[0].equalsIgnoreCase("restore") && !RestoreController.isRestoring())
                RestoreController.restoreAll(sender);
            else
                this.listCommands(sender);
        }
        else
            this.listCommands(sender);
        
        return true;
    }
    
    /**
     * Lists all available admin commands for block place.
     * 
     * @param player Command list sent to this player. 
     */
    public void listCommands(final Player player)
    {
        player.sendMessage("/bb restore");
    }
}
