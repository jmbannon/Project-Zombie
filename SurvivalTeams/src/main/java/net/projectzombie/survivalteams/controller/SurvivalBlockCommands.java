package net.projectzombie.survivalteams.controller;

import net.projectzombie.survivalteams.file.FileWrite;
import static net.projectzombie.survivalteams.controller.CMDText.*;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by maxgr on 7/18/2016.
 */
public class SurvivalBlockCommands implements CommandExecutor
{

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
                        sender.sendMessage(ADD_FINALIZE);
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
                        sender.sendMessage(BR_FINALIZE + radius);
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
                    sender.sendMessage(BN_FINALIZE + bN);
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
                        sender.sendMessage(DELAY_FINALIZE + delay);
                    }
                    else
                        sender.sendMessage(DELAY_USAGE);
                }
                else
                    sender.sendMessage(DELAY_USAGE);
                return true;
            }
            else if (args[0].equals("tool"))
            {
                if (args.length == 2)
                {
                    Material material = Material.valueOf(args[1]);
                    FileWrite.writeSBTool(material);
                    sender.sendMessage(TOOL_FINALIZE + material);
                }
                else
                    sender.sendMessage(TOOL_USAGE);
                return true;
            }
        }
        return false;
    }
}
