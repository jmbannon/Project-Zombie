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
    LEADER (0,  "Leader"),
    OFFICER(1,  "Guardian"),
    MEMBER (10, "Member"),
    NULL   (1024, "n/a");
    
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
    
    public boolean higherRank(final TeamRank otherRank)
    {
        return this.rank > otherRank.rank;
    }

}
