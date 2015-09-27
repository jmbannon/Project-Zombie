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
package net.projectzombie.survivalteams.controller.file;

import net.projectzombie.survivalteams.player.TeamPlayer;

/**
 *
 * @author jb
 */
public class FilePath
{
    
    private FilePath() { /* Do nothing */ }

    static public String getTeamPath(final String teamName)
    {
        return TeamFile.ROOT_PATH + teamName;
    }

    static public String getLeaderPath(final TeamPlayer player, final String teamName)
    {
        return getLeaderUUIDpath(teamName) + "." + player.getUUID().toString();
    }

    static public String getPlayerPath(final TeamPlayer player, final String teamName)
    {
        return getUUIDMemberPath(teamName) + "." + player.getUUID().toString();
    }

    static protected String getUUIDMemberPath(final String teamName)
    {
        return getTeamPath(teamName) + ".members";
    }

    static protected String getLeaderUUIDpath(final String teamName)
    {
        return getTeamPath(teamName) + ".leader";
    }
    
}
