/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final ArrayList<ArrayList<String>> data;
    private final int rowCount;
    private final int columnCount;
    
    
    static public void initializePlugin(final Plugin pl)
    {
        plugin = pl;
        if (!pl.getDataFolder().exists())
            pl.getDataFolder().mkdir();
    }
    
    /**
     * Reads a CSV in the plugin data folder column-wise.
     * @param fileName Name of the file.
     * @param columnValues Name of each column.
     */
    public CSVReader(final String fileName,
                     final String[] columnValues)
    {
        this.file = new File(plugin.getDataFolder(), fileName);
        this.data = readData(true, 1, columnValues);
        if (data != null && data.get(0) != null)
        {
            rowCount = data.get(0).size();
            columnCount = columnValues.length;
        }
        else
        {
            writeBlankCSV(columnValues);
            rowCount = 0;
            columnCount = 0;
        }
    }
    
    /**
     * Reads a CSV in the plugin data folder row-wise.
     * @param fileName Name of the file.
     * @param linesToSkip Number of lines to skip when reading the CSV.
     */
    public CSVReader(final String fileName,
                     final int linesToSkip)
    {
        this.file = new File(plugin.getDataFolder(), fileName);
        this.data = readData(false, linesToSkip, null);
        this.columnCount = -1;
        if (data != null)
        {
            this.rowCount = data.size();
        }
        else
        {
            this.rowCount = 0;

        }
    }
    
    public int getRowCount()
    {
        return rowCount;
    }
    
    private ArrayList<ArrayList<String>> readData(final boolean columnWise,
                                                  final int linesToSkip,
                                                  final String[] columnValues)
    {
        if (file == null || !file.exists() || !file.canRead())
            return null;
        
        try
        {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final ArrayList<String> lines = new ArrayList<>();
            int lineCount = 0;
            String line;
            
            while ((line = reader.readLine()) != null)
            {
                if (lineCount++ >= linesToSkip)
                    lines.add(line);
            }
            reader.close();
            
            if (columnWise)
                return getColumnData(columnValues, lines);
            else
                return getRowData(lines);
        }
        catch (Exception ex)
        {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
    
    private ArrayList<ArrayList<String>> getColumnData(final String[] columnValues,
                                                       final ArrayList<String> lines)
    {
        final ArrayList<ArrayList<String>> toRet = new ArrayList<>();
        String lineValues[];
        
        for (int i = 0; i < columnValues.length; i++)
        {
            toRet.add(new ArrayList<String>());
        }
        
        for (String line : lines)
        {
            lineValues = line.split(",");
            for (int i = 0; i < columnValues.length; i++)
            {
                if (i < lineValues.length)
                    toRet.get(i).add(lineValues[i]);
                else
                    toRet.get(i).add(null);
            }
        }
        return toRet;
    }
    
    private ArrayList<ArrayList<String>> getRowData(final ArrayList<String> lines)
    {
        final ArrayList<ArrayList<String>> toRet = new ArrayList<>();
        String lineValues[];
        
        for (int i = 0; i < lines.size(); i++)
        {
            toRet.add(new ArrayList<String>());
            lineValues = lines.get(i).split(",");
            toRet.get(i).addAll(Arrays.asList(lineValues));
        }
        
        return toRet;
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
    
    public String[] getRowData(final int rowNumber)
    {
        if (!isValidRow(rowNumber))
            return null;
        
        final ArrayList<String> rowData = data.get(rowNumber);
        final String[] toRet = new String[rowData.size()];
        for (int i = 0; i < rowData.size(); i++)
        {
            toRet[i] = rowData.get(i);
        }
        return toRet;
    }
    
    private boolean isValidColumn(final int columnNumber)
    {
        return data != null
                && columnNumber >= 0 
                && columnNumber < columnCount
                && data.get(columnNumber) != null;
    }
    
    private boolean isValidRow(final int rowNumber)
    {
        return data != null
                && rowNumber >= 0
                && rowNumber < rowCount
                && data.get(rowNumber) != null;
    }
    
    public boolean writeBlankCSV(final String[] values)
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
