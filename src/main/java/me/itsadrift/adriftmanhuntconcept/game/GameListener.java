package me.itsadrift.adriftmanhuntconcept.game;

import me.itsadrift.adriftmanhuntconcept.AdriftManhuntConcept;
import me.itsadrift.adriftmanhuntconcept.game.gamesettings.GameSetting;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.UUID;

public class GameListener implements Listener {
    public AdriftManhuntConcept main;

    public GameListener(AdriftManhuntConcept main) {
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (this.main.getGameManager().getManhunt(e.getPlayer().getUniqueId()) != null) {
            Manhunt manhunt = this.main.getGameManager().getManhunt(e.getPlayer().getUniqueId());
            if (manhunt.getGameState() == GameState.RUNNERS_RELEASED && manhunt.getGameSettings().getSettingValue(GameSetting.RELEASE_TIME).asInt() != 0) {
                if (manhunt.getRunners().contains(e.getPlayer().getUniqueId())) return;
                e.getPlayer().teleport(e.getFrom());
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (this.main.getGameManager().getManhunt(player.getUniqueId()) != null) {
                Manhunt manhunt = this.main.getGameManager().getManhunt(player.getUniqueId());
                if (manhunt.getRunTime() <= manhunt.getGameSettings().getSettingValue(GameSetting.RUNNERS_IMMUNITY).asInt()) {
                    e.setCancelled(true);
                    return;
                }
                if (((Player) e.getEntity()).getHealth() - e.getFinalDamage() <= 0.0D) {
                    UUID uuid = e.getEntity().getUniqueId();
                    e.setCancelled(true);
                    if (manhunt.getRunners().contains(uuid)) {
                        manhunt.runnerDied(uuid);
                    } else if (manhunt.getHunters().contains(uuid)) {
                        Location deathLoc = player.getLocation();
                        deathLoc.setY(deathLoc.getWorld().getHighestBlockYAt(deathLoc));
                        manhunt.hunterDied(uuid, deathLoc);
                    }
                    player.setHealth(20.0D);
                    player.setFoodLevel(20);
                }
            }
        }
    }

    @EventHandler
    public void onEndComplete(PlayerChangedWorldEvent e) {
        Manhunt manhunt = this.main.getGameManager().getManhunt(e.getPlayer().getUniqueId());
        if (this.main.getGameManager().getManhunt(e.getPlayer().getUniqueId()) != null && e.getFrom().getName().equals(manhunt.getStringId() + "_the_end"))
            manhunt.stop(false);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (this.main.getGameManager().getManhunt(e.getPlayer().getUniqueId()) != null) {
            Manhunt manhunt = this.main.getGameManager().getManhunt(e.getPlayer().getUniqueId());
            if (manhunt.getGameSettings().getSettingValue(GameSetting.ALL_RANDOM_BLOCKS).asBoolean()) {
                e.setDropItems(false);
                Material[] matlist = Material.values();
                int random = (new Random()).nextInt(matlist.length);
                Material mat = matlist[random];
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(mat));
            } else if (manhunt.getGameSettings().getSettingValue(GameSetting.SELECTIVE_RANDOM_BLOCKS).asBoolean()) {
                e.setDropItems(false);
                Material blockType = e.getBlock().getType();
                if (manhunt.selectiveRandomMap.get(blockType) != null)
                    e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(manhunt.selectiveRandomMap.get(blockType)));
            }
        }
    }
}


/* Location:              C:\Users\adrif\Desktop\Servers\Testing Server (1.18)\plugins\AdriftManhuntCONCEPT.jar!\me\itsadrift\adriftmanhuntconcept\game\GameListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */