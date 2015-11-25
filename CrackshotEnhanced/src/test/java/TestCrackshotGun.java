/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import junit.framework.TestCase;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.GunSkeleton;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jbannon
 */
public class TestCrackshotGun extends TestCase
{
    public TestCrackshotGun()
    {
        
    }
    
    public void testMethd()
    {
        for (Material mat : Material.values())
        {
            ItemStack temp = new ItemStack(mat);
            
            System.out.println(mat.name());
            System.out.println("Has meta: " + String.valueOf(temp.hasItemMeta()));
            if (temp.hasItemMeta())
                System.out.println("Has lore: " + String.valueOf(temp.getItemMeta().hasLore()));
            System.out.println("---------------------------");
        }
        
        assertTrue(true);
    }
    
    public void testGunAccess2()
    {
        boolean indexMatch = true;
        ArrayList<CrackshotGun> guns = Guns.getGuns();
        int id;
        String msg = "should not see this";
        
        for (int i = 0; i < guns.size(); i++)
        {
            id = guns.get(i).getUniqueId();
            if (id != i)
            {
                indexMatch = false;
                msg = "GunAccess(" + i + ") != uniqueId(" + id + ").";
                break;
            }
        }
        
        assertTrue(msg, indexMatch);
    }
    

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
