package net.projectzombie.survivalteams.block;

import net.projectzombie.survivalteams.file.FileWrite;
import net.projectzombie.survivalteams.file.WorldCoordinate;
import net.projectzombie.survivalteams.file.buffers.SBlockBuffer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.PlayerInventory;

/**
 * Created by maxgr on 7/17/2016.
 */
public class SurvivalBlock {
    private int health;
    private String teamName;
    private Location loc;

    /**
     * This is meant for default types only.
     * @param health
     */
    public SurvivalBlock(final int health)
    {
        this.health = health;
    }

    public SurvivalBlock(final int health, final String teamName, final Location loc)
    {
        this.health = health;
        this.teamName = teamName;
        this.loc = loc;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int nHealth)
    {
        health = nHealth;

    }

    public void setLocation(Location loc)
    {
        this.loc = loc;
    }

    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }

    public String getTeamName()
    {
        return teamName;
    }

    public Location getLocation()
    {
        return loc;
    }

    public void takeDamage(PlayerInventory pI) {
        health -= 10;
        onDeath();
    }

    public void takeDamage(EntityEquipment eE) {
        health -= 10;
        onDeath();
        // TODO unify create damage and player damage
    }

    public boolean isDead()
    {
        return health <= 0;
    }

    public String getID()
    {
        return WorldCoordinate.toStringLocID(teamName, loc.getBlock());
    }

    public boolean onDeath()
    {
        if (isDead())
        {
            kill();
        }
        return false;
    }

    public void kill()
    {
        FileWrite.writeSBlock(getID(), null);

        if (SBlockBuffer.isBreakNaturally())
            loc.getBlock().breakNaturally();
        else
            loc.getBlock().setType(Material.AIR);

        SBlockBuffer.remove(loc);
    }

    public boolean saveSBlock() {
        return FileWrite.writeSBlockHealth(teamName, loc, health);
    }

    public static boolean createSBlock(Block block, String teamNameN) {
        SurvivalBlock sBT = SBlockBuffer.getDefault(block.getType());
        SurvivalBlock sB = new SurvivalBlock(sBT.getHealth(), teamNameN, block.getLocation());

        SBlockBuffer.add(sB, block.getLocation());
        return FileWrite.writeSBlockHealth(sB);
    }
}
