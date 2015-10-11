/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.consistentchatapi;

import org.bukkit.ChatColor;

/**
 *
 * @author com.gmail.jbann1994
 */
public class PluginChat
{
    static private final String BRACKET_COL = ChatColor.GRAY.toString();
    
    private final String pluginNameTag;
    private final String plainText;
    private final String keywordColorOne;
    private final String keywordColorTwo;
    
    public PluginChat(final String pluginTag,
                      final ChatColor tagColor,
                      final ChatColor plainText,
                      final ChatColor keyWordColorOne,
                      final ChatColor keyWordColorTwo)
    {
        this.pluginNameTag   = buildTag(pluginTag, tagColor, plainText);
        this.plainText       = plainText.toString();
        this.keywordColorOne = keyWordColorOne.toString();
        this.keywordColorTwo = keyWordColorTwo.toString();
    }
    
    public String getTag() { return this.pluginNameTag; }
    
    /**
     * Colors the given string to keywordColorOne.
     * @param string String to change color.
     * @return Colored string.
     */
    public String toK1(final String string)
    {
        return keywordColorOne + string + plainText;
    }
    
    /**
     * Colors the given string to keywordColorTwo.
     * @param string String to change color.
     * @return Colored string.
     */
    public String toK2(final String string)
    {
        return keywordColorTwo + string + plainText;
    }
    
    public String toPT(final String string)
    {
        return plainText + string + plainText;
    }
    
    /**
     * Converts the string to the given ChatColor.
     * @param color Color to convert the string.
     * @param string String to be colored.
     * @return Properly colored string.
     */
    public String toColor(final ChatColor color,
                          final String string)
    {
        return color.toString() + string + plainText;
    }
    
    /**
     * Builds the tag with proper brackets and color.
     * @param pluginTag Name of the tag within brackets
     * @param tagColor Color of the name tag within brackets.
     * @param plainText
     * @return Properly colored plugin tag.
     */
    private static String buildTag(final String pluginTag,
                                  final ChatColor tagColor,
                                  final ChatColor plainText)
    {
         return BRACKET_COL + "[" + tagColor + pluginTag + BRACKET_COL + "] " + plainText;
    }
}
