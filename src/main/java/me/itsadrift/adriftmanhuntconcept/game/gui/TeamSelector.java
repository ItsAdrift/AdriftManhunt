package me.itsadrift.adriftmanhuntconcept.game.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.itsadrift.adriftmanhuntconcept.game.GameManager;
import me.itsadrift.adriftmanhuntconcept.game.gamesettings.GameSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeamSelector {
  private List<UUID> hunters = new ArrayList<>();
  
  private List<UUID> runners = new ArrayList<>();
  
  private GameManager gameManager;
  
  private GameSettings gameSettings;
  
  public TeamSelector(GameManager gameManager, GameSettings gameSettings) {
    this.gameManager = gameManager;
    this.gameSettings = gameSettings;
    TeamSelectorGUI teamSelectorGUI = gameManager.getMain().getTeamSelectorGUI();
    for (Player player : Bukkit.getOnlinePlayers())
      teamSelectorGUI.openGUI(player, false, false); 
  }
  
  public void selectTeam(UUID uuid, boolean hunterTeam) {
    this.hunters.remove(uuid);
    this.runners.remove(uuid);
    if (hunterTeam) {
      this.hunters.add(uuid);
    } else {
      this.runners.add(uuid);
    } 
    if (this.hunters.size() + this.runners.size() == Bukkit.getOnlinePlayers().size())
      this.gameManager.createManhunt(this.gameSettings); 
  }
  
  public void clearSelection(UUID uuid) {
    this.hunters.remove(uuid);
    this.runners.remove(uuid);
  }
  
  public List<UUID> getHunters() {
    return this.hunters;
  }
  
  public List<UUID> getRunners() {
    return this.runners;
  }
}


/* Location:              C:\Users\adrif\Desktop\Servers\Testing Server (1.18)\plugins\AdriftManhuntCONCEPT.jar!\me\itsadrift\adriftmanhuntconcept\game\gui\TeamSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */