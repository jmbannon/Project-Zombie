/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.main;

import net.projectzombie.crackshot_enhanced.custom_weapons.GunSmithController;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Mod.ModType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class GunSmithCommandExec implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) 
    {
        if (!(cs instanceof Player))
			return true;
		
        final Player sender = (Player)cs;
        int price;
        
		if (cmd.getName().equalsIgnoreCase("gunsmith") || cmd.getName().equalsIgnoreCase("gs"))
        {
            if (args.length >= 2)
            {
                if (args[0].equalsIgnoreCase("repair"))
                {
                    if (args[1].equalsIgnoreCase("weapon"))
                       GunSmithController.repairWeapon(sender);
                    else if (args[1].equalsIgnoreCase("price"))
                    {
                        price = GunSmithController.repairPrice(sender);
                        if (price < 0)
                            sender.sendMessage("Cannot repair an item that is not a gun!");
                        else if (price == 0)
                            sender.sendMessage("Gun is fully repaired already!");
                        else
                            sender.sendMessage("Gun repair would cost $" + price + ".");
                    }
                    else
                        listCommands(sender);
                }
                else if (args[0].equalsIgnoreCase("upgrade"))
                {
                    if (args[1].equalsIgnoreCase("weapon"))
                       GunSmithController.upgradeBuildWeapon(sender);
                    else if (args[1].equalsIgnoreCase("price"))
                    {
                        price = GunSmithController.upgradeBuildPrice(sender);
                        if (price < 0)
                            sender.sendMessage("Cannot upgrade an item that is not a gun!");
                        else if (price == 0)
                            sender.sendMessage("Gun is fully upgraded already!");
                        else
                            sender.sendMessage("Gun upgrade would cost $" + price + ".");
                    }
                    else
                        listCommands(sender);
                }
                else if (args[0].equalsIgnoreCase("mod"))
                {
                    if (args[1].equalsIgnoreCase("buy") && args.length >= 3)
                    {
                        for (ModType mod : ModType.values())
                            if (mod.toString().toLowerCase().contains(args[2].toLowerCase()))
                            {
                                GunSmithController.addModification(sender, mod);
                                return true;
                            }
                        GunSmithController.listModifications(sender);
                    }
                    else if (args[1].equalsIgnoreCase("list"))
                        GunSmithController.listModifications(sender);
                    else
                        listCommands(sender);
                }
                else
                    listCommands(sender);
            }
            else
                listCommands(sender);
        }
        return true;
    }
    
    public void listCommands(final Player sender)
    {
        sender.sendMessage("/gunsmith repair weapon");
        sender.sendMessage("/gunsmith repair price");
        sender.sendMessage("/gunsmith upgrade weapon");
        sender.sendMessage("/gunsmith upgrade price");
        sender.sendMessage("/gunsmith mod list");
        sender.sendMessage("/gunsmith mod buy <modification>");
    }
    
}
