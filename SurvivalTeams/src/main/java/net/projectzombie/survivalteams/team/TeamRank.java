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
    OFFICER  (5,  "Officer"),
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
    
    public boolean isLeader()
    {
        return this.equals(LEADER);
    }
    
    public boolean canInvite()
    {
        return this.equals(OFFICER) || this.equals(LEADER);
    }
    
    public boolean canKick()
    {
        return this.equals(OFFICER) || this.equals(LEADER);
    }
    
    public boolean canSetSpawn()
    {
        return this.equals(OFFICER) || this.equals(LEADER);
    }
    
    public boolean canPromote()
    {
        return this.equals(LEADER);
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
