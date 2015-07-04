package net.projectzombie.crackshot_enhanced.main;

import net.projectzombie.crackshot_enhanced.windows.BlockBreakListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class OPCommandExec implements CommandExecutor {

    final Plugin plugin;
    final BlockBreakListener blockBreak;
    
	public OPCommandExec(final Plugin plugin) throws IOException, Exception
    {
        this.plugin = plugin;
        this.blockBreak = new BlockBreakListener(plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player) || !cs.isOp())
			return true;
		
        final Player sender = (Player)cs;
        
		if (cmd.getName().equalsIgnoreCase("bw") && args.length > 0)
        {
			if (args[0].equalsIgnoreCase("restore") && args.length == 2)
                this.restoreCommands(sender, args);
            else if (args[0].equalsIgnoreCase("lore"))
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
        sender.sendMessage("/bw restore glass");
        sender.sendMessage("/bw restore lights");
        sender.sendMessage("/bw lore");
    }
    
    public void restoreCommands(final Player sender,
                                final String[] args)
    {
        int blockCount = -1;
        if (args[1].equalsIgnoreCase("glass")) 
        {
            try
            {
                blockCount = blockBreak.restoreGlass();
                sender.sendMessage("Restored " + blockCount + " glass blocks.");
            }
            catch (Exception ex)
            {
                Logger.getLogger(OPCommandExec.class.getName()).log(Level.SEVERE, null, ex);
                sender.sendMessage("An IO error occured. Please consult an admin.");
            }
        }
        else if (args[1].equalsIgnoreCase("lights"))
        {
            try
            {
                blockCount = blockBreak.restoreLights(sender);
                sender.sendMessage("Restored " + blockCount + " light blocks.");
            }
            catch (IOException ex)
            {
                Logger.getLogger(OPCommandExec.class.getName()).log(Level.SEVERE, null, ex);
                sender.sendMessage("An IO error occured. Please consult an admin.");
            }
        }
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