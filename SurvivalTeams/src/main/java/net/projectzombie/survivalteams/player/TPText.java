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
package net.projectzombie.survivalteams.player;

import net.projectzombie.survivalteams.team.Team;
import net.projectzombie.survivalteams.team.TeamRank;

/**
 *
 * @author jb
 */
public class TPText
{
    
    static protected String
    
    TEAM_CREATE_NAME_TAKEN  = "A team with this name already exists.",
            
    RANK_NO_OPERATION       = "You are not a high enough rank to perform this action.",
    INVITE_INVALID_EXPIRED  = "This invite has expired; ask for a resend.",
    INVITE_INVALID_HAS_TEAM = "You cannot invite a player who is already on a team.",
    INVITE_INVALID_ON_TEAM  = "You cannot accept a team invite when you are on a team.",
            
    LEADER_CANT_QUIT        = "As the leader you must disband your team to quit it.",
    NOT_LEADER              = "You must be a leader to perform this action.",
    CANNOT_PROMOTE          = "You cannot promote this player.",
    NO_TEAM                 = "You are not on a team.",
    NO_SPAWN                = "Your team does not have a spawn point set.",
    ON_TEAM                 = "You cannot do this while on a team.",
    NOT_ON_TEAM             = "This player is not on your team.",
    FILE_ERROR              = "A file error has occured with this operation. Please consult an admin",
    NEW_SPAWN               = "A new team spawn has been set.",
    PLAYER_NOT_FOUND        = "Could find a player with that name.",
    RANK_NOT_FOUND          = "That rank does not exist.",
    TEAM_NOT_ONLINE         = "None of your team is online.";
    
    private TPText() { /* Return nothing. */ }
    
    static public String kickedPlayer(final TeamPlayer player)
    {
        return "You have kicked " + player.getPlayerName() + ".";
    }
    
    static public String kickedFromTeam(final Team team)
    {
        return "You have been kicked from " + team.getName() + ".";
    }
    
    static public String recieveTeamInvite(final TeamPlayer sender)
    {
        return sender.getPlayerName() + " has invited you to " + sender.getTeam().getName() + ".";
    }
    
    static public String recievePromototion(final TeamPlayer sender,
                                            final TeamRank newRank)
    {
        return sender.getPlayerName() + " has promoted you to " + newRank.getTitle() + ".";
    }
    
    static public String acceptTeamInvite(final Team team,
                                          final TeamRank newRank)
    {
        return "You have joined " + team.getName() + " starting as a " + newRank.getTitle() + ".";
    }
    
    static public String createdNewTeam(final Team team)
    {
        return "You have created " + team.getName() + ".";
    }
    
    static public String disbandedTeam(final Team team)
    {
        return team.getName() + " has been disbanded.";
    }
    
    static public String hasQuitTeam(final TeamPlayer quitter)
    {
        return quitter.getPlayerName() + " has quit your team.";
    }
    
    static public String quitTeam(final Team team)
    {
        return "You have quit " + team.getName() + ".";
    }
    
    static public String promoted(final TeamPlayer reciever,
                                  final TeamRank rank)
    {
        return "You have promoted " + reciever.getPlayerName() + " to " + rank.getTitle() + ".";
    }
}
