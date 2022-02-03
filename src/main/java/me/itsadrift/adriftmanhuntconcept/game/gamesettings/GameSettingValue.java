package me.itsadrift.adriftmanhuntconcept.game.gamesettings;

public class GameSettingValue<T> {
  private T value;
  
  public GameSettingValue(T value) {
    this.value = value;
  }
  
  public T getValue() {
    return this.value;
  }
  
  public boolean asBoolean() {
    if (this.value instanceof Boolean)
      return ((Boolean)this.value).booleanValue(); 
    return false;
  }
  
  public String asString() {
    return this.value.toString();
  }
  
  public int asInt() {
    if (this.value instanceof Integer)
      return ((Integer)this.value).intValue(); 
    return 0;
  }
}


/* Location:              C:\Users\adrif\Desktop\Servers\Testing Server (1.18)\plugins\AdriftManhuntCONCEPT.jar!\me\itsadrift\adriftmanhuntconcept\game\gamesettings\GameSettingValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */