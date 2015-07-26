/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.yaml_generator;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotBase;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotGun;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon.PISTOL;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon.REVOLVER;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.HiddenStringUtils;
import org.bukkit.ChatColor;

/**
 *
 * @author jbannon
 */
public class ItemInformation
{
    private ItemInformation() { /* Do nothing */ }
    
    static
    protected String getItemName(final CrackshotGun gun)
    {
        final String ID = String.valueOf(gun.getEnumValue() * 13);
        final StringBuilder stb = new StringBuilder();
        
        stb.append(CrackshotLore.ITEM_COLOR);
        for (char c : ID.toCharArray())
        {
            stb.append('&');
            stb.append(c);
        }
        stb.append(CrackshotLore.ITEM_COLOR);
        stb.append(gun.getBase().toString());
        return stb.toString().replace(ChatColor.COLOR_CHAR, '&');
    }
    
    static
    protected String getItemType(final CrackshotGun gun)
    {
        final CrackshotBase base = gun.getBase();
        final int materialData = base.getItemData();
        final StringBuilder stb = new StringBuilder();
        
        stb.append(base.getItemID());
        if (materialData != 0)
        {
            stb.append('~');
            stb.append(materialData);
        }
        return stb.toString();
    }
    
    static
    protected String getItemLore(final CrackshotGun gun)
    {
        final StringBuilder stbVerify = new StringBuilder();
        stbVerify.append(CrackshotLore.seperator);
        stbVerify.append(CrackshotLore.verification);
        stbVerify.append(CrackshotLore.seperator);
        stbVerify.append(gun.getEnumValue());
        
        final StringBuilder stb = new StringBuilder();
        stb.append(CrackshotLore.line);
        stb.append(HiddenStringUtils.encodeString(stbVerify.toString()).replace(ChatColor.COLOR_CHAR, '&'));
        stb.append('|');
        stb.append(CrackshotLore.preShotVerification);
        
        return stb.toString();
    }
    
    static
    protected String getInventoryControl(final CrackshotGun gun)
    {
        return (gun.getBase().getWeaponType().equals(PISTOL)
                || gun.getBase().getWeaponType().equals(REVOLVER)) ? 
            "Group_Sidearm" : "Group_Primary";
    }
}
