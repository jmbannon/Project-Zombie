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
    public static String[] COMMAND    = new String[] { "party", "p"};
    
    public static String[] ARG_INVITE = new String[]  { "invite",  "inv",    "i" };
    public static String[] ARG_KICK   = new String[]  { "kick",    "boot",   "k" };
    public static String[] ARG_CREATE = new String[]  { "create",  "new",    "c" };
    public static String[] ARG_ACCEPT = new String[]  { "accept",            "a" };
    public static String[] ARG_PROMOTE = new String[] { "promote", "raise",  "p" };
    public static String[] ARG_DEMOTE = new String[]  { "demote",  "d" };
    public static String[] ARG_LEAVE  = new String[]  { "leave",   "quit",   "q", "l" };
    public static String[] ARG_DISBAND = new String[] { "disband" };
    public static String[] ARG_ONLINE = new String[]  { "invite",  "online", "o" };
    public static String[] ARG_LIST   = new String[]  { "archive", "list" };
    public static String[] ARG_HELP   = new String[]  { "help",    "commands" };
            
}
