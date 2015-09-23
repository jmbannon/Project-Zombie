/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.controller.file;

import net.projectzombie.survivalteams.player.TeamPlayer;

/**
 *
 * @author jb
 */
public class Paths
{
    
    private Paths() { /* Do nothing */ }

    public static String getTeamPath(final String teamName)
    {
        return TeamFile.BASE_PATH + teamName;
    }

    public static String getLeaderPath(final TeamPlayer player, final String teamName)
    {
        return player.hasTeam() ? getLeaderUUIDpath(teamName) : null;
    }

    public static String getPlayerPath(final TeamPlayer player, final String teamName)
    {
        return player.hasTeam() ? getUUIDMemberPath(teamName) + player.getUUID().toString() : null;
    }

    static String getUUIDMemberPath(final String teamName)
    {
        return getTeamPath(teamName) + ".members";
    }

    static String getLeaderUUIDpath(final String teamName)
    {
        return getTeamPath(teamName) + ".leader";
    }
    
}
