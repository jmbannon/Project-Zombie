package net.projectzombie.crackshot_enhanced.main;

import net.projectzombie.crackshot_enhanced.windows.BlockBreakListener;
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
    
	public CommandExec(final Plugin plugin) throws IOException, Exception
    {
        this.plugin = plugin;
        this.blockBreak = new BlockBreakListener(plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player) || !cs.isOp())
			return true;
		
        final Player sender = (Player)cs;
        
		if (cmd.getName().equalsIgnoreCase("bw") && args.length > 0) {
			if (args[0].equalsIgnoreCase("restore") && args.length == 2) {
                int blockCount = -1;
                if (args[1].equalsIgnoreCase("glass")) 
                {
                    try {
                        blockCount = blockBreak.restoreGlass();
                    } catch (Exception ex) {
                        Logger.getLogger(CommandExec.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sender.sendMessage("Restored " + blockCount + " glass blocks.");
                } else if (args[1].equalsIgnoreCase("lights")) {
                    try {
                        blockCount = blockBreak.restoreLights(sender);
                    } catch (IOException ex) {
                        Logger.getLogger(CommandExec.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sender.sendMessage("Restored " + blockCount + " light blocks.");
                }
			} 
			else
				this.commandList(sender);
		} else
            this.commandList(sender);
		
		return true;
	}
    
    public void commandList(Player sender)
    {
        sender.sendMessage("/bw restore glass");
        sender.sendMessage("/bw restore lights");
    }
}