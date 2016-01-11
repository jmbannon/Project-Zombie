/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types;

import java.util.ArrayList;

/**
 *
 * @author jesse
 */
public abstract class CSVInput
{
    private final String CSVName;
    private final String[] columnNames;
    private final String template;
    
    public CSVInput(final String CSVName,
                    final String[] columnNames)
    {
        this.CSVName = CSVName;
        this.columnNames = columnNames;
        this.template = null;
    }
    
    public CSVInput(final String CSVName,
                    final String template)
    {
        this.CSVName = CSVName;
        this.columnNames = null;
        this.template = template;
    }
    
    abstract public CSVValue[] getAll();
    abstract public CSVValue get(final String name);
    abstract public CSVValue[] get(final String[] names);
    abstract public CSVValue[] get(final String[] names,
                                   final boolean includeNull);

    public int initialize()
    {
        CSVValue[] temp = getAll();
        if (temp == null)
        {
            return -1;
        }
        else
        {
            return temp.length;
        }
    }
    
    public boolean contains(final String name)
    {
        return getIndex(name) != -1;
    }
    
    public int getIndex(final String name)
    {
        final CSVValue values[] = getAll();
        String temp;
        if (values == null || name == null)
            return -1;
        
        else
        {
            for (int i = 0; i < values.length; i++)
            {
                temp = values[i].getName();
                if (temp != null && temp.equalsIgnoreCase(name))
                    return i;
            }
            return -1;
        }
    }
    
    /**
     * @param names Names to retrieve.
     * @return ArrayList of the indexes at which they appear in the child's set of modifiers.
     */
    public ArrayList<Integer> getIndexes(final String names[])
    {
        final CSVValue values[] = getAll();
        final ArrayList<Integer> toReturn = new ArrayList<>();
        CSVValue valueTemp;
        String temp;
        
        if (values == null || names == null || names.length == 0)
            return null;
        
        for (String name : names)
        {
            for (int i = 0; i < values.length; i++)
            {
                valueTemp = values[i];
                if (valueTemp == null && (name == null || name.isEmpty() || name.equalsIgnoreCase("null")))
                {
                    toReturn.add(i);
                    break;
                }
                else if (valueTemp != null && name != null)
                {
                    temp = valueTemp.getName();
                    if (temp != null && temp.equalsIgnoreCase(name))
                    {
                        toReturn.add(i);
                        break;
                    }
                }
            }
        }
        if (toReturn.isEmpty())
            return null;
        else
            return toReturn;
    }
}
