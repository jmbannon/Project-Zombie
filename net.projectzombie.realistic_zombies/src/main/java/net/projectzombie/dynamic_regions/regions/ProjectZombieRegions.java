/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.regions;

/**
 *
 * @author jbannon
 */
public enum ProjectZombieRegions
{
    MOUNTAINS("mountains"),
    CITY("city");
    
    
    private final String regionID;
    
    ProjectZombieRegions(final String regionID)
    {
        this.regionID = regionID;
    }
    
    public String getRegionID() { return this.regionID; }
}
