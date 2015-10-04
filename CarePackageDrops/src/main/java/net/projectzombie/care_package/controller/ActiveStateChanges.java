/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.care_package.controller;

import java.util.HashMap;

/**
 *
 * @author com.gmail.jbann1994
 */
public class ActiveStateChanges
{
    static private HashMap<String, StateChange> activeStateChanges;
    
    private ActiveStateChanges() { /* Do nothing. */ }
    
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
    
    static public boolean contains(final String baseName)
    {
        return activeStateChanges.containsKey(baseName);
    }
    
    static public void onDisable()
    {
        for (StateChange stateChange : activeStateChanges.values())
            stateChange.restoreState();
    }
}
