/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.TestCase;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotGun;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author jbannon
 */
public class TestCrackshotGun extends TestCase
{
    public TestCrackshotGun()
    {
        
    }
    
    public void testUniqueIDs()
    {
        CrackshotGun enums[] = CrackshotGun.values();
        String msg;
        
        for (CrackshotGun gunOne : enums)
        {
            for (CrackshotGun gunTwo : enums)
            {
                 if (!gunOne.equals(gunTwo))
                 {
                     msg = "Expected " + gunOne.getUniqueId() + " != " + gunTwo.getUniqueId();
                     assertTrue(msg, gunOne.getUniqueId() != gunTwo.getUniqueId());
                 }
            }
        }
    }
    
    public void testGunAccess()
    {
        CrackshotGun enums[] = CrackshotGun.values();
        Boolean hasID;
        String msg;
        
        for (int i = 0; i < enums.length; i++)
        {
            hasID = false;
            msg = i + " is not in the sequential unique IDs";
            for (CrackshotGun gun : enums)
            {
                if (i == gun.getUniqueId())
                    hasID = true;
            }
            assertTrue(msg, hasID);
        }
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
