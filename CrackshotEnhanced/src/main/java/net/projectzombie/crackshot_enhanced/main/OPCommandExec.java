package net.projectzombie.crackshot_enhanced.main;

import net.projectzombie.crackshot_enhanced.windows.BlockBreakListener;
import net.projectzombie.crackshot_enhanced.custom_weapons.yaml_generator.YAMLGenerator;
import org.bukkit.Bukkit;
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
            Bukkit.broadcastMessage(args[0]);
            if (args[0].equalsIgnoreCase("yaml"))
                YAMLGenerator.generateDefaultWeapons(plugin);
            else
                this.commandList(sender);
        }
        else
            this.commandList(sender);

        return true;
    }
    
    public void commandList(Player sender)
    {
        sender.sendMessage("/bw yaml");
    }
}