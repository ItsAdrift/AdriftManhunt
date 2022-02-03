package me.itsadrift.adriftmanhuntconcept.command;

import me.itsadrift.adriftmanhuntconcept.AdriftManhuntConcept;
import me.itsadrift.adriftmanhuntconcept.game.Manhunt;
import me.itsadrift.adriftmanhuntconcept.game.gamesettings.GameSetting;
import me.itsadrift.adriftmanhuntconcept.game.gamesettings.GameSettingValue;
import me.itsadrift.adriftmanhuntconcept.game.gamesettings.GameSettings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManhuntCommand implements CommandExecutor {
  private AdriftManhuntConcept main;
  
  public ManhuntCommand(AdriftManhuntConcept main) {
    this.main = main;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (args[0].equalsIgnoreCase("setup")) {
      GameSettings settings = new GameSettings(fromString(args[1]), fromString(args[2]), parseInt(5), fromString(args[3]), fromString(args[3]), fromString(args[4]), fromString(args[5]), fromString(args[6]), fromString(args[7]));
      this.main.getGameManager().createNewTeamSelector(settings);
      sender.sendMessage(colour("&a&lAdriftManHunt BETA &7» &fYou have setup a game!"));
    } else if (args[0].equalsIgnoreCase("extend")) {
      Player player = (Player)sender;
      if (this.main.getGameManager().getManhunt(player.getUniqueId()) != null) {
        Manhunt manhunt = this.main.getGameManager().getManhunt(player.getUniqueId());
        manhunt.getGameSettings().setSettingValue(GameSetting.END_REASON, new GameSettingValue(Integer.valueOf(manhunt.getGameSettings().getSettingValue(GameSetting.END_REASON).asInt() + Integer.parseInt(args[1]))));
        player.sendMessage(colour("&a&lAdriftManHunt BETA &7» &fYou have extended the game by &a" + Integer.parseInt(args[1]) + "&f minutes!"));
      } 
    } else if (args[0].equalsIgnoreCase("stop")) {
      Player player = (Player)sender;
      if (this.main.getGameManager().getManhunt(player.getUniqueId()) != null) {
        Manhunt manhunt = this.main.getGameManager().getManhunt(player.getUniqueId());
        manhunt.stop(true);
        player.sendMessage(colour("&a&lAdriftManHunt BETA &7» &fYou have ended the game!"));
      } 
    } 
    return false;
  }
  
  private GameSettingValue fromString(String s) {
    if (isInt(s))
      return new GameSettingValue(Integer.valueOf(Integer.parseInt(s))); 
    if (isBoolean(s))
      return new GameSettingValue(Boolean.valueOf(Boolean.parseBoolean(s))); 
    return new GameSettingValue(s);
  }
  
  private GameSettingValue parseInt(int i) {
    return new GameSettingValue(Integer.valueOf(i));
  }
  
  private boolean isInt(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    } 
  }
  
  private boolean isBoolean(String s) {
    if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))
      return true; 
    return false;
  }
  
  private String colour(String s) {
    return ChatColor.translateAlternateColorCodes('&', s);
  }
}


/* Location:              C:\Users\adrif\Desktop\Servers\Testing Server (1.18)\plugins\AdriftManhuntCONCEPT.jar!\me\itsadrift\adriftmanhuntconcept\command\ManhuntCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */