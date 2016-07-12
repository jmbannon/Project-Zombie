/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.physical.weps;

import cs.guns.qualities.Build;
import cs.guns.utilities.HiddenLoreInfo;
import cs.guns.weps.GunID;
import cs.guns.weps.Guns;
import cs.guns.weps.Guns.CrackshotGun;

/**
 *
 * @author Jesse Bannon
 * 
 * This class hides critical information within the Lore of an 
 * CrackshotGun ItemStack.
 * 
 * It converts text to hex which then each hex char
 * is prepended by a COLOR_CHAR which tricks Minecraft into
 * thinking it's a color, therefore no actual text is displayed.
 * 
 * A decrypted HiddenGunInfo is in the form of
 *      PZ`gunID`durability`BuildType
 * 
 * where PZ is a HiddenLore verifier.
 */
public class HiddenGunInfo extends HiddenLoreInfo
{ 
    /** Index of the Gun ID. */
    private static final int GUN_ID_IDX = 1;
    
    /** Index of the current durability. */
    private static final int DUR_IDX = 2;
    
    /** Index of the current build. */
    private static final int BUILD_IDX = 3;
    
    /** Total length of the elements within the HiddenInfo. */
    private static final int INFO_LEN = 4;
    
    /** Magic number that is set to a pre-shot gun's durability. Metal af \m/ */
    private static final int PRESHOT_VER_DUR = -666;
    
    /** Build that is set to a pre-shot gun's build. */
    private static final Build PRESHOT_VER_BUILD = Build.PRESHOT;
    
    /**
     * Creates HiddenGunInfo from an existing hidden string.
     * @param hiddenLoreInfo Existing hidden string that contains gun info.
     */
    public HiddenGunInfo(final String hiddenLoreInfo)
    {
        super(hiddenLoreInfo);
    }

    /**
     * Creates HiddenGunInfo from a GunID. Gun is assumed to be pre-shot.
     * @param id GunID of the gun.
     */
    public HiddenGunInfo(final GunID id)
    {
        super(new String[] {
            String.valueOf(id.toString()),
            String.valueOf(PRESHOT_VER_DUR),
            PRESHOT_VER_BUILD.name()
        });
    }
    
    /**
     * @return The GunID hidden in the lore as a String.
     */
    public String getGunIDStr()
    {
        if (super.isValid())
            return super.getInfoStr(GUN_ID_IDX);
        else
            return null;
    }
    
    /**
     * @return The GunID hidden in the lore.
     */
    public GunID getGunID()
    {
        return new GunID(getGunIDStr());
    }
    
    /**
     * Gets the CrackshotGun by parsing the GunID in the hidden lore.
     * @return CrackshotGun affiliated with the GunID.
     */
    public CrackshotGun getGun()
    {
        return Guns.get(super.getInfoStr(GUN_ID_IDX));
    }
    
    /**
     * @return Current durability.
     */
    public int getDurability()
    {
        return super.getInfoInt(DUR_IDX);
    }
    
    /**
     * @return Returns true if the gun is post-shot and the durability is >= 0.
     *         False otherwise.
     */
    public boolean isBroken()
    {
        return isPostShot() && getDurability() >= 0;
    }
    
    /**
     * @return Current build of the gun.
     */
    public Build getBuild() 
    {
        try
        {
            return Build.valueOf(super.getInfoStr(BUILD_IDX));
        } 
        catch (IllegalArgumentException ex)
        {
            return null;
        }
    }
    
    /**
     * Sets the GunID with the String equivalent.
     * @param newID GunID String to set.
     */
    public void setGunID(final String newID)
    {
        super.setInfoStr(GUN_ID_IDX, newID);
    }
    
    /**
     * Sets the GunID with the new GunID.
     * @param newID GunID to set.
     */
    public void setGunID(final GunID newID)
    {
        super.setInfoStr(GUN_ID_IDX, newID.toString());
    }
    
    /**
     * Sets the durability with the new durability.
     * @param newDurability New durability to set.
     */
    public void setDurability(final int newDurability)
    {
        super.setInfoInt(DUR_IDX, newDurability);
    }
    
    /**
     * Decrements the current durability by one and returns the new durability.
     * @return Current durability minus one.
     */
    public int decrementDurability()
    {
        final int decDur = getDurability() - 1;
        super.setInfoInt(DUR_IDX, decDur);
        return decDur;
    }
    
    /**
     * Sets the Build.
     * @param build New Build to set.
     */
    public void setBuild(final Build build)
    {
        super.setInfoStr(BUILD_IDX, build.name());
    }
    
    /**
     * @return Returns true if hidden gun info is pre-shot by checking magic values
     * set for both build and durability. False otherwise.
     */
    public boolean isPreShot()
    {
        return this.isValid()
            && this.getDurability() == PRESHOT_VER_DUR
            && this.getBuild().equals(PRESHOT_VER_BUILD);
    }
    
    /**
     * @return Returns true if the HiddenGunInfo is valid and it's not pre-shot.
     */
    public boolean isPostShot()
    {
        return this.isValid() && !this.isPreShot();
    }
    
    /**
     * @return Returns the string equivalent of this particular GunID's 
     * HiddenGunInfo.
     */
    public String getPreShotHiddenInfo()
    {
        return new HiddenGunInfo(this.getGunID()).getHiddenInfo();
    }
    
    /**
     * @return Returns true if the HiddenGunInfo's length is correct and
     * the GunID is valid. Returns false otherwise.
     */
    @Override
    public boolean isValid()
    {
        return super.isValid()
            && super.getLength() == INFO_LEN
            && GunID.isValidID(getGunIDStr());
    }
}
