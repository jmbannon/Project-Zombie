package main;

import breakable_windows.BlockBreakListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandExec implements CommandExecutor {

    final Plugin plugin;
    final BlockBreakListener blockBreak;
    
	public CommandExec(final Plugin plugin) throws IOException
    {
        this.plugin = plugin;
        this.blockBreak = new BlockBreakListener(plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player playerOne = (Player) sender;
		
		if (!playerOne.isOp())
			return true;
		
		if (cmd.getName().equalsIgnoreCase("bw")) {
			if (args[0].equalsIgnoreCase("restore")) {
                int blockCount = -1;
                try {
                    blockCount = blockBreak.restoreBlocks();
                } catch (Exception ex) {
                    Logger.getLogger(CommandExec.class.getName()).log(Level.SEVERE, null, ex);
                }
                playerOne.sendMessage("Restored " + blockCount + " blocks.");
			}
			else
				playerOne.sendMessage("/bw restore");		
		}
		
		return true;
	}
}