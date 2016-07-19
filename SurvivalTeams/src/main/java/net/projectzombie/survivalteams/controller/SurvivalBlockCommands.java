package net.projectzombie.survivalteams.controller;

import net.projectzombie.survivalteams.file.FileWrite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by maxgr on 7/18/2016.
 */
public class SurvivalBlockCommands implements CommandExecutor
{
    public static final String
        ADD_USAGE = "/sb add <Type> <Health>",
        BR_USAGE = "/sb br <radius size>",
        BN_USAGE = "/sb bn <true:false>",
        DELAY_USAGE = "/sb delay <time>",

        SB_PERMS = "SB.all";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length >= 1 && sender.hasPermission(SB_PERMS))
        {
            if (args[0].equals("add"))
            {
                boolean isInt = true;
                int health = -1;
                if (args.length == 3)
                {
                    try { health = Integer.parseInt(args[2]); }
                    catch (NumberFormatException e) { isInt = false; }
                    if (isInt && health != -1)
                    {
                        FileWrite.writeDefaultSBlockHealth(args[1], health);
                    }
                    else
                        sender.sendMessage(ADD_USAGE);
                }
                else
                    sender.sendMessage(ADD_USAGE);
                return true;
            }
            else if (args[0].equals("br"))
            {
                boolean isInt = true;
                int radius = -1;
                if (args.length == 2)
                {
                    try { radius = Integer.parseInt(args[1]); }
                    catch (NumberFormatException e) { isInt = false; }
                    if (isInt && radius != -1)
                    {
                        FileWrite.writeDefaultSBuildRadius(radius);
                    }
                    else
                        sender.sendMessage(BR_USAGE);
                }
                else
                    sender.sendMessage(BR_USAGE);
                return true;
            }
            else if (args[0].equals("bn"))
            {
                boolean bN;
                if (args.length == 2)
                {
                    bN = Boolean.valueOf(args[1]);
                    FileWrite.writeBreakNaturally(bN);
                }
                else
                    sender.sendMessage(BN_USAGE);
                return true;
            }
            else if (args[0].equals("delay"))
            {
                boolean isInt = true;
                int delay = -1;
                if (args.length == 2)
                {
                    try { delay = Integer.parseInt(args[1]); }
                    catch (NumberFormatException e) { isInt = false; }
                    if (isInt && delay != -1)
                    {
                        FileWrite.writeAttackDelay(delay);
                    }
                    else
                        sender.sendMessage(DELAY_USAGE);
                }
                else
                    sender.sendMessage(DELAY_USAGE);
                return true;
            }
        }
        return false;
    }
}
