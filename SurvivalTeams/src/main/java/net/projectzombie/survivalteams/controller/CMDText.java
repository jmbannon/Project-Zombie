/*
 * SurvivalTeams
 *
 * Version:     0.5
 * MC Build:    1.8.3
 * Date:        09-22-2015
 *
 * Author:      Jesse Bannon
 * Email:       jbann1994@gmail.com
 * Server:      Project Zombie
 * Website:     www.projectzombie.net
 *
 * Allows players to create rank-based Teams. Includes features such as no
 * team PVP and a group spawn.
 *
*/
package net.projectzombie.survivalteams.controller;

/**
 *
 * @author jb
 */
public class CMDText
{
    public static String[]
    COMMAND = new String[] { "party", "p"},
    
    ARG_INVITE = new String[]  { "invite",  "inv",    "i" },
    ARG_KICK   = new String[]  { "kick",    "boot",   "k" },
    ARG_CREATE = new String[]  { "create",  "new",    "c" },
    ARG_ACCEPT = new String[]  { "accept",            "a" },
    ARG_PROMOTE = new String[] { "promote", "raise",  "p" },
    ARG_DEMOTE = new String[]  { "demote",  "d" },
    ARG_LEAVE  = new String[]  { "leave",   "quit",   "q", "l" },
    ARG_DISBAND = new String[] { "disband" },
    ARG_ONLINE = new String[]  { "online", "o" },
    ARG_INFO   = new String[]  { "info" },
    ARG_SETBASE = new String[] { "setbase", "set" },
    ARG_BASE    = new String[] { "base", "spawn" },
    ARG_LIST   = new String[]  { "list" },
    ARG_HELP   = new String[]  { "help",    "commands" },
            
    ARG_REMOVE_SPAWNS   = new String[]  { "removeAllSpawns" };

    public static final String
            ADD_USAGE = "/sb add <Type> <Health>",
            BR_USAGE = "/sb br <radius size>",
            BN_USAGE = "/sb bn <true:false>",
            DELAY_USAGE = "/sb delay <time>",
            TOOL_USAGE = "/sb tool <Material>",

            ADD_FINALIZE = "Default type added.",
            BR_FINALIZE = "Build radius set to ",
            BN_FINALIZE = "Break naturally set to ",
            DELAY_FINALIZE = "Delay set to ",
            TOOL_FINALIZE = "SB tool set to ",

    SB_PERMS = "SB.all";
            
}
