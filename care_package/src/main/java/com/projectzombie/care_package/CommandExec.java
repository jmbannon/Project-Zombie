/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import com.projectzombie.care_package.StateController.StateType;
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
    private final Plugin plugin;
    private final StateController controller;
    private final PackageHandler chest;
    
    public CommandExec(Plugin plugin) {
        this.plugin = plugin;
        controller = new StateController(plugin);
        chest = new PackageHandler(plugin);
    }
    
    /**
     * TODO
     * -list chests
     * -remove chests
     * -get chest config
     * -tp to base/alt
     * -initiate drop
     * -clean player interface
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
            String[] args) {
        final Player player = (Player) cs;

        if (!player.isOp()) // Change later!!!
        {
            return false;
        }

        if (args.length == 0) {
            this.listCommands(player);
            
        } else if (args[0].equalsIgnoreCase("list") && args.length == 2) {
            if (args[1].equalsIgnoreCase("alt"))
                controller.listStates(player, StateType.ALT);
            else if (args[1].equalsIgnoreCase("base"))
                controller.listStates(player, StateType.BASE);
            else
                this.listCommands(player);
            
            
        } else if (args[0].equalsIgnoreCase("create") && args.length == 3) {
            if (args[1].equalsIgnoreCase("alt"))
                controller.createState(player, args[2], StateType.ALT);
            else if (args[1].equalsIgnoreCase("base"))
                controller.createState(player, args[2], StateType.BASE);
            else if (args[1].equalsIgnoreCase("chest"))
                chest.createPackage(player, args[2]);
            else
                this.listCommands(player);
  
        } else if (args[0].equalsIgnoreCase("remove") && args.length == 3) {
            if (args[1].equalsIgnoreCase("base"))
                controller.removeState(player, args[2], StateController.StateType.BASE);
            else if (args[1].equalsIgnoreCase("alt"))
                controller.removeState(player, args[2], StateController.StateType.ALT);
            else
                this.listCommands(player);
                
        }  else if (args[0].equalsIgnoreCase("link") && args.length >= 3) {
            StringBuilder temp = new StringBuilder("");
            for (int i = 3; i < args.length; i++) {
                temp.append(args[i]);
                temp.append(' ');
            }
            
            controller.linkState(player, args[1], args[2], temp.toString());
            
        } else if (args[0].equalsIgnoreCase("basetoalt") && args.length == 3) {
            controller.setAltState(args[1], args[2]);
            player.sendMessage("attemping to swap states" + args[1] + " and " + args[2]);
            
        } else if (args[0].equalsIgnoreCase("chest") && args.length == 3) {
            if (args[1].equalsIgnoreCase("create"))
                chest.createPackage(player, args[2]); 
            else if (args[1].equalsIgnoreCase("remove"))
                chest.removePackage(player, args[2]);
            else
                this.listCommands(player);
        } else if (args[0].equalsIgnoreCase("restore")) {
            controller.restoreState();
            
        } else {
            this.listCommands(player);
        }
        return true;
    }
    
        /**
     * Lists all commands to the sender.
     * @param sender Command sender.
     */
    private void listCommands(final Player sender) 
    {
        sender.sendMessage("/cp create <base/alt/chest> <name>");
        sender.sendMessage("/cp link <base> <alt> <desc>");
        sender.sendMessage("/cp remove <base/alt> <name>");
        sender.sendMessage("/cp list <base/alt>");
        sender.sendMessage("/cp restore");
        sender.sendMessage("/cp baseToAlt <base name> <alt name>");
    }
    
    public void onEnable() {
        controller.onEnable();
        chest.onEnable();
    }
    
    public void onDisable() {
        controller.onDisable();
        controller.onDisable();
    }
}
