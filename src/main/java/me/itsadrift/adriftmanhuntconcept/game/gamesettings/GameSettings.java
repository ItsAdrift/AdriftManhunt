package me.itsadrift.adriftmanhuntconcept.game.gamesettings;

import java.util.HashMap;
import me.itsadrift.adriftmanhuntconcept.AdriftManhuntConcept;

public class GameSettings {
  private AdriftManhuntConcept main;
  
  private HashMap<GameSetting, GameSettingValue> gameSettings;
  
  public GameSettings(AdriftManhuntConcept main) {
    this.gameSettings = new HashMap<>();
    this.main = main;
  }
  
  public GameSettings loadDefaultSettings() {
    this.gameSettings.put(GameSetting.SPAWN_RADIUS, new GameSettingValue<>(Integer.valueOf(5)));
    this.gameSettings.put(GameSetting.APPLY_BLINDNESS, new GameSettingValue<>(Boolean.valueOf(false)));
    this.gameSettings.put(GameSetting.BLINDNESS_LEVEL, new GameSettingValue<>(Integer.valueOf(5)));
    this.gameSettings.put(GameSetting.RELEASE_TIME, new GameSettingValue<>(Integer.valueOf(10)));
    this.gameSettings.put(GameSetting.RUNNERS_IMMUNITY, new GameSettingValue<>(Integer.valueOf(10)));
    this.gameSettings.put(GameSetting.END_REASON, new GameSettingValue<>(Integer.valueOf(20)));
    this.gameSettings.put(GameSetting.HUNTER_RESPAWN_RADIUS, new GameSettingValue<>(Integer.valueOf(0)));
    this.gameSettings.put(GameSetting.ALL_RANDOM_BLOCKS, new GameSettingValue<>(Boolean.valueOf(false)));
    this.gameSettings.put(GameSetting.SELECTIVE_RANDOM_BLOCKS, new GameSettingValue<>(Boolean.valueOf(false)));
    return this;
  }
  
  public GameSettings(int spawnRadius, boolean applyBlindness, int blindnessLevel, int releaseTime, int runnersImmunity, Object endReason, int hunterRespawnRadius, boolean allRandomBlocks, boolean selectiveRandomBlocks) {
    this.gameSettings = new HashMap<>();
    this.gameSettings.put(GameSetting.SPAWN_RADIUS, new GameSettingValue<>(Integer.valueOf(spawnRadius)));
    this.gameSettings.put(GameSetting.APPLY_BLINDNESS, new GameSettingValue<>(Boolean.valueOf(applyBlindness)));
    this.gameSettings.put(GameSetting.BLINDNESS_LEVEL, new GameSettingValue<>(Integer.valueOf(blindnessLevel)));
    this.gameSettings.put(GameSetting.RELEASE_TIME, new GameSettingValue<>(Integer.valueOf(releaseTime)));
    this.gameSettings.put(GameSetting.RUNNERS_IMMUNITY, new GameSettingValue<>(Integer.valueOf(runnersImmunity)));
    this.gameSettings.put(GameSetting.END_REASON, new GameSettingValue(endReason));
    this.gameSettings.put(GameSetting.HUNTER_RESPAWN_RADIUS, new GameSettingValue<>(Integer.valueOf(hunterRespawnRadius)));
    this.gameSettings.put(GameSetting.ALL_RANDOM_BLOCKS, new GameSettingValue<>(Boolean.valueOf(allRandomBlocks)));
    this.gameSettings.put(GameSetting.SELECTIVE_RANDOM_BLOCKS, new GameSettingValue<>(Boolean.valueOf(selectiveRandomBlocks)));
  }
  
  public GameSettings(GameSettingValue spawnRadius, GameSettingValue applyBlindness, GameSettingValue blindnessLevel, GameSettingValue releaseTime, GameSettingValue runnersImmunity, GameSettingValue endReason, GameSettingValue hunterRespawnRadius, GameSettingValue allRandomBlocks, GameSettingValue selectiveRandomBlocks) {
    this.gameSettings = new HashMap<>();
    this.gameSettings.put(GameSetting.SPAWN_RADIUS, spawnRadius);
    this.gameSettings.put(GameSetting.APPLY_BLINDNESS, applyBlindness);
    this.gameSettings.put(GameSetting.BLINDNESS_LEVEL, blindnessLevel);
    this.gameSettings.put(GameSetting.RELEASE_TIME, releaseTime);
    this.gameSettings.put(GameSetting.RUNNERS_IMMUNITY, runnersImmunity);
    this.gameSettings.put(GameSetting.END_REASON, endReason);
    this.gameSettings.put(GameSetting.HUNTER_RESPAWN_RADIUS, hunterRespawnRadius);
    this.gameSettings.put(GameSetting.ALL_RANDOM_BLOCKS, allRandomBlocks);
    this.gameSettings.put(GameSetting.SELECTIVE_RANDOM_BLOCKS, selectiveRandomBlocks);
  }
  
  public GameSettingValue getSettingValue(GameSetting setting) {
    return this.gameSettings.get(setting);
  }
  
  public void setSettingValue(GameSetting setting, GameSettingValue value) {
    this.gameSettings.put(setting, value);
  }
}


/* Location:              C:\Users\adrif\Desktop\Servers\Testing Server (1.18)\plugins\AdriftManhuntCONCEPT.jar!\me\itsadrift\adriftmanhuntconcept\game\gamesettings\GameSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */