/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.realistic_zombies.spawning;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 *
 * @author jesse
 */
public class SkyscraperSetting extends SpawnSettings {

    private static final int X_MIN = -20, X_MAX = 20, X_BOUND = (X_MAX - X_MIN);
    private static final int Z_MIN = -20, Z_MAX = 20, Z_BOUND = (Z_MAX - Z_MIN);
    private static final int Y_MIN = 4,   Y_MAX = 10, Y_BOUND = (Y_MAX - Y_MIN); 

    public SkyscraperSetting()
    {
        super(new String[] {"test"}, 100);
        
    }

    @Override
    public SpawnOffset[] calculateOffsets(Location playerLocation)
    {
        final Block playerBlock = playerLocation.getBlock();
        final ArrayList<SpawnOffset> offsets = new ArrayList<>();
        
        int x, y, z;
        
        for (int i = 0; i < 100; i++)
        {
            x = RAND.nextInt(X_BOUND) - X_MAX;
            y = RAND.nextInt(Y_BOUND) - Y_MAX;
            z = RAND.nextInt(Z_BOUND) - Z_MAX;
        }
        
    }
    
    public boolean isViable(final Block blockLocation)
    {
        return (!blockLocation.getRelative(BlockFace.DOWN).isEmpty()
                && blockLocation.getRelative(BlockFace.UP).isEmpty());
    }
    
}
