/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.team;

/**
 *
 * @author jb
 */
public enum TeamRank
{
    NULL     (-1, "n/a"),
    FOLLOWER (10, "Follower"),
    OPERATOR (5,  "Operator"),
    LEADER   (0,  "Leader");

    private final int rank;
    private final String title;

    private TeamRank(final int rank,
                      final String title)
    {
        this.rank = rank;
        this.title = title;
    }
    
    public int    getRank()    { return rank; }
    public String getTitle()   { return title; }
    public String toFileName() { return String.valueOf(rank); }
    
    public boolean canInvite()
    {
        return this.equals(OPERATOR) || this.equals(LEADER);
    }
    
    public boolean canBePromoted()
    {
        return this.equals(FOLLOWER);
    }

    static public TeamRank getRank(final int rankValue)
    {
        for (TeamRank rank : TeamRank.values())
            if (rank.getRank() == rankValue)
                return rank;
        return null;
    }
    
    
    
}
