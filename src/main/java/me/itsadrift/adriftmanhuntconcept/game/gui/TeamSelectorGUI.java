package me.itsadrift.adriftmanhuntconcept.game.gui;

import me.itsadrift.adriftmanhuntconcept.AdriftManhuntConcept;
import me.itsadrift.adriftmanhuntconcept.game.GameManager;
import me.itsadrift.adriftmanhuntconcept.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class TeamSelectorGUI implements Listener {
    ItemBuilder hunterTeam = (new ItemBuilder(Material.RED_STAINED_GLASS_PANE)).setDisplayName("&c&lHunter").setLore("&7Click to join the &cHUNTER &7team");

    ItemBuilder runnerTeam = (new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE)).setDisplayName("&b&lRunner").setLore("&7Click to join the &bRUNNER &7team");

    ItemBuilder decline = (new ItemBuilder(Material.BARRIER)).setDisplayName("&c&lDecline").setLore("&7Decline the invitation to join", "&7this &5EPIC &7manhunt game.");

    private final AdriftManhuntConcept main;

    private final GameManager gameManager;

    public TeamSelectorGUI(AdriftManhuntConcept main) {
        this.main = main;
        this.gameManager = main.getGameManager();
    }

    public void openGUI(Player player, boolean shiny, boolean shinyHunter) {
        Inventory inv = Bukkit.createInventory(null, 9, colour("&8Select Team"));
        ItemStack background = (new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)).setDisplayName("&7").build();
        for (int i = 0; i < inv.getSize(); i++)
            inv.setItem(i, background);
        if (shiny) {
            inv.setItem(3, shinyHunter ? this.hunterTeam.clone().setGlowing(true).build() : this.hunterTeam.build());
            inv.setItem(5, shinyHunter ? this.runnerTeam.build() : this.runnerTeam.clone().setGlowing(true).build());
            inv.setItem(8, this.decline.build());
        } else {
            inv.setItem(3, this.hunterTeam.build());
            inv.setItem(5, this.runnerTeam.build());
            inv.setItem(8, this.decline.build());
        }
        player.openInventory(inv);
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null && e.getCurrentItem() != null &&
                e.getView().getTitle().equals(colour("&8Select Team"))) {
            e.setCancelled(true);
            String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
            UUID uuid = e.getWhoClicked().getUniqueId();
            if (itemName.equals(colour("&c&lHunter"))) {
                openGUI((Player) e.getWhoClicked(), true, true);
                this.gameManager.getTeamSelector().selectTeam(uuid, true);
            } else if (itemName.equals(colour("&b&lRunner"))) {
                openGUI((Player) e.getWhoClicked(), true, false);
                this.gameManager.getTeamSelector().selectTeam(uuid, false);
            } else if (itemName.equals(colour("&c&lDecline"))) {
                e.getWhoClicked().closeInventory();
                this.gameManager.getTeamSelector().clearSelection(uuid);
            }
        }
    }

    private static String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}


/* Location:              C:\Users\adrif\Desktop\Servers\Testing Server (1.18)\plugins\AdriftManhuntCONCEPT.jar!\me\itsadrift\adriftmanhuntconcept\game\gui\TeamSelectorGUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */