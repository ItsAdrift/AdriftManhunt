package me.itsadrift.adriftmanhuntconcept.game;

import me.itsadrift.adriftmanhuntconcept.AdriftManhuntConcept;
import me.itsadrift.adriftmanhuntconcept.game.gamesettings.GameSettings;
import me.itsadrift.adriftmanhuntconcept.game.gui.TeamSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameManager {
    private final AdriftManhuntConcept main;

    private final List<Manhunt> games = new ArrayList<>();

    private TeamSelector teamSelector;

    public GameManager(AdriftManhuntConcept main) {
        this.main = main;
    }

    public Manhunt createManhunt(GameSettings settings) {
        Manhunt m = new Manhunt(this.main, this.teamSelector, settings);
        this.games.add(m);
        return m;
    }

    public void createNewTeamSelector(GameSettings gameSettings) {
        this.teamSelector = new TeamSelector(this, gameSettings);
    }

    public TeamSelector getTeamSelector() {
        return this.teamSelector;
    }

    public Manhunt getManhunt(UUID uuid) {
        for (Manhunt manhunt : this.games) {
            if (manhunt.getPlayers().contains(uuid))
                return manhunt;
        }
        return null;
    }

    public AdriftManhuntConcept getMain() {
        return this.main;
    }
}


/* Location:              C:\Users\adrif\Desktop\Servers\Testing Server (1.18)\plugins\AdriftManhuntCONCEPT.jar!\me\itsadrift\adriftmanhuntconcept\game\GameManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */