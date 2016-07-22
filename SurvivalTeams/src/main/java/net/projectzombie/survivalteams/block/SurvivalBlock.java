package net.projectzombie.survivalteams.block;

/**
 * Created by maxgr on 7/22/2016.
 */
public class SurvivalBlock {
    private int health;

    /**
     * Default block types.
     * @param health = Block's health.
     */
    public SurvivalBlock(final int health)
    {
        this.health = health;
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
    public void setHealth(final int nHealth)
    {
        health = nHealth;

    }

    /**
     * Adds a number to health.
     * @param healing
     */
    public void addToHealth(final int healing)
    {
        health += healing;
    }

    /**
     * Adds a number to health.
     * @param damage
     */
    public void decrementHealth(final int damage)
    {
        health += damage;
    }
}
