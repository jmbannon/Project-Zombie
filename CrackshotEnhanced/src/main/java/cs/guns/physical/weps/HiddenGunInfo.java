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
 * @author jb
 */
public class HiddenGunInfo extends HiddenLoreInfo
{
    // PZ`gunID`durability`BuildType
    private static final int INFO_ID_IDX = 1;
    private static final int INFO_DUR_IDX = 2;
    private static final int INFO_BUILD_IDX = 3;
    private static final int INFO_LEN = 4;
    
    private static final int PRESHOT_VER_DUR = -666;
    private static final Build PRESHOT_VER_BUILD = Build.PRESHOT;
    
    public HiddenGunInfo(final String hiddenLoreInfo)
    {
        super(hiddenLoreInfo);
    }

    
    public HiddenGunInfo(final GunID id)
    {
        super(new String[] {
            String.valueOf(id.toString()),
            String.valueOf(PRESHOT_VER_DUR),
            PRESHOT_VER_BUILD.name()
        });
    }
    
    public String getGunIDStr()
    {
        if (super.isValid())
            return super.getInfoStr(INFO_ID_IDX);
        else
            return null;
    }
    
    public GunID getGunID()
    {
        return new GunID(getGunIDStr());
    }
    
    public CrackshotGun getGun()
    {
        return Guns.get(super.getInfoStr(INFO_ID_IDX));
    }
    
    public int getDurability()
    {
        return super.getInfoInt(INFO_DUR_IDX);
    }
    
    public boolean isBroken()
    {
        return isPostShot() && getDurability() >= 0;
    }
    
    public Build getBuild() 
    {
        try
        {
            return Build.valueOf(super.getInfoStr(INFO_BUILD_IDX));
        } 
        catch (IllegalArgumentException ex)
        {
            return null;
        }
    }
    
    public void setGunID(final String newID)
    {
        super.setInfoStr(INFO_ID_IDX, newID);
    }
    
    public void setDurability(final int dur)
    {
        super.setInfoInt(INFO_DUR_IDX, dur);
    }
    
    public int decrementDurability()
    {
        final int decDur = getDurability() - 1;
        super.setInfoInt(INFO_DUR_IDX, decDur);
        return decDur;
    }
    
    public void setBuild(final Build build)
    {
        super.setInfoStr(INFO_BUILD_IDX, build.name());
    }
    
    /**
     * Assures hidden gun info is pre-shot by checking magic values set for
     * both build and durability.
     * @return 
     */
    public boolean isPreShot()
    {
        return this.isValid()
            && this.getDurability() == PRESHOT_VER_DUR
            && this.getBuild().equals(PRESHOT_VER_BUILD);
    }
    
    public boolean isPostShot()
    {
        final Build build = this.getBuild();
        return isValid()
            && !(build == null || build.equals(PRESHOT_VER_BUILD));
    }
    
    public String getPreShotHiddenLore()
    {
        return new HiddenGunInfo(this.getGunID()).getHiddenLore();
    }
    
    @Override
    public boolean isValid()
    {
        return super.isValid()
            && super.getLength() == INFO_LEN
            && GunID.isValidID(getGunIDStr());
    }
}
