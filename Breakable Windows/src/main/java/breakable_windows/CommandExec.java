package breakable_windows;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandExec extends BlockBreakListener implements CommandExecutor {

	public CommandExec(Plugin plugin) {
		super(plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player playerOne = (Player) sender;
		
		if (!playerOne.isOp())
			return true;
		
		if (cmd.getName().equalsIgnoreCase("bw")) {
			if (args.length == 0)
				playerOne.sendMessage("/bw entries");
			else if (args[0].equalsIgnoreCase("entries"))
				playerOne.sendMessage("Block entries: " + super.getHashMapSize());
			else if (args[0].equalsIgnoreCase("restore")) {
				final int temp = super.getHashMapSize();
				super.restoreBlocks();
				playerOne.sendMessage("Restored " + temp + " blocks!");
			}
			else
				playerOne.sendMessage("/bw entries");		
		}
		
		return true;
	}
}