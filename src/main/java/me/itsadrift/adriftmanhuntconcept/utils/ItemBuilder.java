package me.itsadrift.adriftmanhuntconcept.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
  ItemStack item;
  
  ItemMeta meta;
  
  int amount;
  
  List<String> lore = new ArrayList<>();
  
  String displayName;
  
  Map<Enchantment, Integer> enchants = new HashMap<>();
  
  boolean unbreakable;
  
  boolean glowing;
  
  public ItemBuilder(Material mat) {
    this.item = new ItemStack(mat);
    this.meta = this.item.getItemMeta();
  }
  
  public ItemBuilder(Material mat, int amount) {
    this.item = new ItemStack(mat, amount);
    this.meta = this.item.getItemMeta();
    this.amount = amount;
  }
  
  public ItemBuilder(ItemStack item) {
    this.item = item;
    this.amount = item.getAmount();
    this.meta = item.getItemMeta();
    this.meta = item.getItemMeta();
    if (this.meta.getLore() != null)
      this.lore = this.meta.getLore(); 
    this.displayName = this.meta.getDisplayName();
    this.enchants = this.meta.getEnchants();
    this.unbreakable = this.meta.isUnbreakable();
  }
  
  public ItemBuilder setLore(String... lore) {
    List<String> a = new ArrayList<>();
    for (String s : Arrays.<String>asList(lore))
      a.add(HexUtils.colour(s)); 
    this.lore = a;
    this.meta.setLore(a);
    this.item.setItemMeta(this.meta);
    return this;
  }
  
  public ItemBuilder setDisplayName(String name) {
    this.displayName = name;
    this.meta.setDisplayName(HexUtils.colour(name));
    this.item.setItemMeta(this.meta);
    return this;
  }
  
  public ItemBuilder addEnchant(Enchantment ench, int level) {
    this.meta.addEnchant(ench, level, true);
    this.item.setItemMeta(this.meta);
    this.enchants.put(ench, Integer.valueOf(level));
    return this;
  }
  
  public ItemBuilder setUnbreakable(boolean bool) {
    this.meta.setUnbreakable(bool);
    this.item.setItemMeta(this.meta);
    this.unbreakable = bool;
    return this;
  }
  
  public ItemBuilder setGlowing(boolean glowing) {
    this.glowing = glowing;
    this.meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
    this.meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
    return this;
  }
  
  public ItemStack build() {
    this.item.setItemMeta(this.meta);
    return this.item;
  }
  
  public ItemBuilder clone() {
    return new ItemBuilder(build());
  }
  
  private String colour(String s) {
    return ChatColor.translateAlternateColorCodes('&', s);
  }
}


/* Location:              C:\Users\adrif\Desktop\Servers\Testing Server (1.18)\plugins\AdriftManhuntCONCEPT.jar!\me\itsadrift\adriftmanhuntconcep\\utils\ItemBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */