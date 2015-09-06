/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.regions.modules;

import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class FlatSpawns extends RegionModule 
{
    
    public FlatSpawns(final String regionName)
    {
        super(regionName, 120);
    }

    @Override
    public boolean executeModule(final Player player)
    {
        return true;
    }
    

    

    
    
}
