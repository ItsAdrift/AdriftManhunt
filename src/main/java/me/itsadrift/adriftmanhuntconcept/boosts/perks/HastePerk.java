package me.itsadrift.adriftmanhuntconcept.boosts.perks;

import me.itsadrift.adriftmanhuntconcept.AdriftManhuntConcept;
import me.itsadrift.adriftmanhuntconcept.game.Manhunt;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class HastePerk implements Listener {
  public AdriftManhuntConcept main;
  
  public HastePerk(AdriftManhuntConcept main) {
    this.main = main;
  }
  
  @EventHandler
  public void onMine(BlockBreakEvent e) {
    if (this.main.getGameManager().getManhunt(e.getPlayer().getUniqueId()) != null) {
      Manhunt manhunt = this.main.getGameManager().getManhunt(e.getPlayer().getUniqueId());
    }
  }
}


/* Location:              C:\Users\adrif\Desktop\Servers\Testing Server (1.18)\plugins\AdriftManhuntCONCEPT.jar!\me\itsadrift\adriftmanhuntconcept\boosts\perks\HastePerk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */