/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public final class CSVReader
{
    private static Plugin plugin = null;
    
    private final File file;
    private final String[] values;
    private final ArrayList<ArrayList<String>> data;
    private final int rowCount;
    
    
    static public void initializePlugin(final Plugin pl)
    {
        plugin = pl;
        if (!pl.getDataFolder().exists())
            pl.getDataFolder().mkdir();
    }
    
    public CSVReader(final String fileName,
                     final String[] values)
    {
        this.file = new File(plugin.getDataFolder(), fileName);
        this.values = values;
        this.data = readData();
        if (data != null && data.get(0) != null)
            rowCount = data.get(0).size();
        else
        {
            writeBlankCSV();
            rowCount = 0;
        }
    }
    
    public int getRowCount()
    {
        return rowCount;
    }
    
    private ArrayList<ArrayList<String>> readData()
    {
        try
        {
            if (file == null || !file.exists() || !file.canRead())
                return null;
            
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final ArrayList<ArrayList<String>> toRet = new ArrayList<>();
            boolean firstLine = true;
            String lineValues[];
            String line;
            
            for (int i = 0; i < values.length; i++)
                toRet.add(new ArrayList<String>());
            
            while ((line = reader.readLine()) != null)
            {
                if (firstLine)
                {
                    firstLine = false;
                    continue;
                }
                
                lineValues = line.split(",");
                for (int i = 0; i < values.length; i++)
                {
                    if (i < lineValues.length)
                        toRet.get(i).add(lineValues[i]);
                    else
                        toRet.get(i).add(null);
                }
            }
            reader.close();
            return toRet;
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        catch (IOException ex)
        {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String[] getColumnString(final int columnNumber)
    {
        if (!isValidColumn(columnNumber))
            return null;
        
        final String[] toRet = new String[rowCount];
        for (int i = 0; i < rowCount; i++)
        {
            toRet[i] = data.get(columnNumber).get(i);
        }
        return toRet;
    }
    
    public int[] getColumnInt(final int columnNumber)
    {
        if (!isValidColumn(columnNumber))
            return null;
        
        final int[] toRet = new int[rowCount];
        for (int i = 0; i < rowCount; i++)
        {
            toRet[i] = Integer.valueOf(data.get(columnNumber).get(i));
        }
        return toRet;
    }
    
    public double[] getColumnDouble(final int columnNumber)
    {
        if (!isValidColumn(columnNumber))
            return null;
        
        final double[] toRet = new double[rowCount];
        for (int i = 0; i < rowCount; i++)
        {
            toRet[i] = Double.valueOf(data.get(columnNumber).get(i));
        }
        return toRet;
    }
    
    private boolean isValidColumn(final int columnNumber)
    {
        return data != null
                && columnNumber >= 0 
                && columnNumber < values.length
                && data.get(columnNumber) != null;
    }
    
    public boolean writeBlankCSV()
    {
        try {
            final FileWriter fw = new FileWriter(file);
            for (String str : values)
            {
                fw.write(str);
                fw.write(",");
            }
            fw.flush();
            fw.close();
            return true;
        }
        catch (IOException ex)
        {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
        
}
