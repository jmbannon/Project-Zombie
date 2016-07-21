package net.projectzombie.survivalteams.controller;

import net.projectzombie.survivalteams.file.FileWrite;
import static net.projectzombie.survivalteams.controller.CMDText.*;

import net.projectzombie.survivalteams.file.WorldCoordinate;
import net.projectzombie.survivalteams.file.buffers.SBWeaponBuffer;
import net.projectzombie.survivalteams.file.buffers.SBlockBuffer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Conveyance commands. Although a couple have to be set up to make server work properly.
 * Help:
 *  add--------- allows adding of default block types.
 *  br --------- Build radius, how far players can build from spawn flag. (default 0)
 *  bn --------- Break naturally, if drops occur. (default false)
 *  delay ------ How often zombies damage blocks when chasing a human. (default 10)
 *  tool ------- Sets the tool for checking block stats. (no default)
 *  damage ----- Default damage. (default 0)
 *  weapon ----- Allows specification of a blocks damage from the item and the durability
 *                  decrease of a hit.
 *  durability - Default item durability decrease on hit. (default 0)
 *  rdsb ------- Removes a default SB from disc.
 *  rsb -------- Removes a SB from disc.
 *  rw --------- Removes a S weapon.
 *  rall ------- Removes all SBs.
 *
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
            else if (args[0].equals("damage"))
            {
                boolean isInt = true;
                int damage = -1;
                if (args.length == 2)
                {
                    try { damage = Integer.parseInt(args[1]); }
                    catch (NumberFormatException e) { isInt = false; }
                    if (isInt && damage != -1)
                    {
                        FileWrite.writeSBDefaultDamage(damage);
                        sender.sendMessage(DAMAGE_FINALIZE + damage);
                    }
                    else
                        sender.sendMessage(DAMAGE_USAGE);
                }
                else
                    sender.sendMessage(DAMAGE_USAGE);
                return true;
            }
            else if (args[0].equals("weapon"))
            {
                boolean isInt = true;
                int damage = 0;
                int durability = 0;
                if (args.length == 4)
                {
                    try
                    {
                        damage = Integer.parseInt(args[2]);
                        durability = Integer.parseInt(args[3]);
                    }
                    catch (NumberFormatException e) { isInt = false; }
                    Material material = Material.valueOf(args[1]);
                    if (isInt)
                    {
                        FileWrite.writeSBWeapon(material, damage, durability);
                        sender.sendMessage(WEAPON_FINALIZE);
                    }
                    else
                        sender.sendMessage(WEAPON_USAGE);
                }
                else
                    sender.sendMessage(WEAPON_USAGE);
                return true;
            }
            else if (args[0].equals("reload"))
            {
                SBlockBuffer.readInDefaults();
                SBlockBuffer.readInPlacedSBlocks();
                SBWeaponBuffer.readInDefaults();
                sender.sendMessage(RELOAD_FINALIZE);
                return true;
            }
            else if (args[0].equals("durability"))
            {
                boolean isInt = true;
                int durability = -1;
                if (args.length == 2)
                {
                    try { durability = Integer.parseInt(args[1]); }
                    catch (NumberFormatException e) { isInt = false; }
                    if (isInt && durability != -1)
                    {
                        FileWrite.writeSBDefaultDurability(durability);
                        sender.sendMessage(DURABILITY_FINALIZE + durability);
                    }
                    else
                        sender.sendMessage(DURABILITY_USAGE);
                }
                else
                    sender.sendMessage(DURABILITY_USAGE);
                return true;
            }
            else if (args[0].equals("rdsb"))
            {
                if (args.length == 2)
                {
                    Material material = Material.valueOf(args[1]);
                    FileWrite.wipeDefaultSBlock(material);
                    SBlockBuffer.removeDefault(material);
                    sender.sendMessage(RDSB_FINALIZE);
                }
                else
                    sender.sendMessage(RDSB_USAGE);
                return true;
            }
            else if (args[0].equals("rsb"))
            {
                if (args.length == 2)
                {
                    FileWrite.wipeSBlock(args[1]);
                    SBlockBuffer.removeSB(WorldCoordinate.toLocation(args[1]));
                    SBlockBuffer.remove(WorldCoordinate.toLocation(args[1]));
                    sender.sendMessage(RSB_FINALIZE);
                }
                else
                    sender.sendMessage(RSB_USAGE);
                return true;
            }
            else if (args[0].equals("rw"))
            {
                if (args.length == 2)
                {
                    Material material = Material.valueOf(args[1]);
                    FileWrite.wipeSBWeapon(material);
                    SBWeaponBuffer.remove(material);
                    sender.sendMessage(RW_FINALIZE);
                }
                else
                    sender.sendMessage(RW_USAGE);
                return true;
            }
            else if (args[0].equals("rall"))
            {
                FileWrite.wipeSBlocks();
                SBlockBuffer.clearBuffer();
                sender.sendMessage(RALL_FINALIZE);
                return true;
            }
            else if (args[0].equals("help"))
            {
                String[] helps = {"add--------- allows adding of default block types.",
                                "br --------- Build radius, how far players can build from spawn flag. (default 0)",
                                "bn --------- Break naturally, if drops occur. (default false)",
                                "delay ------ How often zombies damage blocks when chasing a human. (default 10)",
                                "tool ------- Sets the tool for checking block stats. (no default)",
                                "damage ----- Default damage. (default 0)",
                                "weapon ----- Allows specification of a blocks damage from the item and the ",
                                "   durability decrease of a hit.",
                                "durability - Default item durability decrease on hit. (default 0)",
                                "rdsb ------- Removes a default SB from disc.",
                                "rsb -------- Removes a SB from disc.",
                                "rw --------- Removes a S weapon.",
                                "rall ------- Removes all SBs."};
                help(sender, helps);
                return true;
            }
        }
        return false;
    }

    public void help(CommandSender sender, String[] helps)
    {
        for (String help : helps)
        {
            sender.sendMessage(help);
        }
    }
}
