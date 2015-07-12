package net.projectzombie.crackshot_enhanced.main;

import net.projectzombie.crackshot_enhanced.windows.BlockBreakListener;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class OPCommandExec implements CommandExecutor
{
    final Plugin plugin;
    final BlockBreakListener blockBreak;
    
	public OPCommandExec(final Plugin plugin)
    {
        this.plugin = plugin;
        this.blockBreak = new BlockBreakListener();
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
        
		if (cmd.getName().equalsIgnoreCase("bw") && args.length > 0)
        {
            if (args[0].equalsIgnoreCase("lore"))
                this.loreCommands(sender, args);
            else
                this.commandList(sender);
		}
        else
            this.commandList(sender);
		
		return true;
	}
    
    public void commandList(Player sender)
    {
        sender.sendMessage("/bw lore");
    }
    
    public void loreCommands(final Player sender,
                             final String[] args)
    {
        final String toEncode = CrackshotLore.getEncryptedPreInfoString(sender.getInventory().getItemInHand());
        if (toEncode != null)
        {
            plugin.getLogger().info("Copy this into the gun lore after <line>.");
            plugin.getLogger().info("DO NOT PRESS Ctrl+C whatever you do!!");
            plugin.getLogger().info(toEncode);
            sender.sendMessage("Encrypted string is in console.");
        }
        else
            sender.sendMessage("Not valid lore. `PZ`ID");
    }
}