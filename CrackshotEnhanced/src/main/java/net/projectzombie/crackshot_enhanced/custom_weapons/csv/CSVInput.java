/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.csv;

import java.util.ArrayList;

/**
 *
 * @author jesse
 * @param <T>
 */
public abstract class CSVInput<T extends CSVValue>
{
    private final String CSVName;
    private final String[] columnNames;
    private final String template;
    private final T[] readValues;
    
    public CSVInput(final String CSVName,
                    final T[] readValues,
                    final String[] columnNames)
    {
        this.CSVName = CSVName;
        this.readValues = readValues;
        this.columnNames = columnNames;
        this.template = null;
    }
    
    public CSVInput(final String CSVName,
                    final T[] readValues,
                    final String template)
    {
        this.CSVName = CSVName;
        this.readValues = readValues;
        this.columnNames = null;
        this.template = template;
    }
    
    public T[] getAll()
    {
        return readValues;
    }
    
    public T getNullValue()
    {
        return null;
    }
    
    public T get(final String name)
    {
        for (T temp : readValues)
        {
            if (temp.getName().equalsIgnoreCase(name))
            {
                return temp;
            }
        }
        return null;
    }
    
    /**
     *
     * @param names
     * @return
     */
    public ArrayList<T> get(final String[] names)
    {
        return get(names, false);
    }
    
    public ArrayList<T> get(final String[] names,
                            final boolean includeNull)
    {
        final ArrayList<T> matchValues = new ArrayList<>();
        String tempName;
        
        if (includeNull)
            matchValues.add(getNullValue());
        
        if (names == null || names.length == 0)
            return matchValues;
        
        for (T temp : readValues)
        {
            for (String name : names)
            {
                if (temp == null)
                {
                    if (name == null || name.isEmpty() || name.equalsIgnoreCase("null"))
                    {
                        matchValues.add(temp);
                    }
                }
                else
                {
                    tempName = temp.getName();
                    if (tempName != null && tempName.equalsIgnoreCase(name))
                    {
                        matchValues.add(temp);
                    }
                }
            }
        }
        
        return matchValues;
    }

    public int initialize()
    {
        if (readValues == null)
        {
            return -1;
        }
        else
        {
            return readValues.length;
        }
    }

    
}
