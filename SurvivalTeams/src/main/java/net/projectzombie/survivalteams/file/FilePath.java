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
package net.projectzombie.survivalteams.file;

import net.projectzombie.survivalteams.player.TeamPlayer;

/**
 *
 * @author jb
 */
public class FilePath
{
    protected static final String ROOT_      = "teams";
    
    private FilePath() { /* Do nothing */ }

    static public String getTeamPath(final String teamName)
    {
        return ROOT_ + "." + teamName;
    }

    static public String getPlayerPath(final TeamPlayer player, final String teamName)
    {
        return getUUIDMemberPath(teamName) + "." + player.getUUID().toString();
    }

    static protected String getUUIDMemberPath(final String teamName)
    {
        return getTeamPath(teamName) + ".members";
    }

    static public String getLeaderPath(final String teamName)
    {
        return getTeamPath(teamName) + ".leader";
    }
    
}
