package org.aifusp.utils;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
public class MessageUtils {
    public  static String WhatsappPrefix = "&l「 &a&lWhatsApp &f&l」";
    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String getColoredMessage(String message) {

        if (Bukkit.getVersion().contains("1.16") ||Bukkit.getVersion().contains("1.17") || Bukkit.getVersion().contains("1.18") || Bukkit.getVersion().contains("1.19") || Bukkit.getVersion().contains("1.20"))  {
            Matcher match = pattern.matcher(message);
            while (match.find()){
                String color = message.substring(match.start(),match.end());
                message = message.replace(color,ChatColor.of(color)+"");
                match = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&',message);
    }

}
