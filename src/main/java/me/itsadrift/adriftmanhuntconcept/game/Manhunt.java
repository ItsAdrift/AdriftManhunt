package me.itsadrift.adriftmanhuntconcept.game;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import me.itsadrift.adriftmanhuntconcept.AdriftManhuntConcept;
import me.itsadrift.adriftmanhuntconcept.game.gamesettings.GameSetting;
import me.itsadrift.adriftmanhuntconcept.game.gamesettings.GameSettings;
import me.itsadrift.adriftmanhuntconcept.game.gui.TeamSelector;
import me.itsadrift.adriftmanhuntconcept.utils.ItemBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Manhunt {
    private int id = 0;

    private final GameSettings gameSettings;

    private GameState gameState;

    private int runTime = 0;

    private BukkitTask runTimeTask;

    private MultiverseWorld world;

    private List<UUID> players = new ArrayList<>();

    private List<UUID> runners = new ArrayList<>();

    private List<UUID> hunters = new ArrayList<>();

    public HashMap<Material, Material> selectiveRandomMap = new HashMap<>();

    private final AdriftManhuntConcept main;

    public Manhunt(AdriftManhuntConcept main, TeamSelector teamSelector, GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.main = main;
        this.runners = teamSelector.getRunners();
        this.hunters = teamSelector.getHunters();
        this
                .players = Stream.concat(this.runners.stream(), this.hunters.stream()).collect(Collectors.toList());
        this.id = (new Random()).nextInt(100);
        for (UUID uuid : getPlayers()) {
            Bukkit.getPlayer(uuid).closeInventory();
            Bukkit.getPlayer(uuid).getInventory().clear();
        }
        if (gameSettings.getSettingValue(GameSetting.SELECTIVE_RANDOM_BLOCKS).asBoolean())
            prepareSelectiveRandom();
        prepareWorld();
    }

    public void prepareSelectiveRandom() {
        Material[] matList = Material.values();
        for (Material m : matList) {
            if (m.isBlock()) {
                int random = (new Random()).nextInt(matList.length);
                Material chosenMat = matList[random];
                this.selectiveRandomMap.put(m, chosenMat);
            }
        }
    }

    public void prepareWorld() {
        Bukkit.broadcastMessage(colour("&a&lAdriftManHunt BETA &7» &fThe game will start shortly."));
        Bukkit.getScheduler().runTask(this.main, new Runnable() {
            public void run() {
                Manhunt.this.createWorld();
                (new BukkitRunnable() {
                    int runs = 6;

                    public void run() {
                        if (Manhunt.this.main.getWorldManager().getMVWorld(Manhunt.this.getStringId()) != null) {
                            if (this.runs == 0) {
                                Manhunt.this.world = Manhunt.this.main.getWorldManager().getMVWorld(Manhunt.this.getStringId());
                                Manhunt.this.start();
                                cancel();
                                return;
                            }
                            this.runs--;
                            Bukkit.broadcastMessage(Manhunt.this.colour("&a&lAdriftManHunt BETA &7» &fThe game will start in &a&l" + this.runs + "&f seconds."));
                        }
                    }
                }).runTaskTimer(Manhunt.this.main, 0L, 20L);
            }
        });
    }

    public void start() {
        this.gameState = (this.gameSettings.getSettingValue(GameSetting.RELEASE_TIME).asInt() == 0) ? GameState.RUNNING : GameState.RUNNERS_RELEASED;
        if (!this.gameSettings.getSettingValue(GameSetting.END_REASON).asString().equalsIgnoreCase("END")) {
            this

                    .runTimeTask = (new BukkitRunnable() {
                public void run() {
                    Manhunt.this.runTime++;
                    Manhunt.this.sendTimeActionBar(Manhunt.this.runTime);
                    Manhunt.this.updateCompass();
                    if (Manhunt.this.runTime >= Manhunt.this.gameSettings.getSettingValue(GameSetting.END_REASON).asInt() * 60) {
                        Manhunt.this.stop(false);
                        cancel();
                        return;
                    }
                }
            }).runTaskTimerAsynchronously(this.main, 20L, 20L);
        } else {
            this

                    .runTimeTask = (new BukkitRunnable() {
                public void run() {
                    Manhunt.this.runTime++;
                    Manhunt.this.sendTimeActionBar(Manhunt.this.runTime);
                    Manhunt.this.updateCompass();
                }
            }).runTaskTimerAsynchronously(this.main, 20L, 20L);
        }
        if (this.gameState == GameState.RUNNERS_RELEASED)
            Bukkit.getScheduler().runTaskLater(this.main, new Runnable() {
                public void run() {
                    Manhunt.this.gameState = GameState.RUNNING;
                }
            }, (this.gameSettings.getSettingValue(GameSetting.RELEASE_TIME).asInt() * 20));
        Random random = new Random();
        for (UUID uuid : this.players) {
            if (Bukkit.getPlayer(uuid) != null) {
                Player player = Bukkit.getPlayer(uuid);
                player.setHealth(20.0D);
                player.setFoodLevel(20);
                Bukkit.getPlayer(uuid).sendTitle(colour("&a&lAdrift Manhunt BETA"), colour("&fby ItsAdrift"), 3, 25, 3);
                int r = this.gameSettings.getSettingValue(GameSetting.SPAWN_RADIUS).asInt();
                int x = random.nextInt(r + r) - r;
                int z = random.nextInt(r + r) - r;
                Location l = this.world.getSpawnLocation().add(x, 0.0D, z);
                l.setY(this.world.getCBWorld().getHighestBlockYAt(l));
                player.teleport(l);
                if (this.hunters.contains(uuid) &&
                        this.gameSettings.getSettingValue(GameSetting.APPLY_BLINDNESS).asBoolean() && this.gameSettings.getSettingValue(GameSetting.RELEASE_TIME).asInt() != 0)
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, this.gameSettings.getSettingValue(GameSetting.RELEASE_TIME).asInt() * 20, this.gameSettings.getSettingValue(GameSetting.BLINDNESS_LEVEL).asInt() + 1, false, false));
            }
        }
        for (UUID uuid : getHunters()) {
            Bukkit.getPlayer(uuid).getInventory().addItem((new ItemBuilder(Material.COMPASS)).setDisplayName("&c&lCompass").setLore("&7This compass will always point", "&7towards the person you", "&7want to stab.", "", "&7It even works in-real life!").build());
        }
        if (this.gameSettings.getSettingValue(GameSetting.RELEASE_TIME).asInt() != 0)
            Bukkit.getScheduler().runTaskLater(this.main, new Runnable() {
                public void run() {
                    for (UUID uuid : Manhunt.this.getHunters())
                        Bukkit.getPlayer(uuid).sendTitle(Manhunt.this.colour("&c&lHunting Begins"), Manhunt.this.colour("&fGo stab the other person"), 3, 30, 3);
                    for (UUID uuid : Manhunt.this.getRunners())
                        Bukkit.getPlayer(uuid).sendTitle(Manhunt.this.colour("&c&lHunter Released"), Manhunt.this.colour("&fRun. Run very fast."), 3, 30, 3);
                }
            }, this.gameSettings.getSettingValue(GameSetting.RELEASE_TIME).asInt() * 20);
    }

    public void runnerDied(UUID runner) {
        this.runners.remove(runner);
        Bukkit.broadcastMessage(colour("&a&lAdriftManHunt BETA &7» &fThe runner &b" + Bukkit.getPlayer(runner).getPlayer().getName() + "&f has been &cELIMINATED&f!"));
        if (this.runners.size() == 0)
            stop(true);
        Bukkit.getPlayer(runner).setGameMode(GameMode.SPECTATOR);
    }

    public void hunterDied(UUID hunter, Location deathLoc) {
        for (ItemStack i : Bukkit.getPlayer(hunter).getInventory().getContents()) {
            if (i != null && i.getType() != Material.AIR &&
                    !i.getItemMeta().getDisplayName().equalsIgnoreCase(colour("&c&lCompass")))
                deathLoc.getWorld().dropItem(deathLoc, i);
        }
        Bukkit.getPlayer(hunter).getInventory().clear();
        if (this.gameSettings.getSettingValue(GameSetting.HUNTER_RESPAWN_RADIUS).asInt() != 0) {
            Random random = new Random();
            int r = this.gameSettings.getSettingValue(GameSetting.HUNTER_RESPAWN_RADIUS).asInt();
            int x = random.nextInt(r + r) - r;
            int z = random.nextInt(r + r) - r;
            Location l = deathLoc.add(x, 0.0D, z);
            l.setY((l.getWorld().getHighestBlockYAt(l) + 1));
            Bukkit.getPlayer(hunter).teleport(l);
            Bukkit.getPlayer(hunter).getInventory().addItem((new ItemBuilder(Material.COMPASS)).setDisplayName("&c&lCompass").setLore("&7This compass will always point", "&7towards the person you", "&7want to stab.", "", "&7It even works in-real life!").build());
        }
        Bukkit.broadcastMessage(colour("&a&lAdriftManHunt BETA &7» &fThe hunter &c" + Bukkit.getPlayer(hunter).getPlayer().getName() + "&f died."));
    }

    public void stop(boolean huntersWin) {
        setGameState(GameState.ENDING);
        this.runTimeTask.cancel();
        final Vector launch = new Vector(0, 5, 0);
        if (huntersWin) {
            for (UUID uuid : getHunters()) {
                final Player player = Bukkit.getPlayer(uuid);
                player.sendTitle(colour("&6&lVictory"), colour("&fYou stabbed your friend. Sad."), 3, 65, 3);
                (new BukkitRunnable() {
                    int runs = 0;

                    public void run() {
                        if (this.runs >= 5) {
                            cancel();
                        } else {
                            player.setVelocity(launch);
                            player.playSound(Bukkit.getPlayer(uuid).getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5.0F, 0.0F);
                            this.runs++;
                        }
                    }
                }).runTaskTimer(this.main, 0L, 15L);
            }
            for (UUID uuid : getRunners())
                Bukkit.getPlayer(uuid).sendTitle(colour("&c&lGame Over"), colour("&fYou lost. Good job!"), 3, 65, 3);
        } else {
            for (UUID uuid : getRunners()) {
                final Player player = Bukkit.getPlayer(uuid);
                player.sendTitle(colour("&6&lVictory"), colour("&fYou survived!"), 3, 65, 3);
                (new BukkitRunnable() {
                    int runs = 0;

                    public void run() {
                        if (this.runs >= 5) {
                            cancel();
                        } else {
                            player.setVelocity(launch);
                            player.playSound(Bukkit.getPlayer(uuid).getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5.0F, 0.0F);
                            this.runs++;
                        }
                    }
                }).runTaskTimer(this.main, 0L, 15L);
            }
            for (UUID uuid : getHunters())
                Bukkit.getPlayer(uuid).sendTitle(colour("&c&lGame Over"), colour("&fH- How. Hunting is the easy part!"), 3, 65, 3);
        }
        String timeLasted = LocalTime.MIN.plusSeconds(this.runTime).toString();
        Bukkit.broadcastMessage(colour("&a&lAdriftManHunt BETA &7» &fThe game lasted &a" + timeLasted + "&f!"));
        Bukkit.getScheduler().runTaskLater(this.main, new Runnable() {
            public void run() {
                for (UUID uuid : Manhunt.this.getPlayers()) {
                    Bukkit.getPlayer(uuid).setGameMode(GameMode.CREATIVE);
                    Manhunt.this.setGameState(GameState.ENDED);
                }
                Manhunt.this.main.getWorldManager().removePlayersFromWorld(Manhunt.this.world.getName());
                Manhunt.this.deleteWorld();
                Manhunt.this.players.clear();
            }
        }, 80L);
    }

    public int getId() {
        return this.id;
    }

    public String getStringId() {
        return String.valueOf(this.id);
    }

    public GameSettings getGameSettings() {
        return this.gameSettings;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<UUID> getPlayers() {
        return this.players;
    }

    public List<UUID> getRunners() {
        return this.runners;
    }

    public List<UUID> getHunters() {
        return this.hunters;
    }

    public int getRunTime() {
        return this.runTime;
    }

    public void createWorld() {
        this.main.getWorldManager().addWorld(
                getStringId(), World.Environment.NORMAL, null, WorldType.NORMAL,

                Boolean.valueOf(true), null);
    }

    public void deleteWorld() {
        this.main.getWorldManager().unloadWorld(getStringId());
        this.main.getWorldManager().deleteWorld(getStringId());
        this.main.getWorldManager().removeWorldFromConfig(getStringId());
    }

    private String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void sendTimeActionBar(int runTime) {
        String timeLasted = LocalTime.MIN.plusSeconds(runTime).toString();
        TextComponent actionBar = new TextComponent(colour("&fCurrent run time: &a" + timeLasted + "&f!"));
        for (UUID uuid : getPlayers())
            Bukkit.getPlayer(uuid).spigot().sendMessage(ChatMessageType.ACTION_BAR, actionBar);
    }

    public void updateCompass() {
        if (getHunters().size() > 0)
            for (UUID uuid : getHunters()) {
                Player hunter = Bukkit.getPlayer(uuid);
                Player currentRunner = null;
                for (UUID runnersUUID : getRunners()) {
                    Player runner = Bukkit.getPlayer(runnersUUID);
                    if (currentRunner == null) {
                        currentRunner = runner;
                        continue;
                    }
                    if (hunter.getLocation().distance(runner.getLocation()) < hunter.getLocation().distance(currentRunner.getLocation()))
                        currentRunner = runner;
                }
                Bukkit.getPlayer(uuid).setCompassTarget(currentRunner.getLocation());
            }
    }
}


/* Location:              C:\Users\adrif\Desktop\Servers\Testing Server (1.18)\plugins\AdriftManhuntCONCEPT.jar!\me\itsadrift\adriftmanhuntconcept\game\Manhunt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */