package me.itsadrift.adriftmanhuntconcept;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import me.itsadrift.adriftmanhuntconcept.command.ManhuntCommand;
import me.itsadrift.adriftmanhuntconcept.game.GameListener;
import me.itsadrift.adriftmanhuntconcept.game.GameManager;
import me.itsadrift.adriftmanhuntconcept.game.gui.TeamSelectorGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class AdriftManhuntConcept extends JavaPlugin {
    private static AdriftManhuntConcept instance;

    private GameManager gameManager;

    private MultiverseCore multiverseCore;

    private MVWorldManager mvWorldManager;

    private TeamSelectorGUI teamSelectorGUI;

    public static AdriftManhuntConcept getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        this.gameManager = new GameManager(this);
        this.multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        this.mvWorldManager = this.multiverseCore.getMVWorldManager();
        this.teamSelectorGUI = new TeamSelectorGUI(this);
        getCommand("manhunt").setExecutor(new ManhuntCommand(this));
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
        Bukkit.getPluginManager().registerEvents(this.teamSelectorGUI, this);

    }

    public void onDisable() {
    }

    public MultiverseCore getMultiverseCore() {
        return this.multiverseCore;
    }

    public MVWorldManager getWorldManager() {
        return this.mvWorldManager;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public TeamSelectorGUI getTeamSelectorGUI() {
        return this.teamSelectorGUI;
    }
}


/* Location:              C:\Users\adrif\Desktop\Servers\Testing Server (1.18)\plugins\AdriftManhuntCONCEPT.jar!\me\itsadrift\adriftmanhuntconcept\AdriftManhuntConcept.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */