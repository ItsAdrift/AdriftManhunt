package me.itsadrift.adriftmanhuntconcept.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;

public class HexUtils {
  public static final char COLOR_CHAR = '§';
  
  public static String colour(String msg) {
    Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
    Matcher matcher = hexPattern.matcher(msg);
    StringBuffer buffer = new StringBuffer(msg.length() + 32);
    while (matcher.find()) {
      String group = matcher.group(1);
      matcher.appendReplacement(buffer, "§x§" + group
          .charAt(0) + '§' + group.charAt(1) + '§' + group
          .charAt(2) + '§' + group.charAt(3) + '§' + group
          .charAt(4) + '§' + group.charAt(5));
    } 
    return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
  }
}


/* Location:              C:\Users\adrif\Desktop\Servers\Testing Server (1.18)\plugins\AdriftManhuntCONCEPT.jar!\me\itsadrift\adriftmanhuntconcep\\utils\HexUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */