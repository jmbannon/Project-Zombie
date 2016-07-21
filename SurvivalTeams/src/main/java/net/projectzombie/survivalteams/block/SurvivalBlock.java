package net.projectzombie.survivalteams.block;

import net.projectzombie.survivalteams.file.FileWrite;
import net.projectzombie.survivalteams.file.WorldCoordinate;
import net.projectzombie.survivalteams.file.buffers.SBWeaponBuffer;
import net.projectzombie.survivalteams.file.buffers.SBlockBuffer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
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
     * @param health = Block's health.
     */
    public SurvivalBlock(final int health)
    {
        this.health = health;
    }

    /**
     * Main constructor, meant for all blocks that are not default.
     * @param health = Block's health.
     * @param teamName = Team that placed the block.
     * @param loc = Location of block.
     */
    public SurvivalBlock(final int health, final String teamName, final Location loc)
    {
        this.health = health;
        this.teamName = teamName;
        this.loc = loc;
    }

    /**
     * @return = Block's health. May not match disc value, until restart.
     */
    public int getHealth()
    {
        return health;
    }

    /**
     * @param nHealth = The new value of the block's health.
     */
    public void setHealth(int nHealth)
    {
        health = nHealth;

    }

    /**
     * @param loc = New value of the block's location.
     */
    public void setLocation(Location loc)
    {
        this.loc = loc;
    }

    /**
     * @param teamName = New team the block belongs to. Only should be used when migrating
     *                      from default.
     */
    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }

    /**
     * @return = Name of team block belongs to.
     */
    public String getTeamName()
    {
        return teamName;
    }

    /**
     * @return = Location of block.
     */
    public Location getLocation()
    {
        return loc;
    }

    /**
     * Given the item in hand the block will take damage, item will also take damage,
     *  if damageable.
     * @param pI = Player's inventory that is doing the attack.
     */
    public void takeDamage(PlayerInventory pI) {
        ItemStack item = pI.getItemInHand();
        SurvivalWeapon weapon = SBWeaponBuffer.getWeapon(item.getType());
        if (item.getType().getMaxDurability() != 0)
        {
            if (weapon != null)
                weapon.itemHit(item);
            else
            {
                short itemHealth = (short) (item.getDurability() +
                                    SBWeaponBuffer.getDefaultDurability());
                item.setDurability(itemHealth);
            }
            if (item.getDurability() >= item.getType().getMaxDurability())
                pI.remove(pI.getItemInHand());
        }
        takeDamage(item);
    }

    /**
     * Zombie specific damage event.
     * @param eE = Zombie's inventory.
     */
    public void takeDamage(EntityEquipment eE) {
        // Can be changed to specify zombie's as a whole damage.
        ItemStack weapon = eE.getItemInHand();
        weapon.setDurability(weapon.getDurability());
        takeDamage(weapon);
    }

    /**
     * Unifying damage event, given item gives block damage.
     * @param weapon = Damage specifier.
     */
    private void takeDamage(ItemStack weapon)
    {
        if (SBWeaponBuffer.getWeapons().contains(weapon.getType()))
        {
            int damage = SBWeaponBuffer.getWeaponDamage(weapon.getType()).getHitPoints();
            health += damage;
        }
        else
        {
            health += SBWeaponBuffer.getDefaultDamage();
        }

        onDeath();
    }

    /**
     * @return = True if dead.
     */
    public boolean isDead()
    {
        return health <= 0;
    }

    public String getID()
    {
        return WorldCoordinate.toStringLocID(teamName, loc.getBlock());
    }

    /**
     * Checks if dead then kills the block, if dead.
     */
    public void onDeath()
    {
        if (isDead())
        {
            kill();
        }
    }

    /**
     * Kills the block.
     */
    public void kill()
    {
        FileWrite.wipeSBlock(getID());

        if (SBlockBuffer.isBreakNaturally())
            loc.getBlock().breakNaturally();
        else
            loc.getBlock().setType(Material.AIR);

        SBlockBuffer.remove(loc);
    }

    /**
     * Saves the block.
     * @return = True if save was successful.
     */
    public boolean saveSBlock() {
        return FileWrite.writeSBlockHealth(teamName, loc, health);
    }

    /**
     * Creates a SB and adds it to buffer.
     * @param block
     * @param teamNameN
     * @return = If write of new block is successful.
     */
    public static boolean createSBlock(Block block, String teamNameN) {
        SurvivalBlock sBT = SBlockBuffer.getDefault(block.getType());
        SurvivalBlock sB = new SurvivalBlock(sBT.getHealth(), teamNameN, block.getLocation());

        SBlockBuffer.add(sB, block.getLocation());
        return FileWrite.writeSBlockHealth(sB);
    }
}
