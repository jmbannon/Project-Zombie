/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.care_package.files;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import net.projectzombie.care_package.controller.StateChange;

/**
 *
 * @author com.gmail.jbann1994
 */
public class StateBuffer
{
    static private HashMap<String, StateChange> activeStateChanges;
    
    private StateBuffer() { /* Do nothing. */ }
    
    static public void put(final StateChange stateChange)
    {
        activeStateChanges.put(stateChange.getBaseName(), stateChange);
    }
    
    static public StateChange remove(final String baseName)
    {
        return activeStateChanges.remove(baseName);
    }
    
    static public boolean removeAndRestore(final String baseName)
    {
        final StateChange sc = activeStateChanges.get(baseName);
        if (sc != null)
        {
            sc.restoreState();
            return true;
        }
        else
        {
            return false;
        }
    }
    
    static public Set<String> active()
    {
        return activeStateChanges.keySet();
    }
    
    static public boolean contains(final String baseName)
    {
        return activeStateChanges.containsKey(baseName);
    }
    
    static public boolean contains(final StateChange stateChange)
    {
        return activeStateChanges.containsKey(stateChange.getBaseName());
    }
    
    static public void onDisable()
    {
        for (StateChange stateChange : activeStateChanges.values())
            stateChange.restoreState();
    }
}
