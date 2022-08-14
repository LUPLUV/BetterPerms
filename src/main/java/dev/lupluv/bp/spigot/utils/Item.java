package dev.lupluv.bp.spigot.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Item {

    Material material;
    int amount = 1;
    short subID = 0;
    String displayName = "None";
    List<String> lore = new ArrayList<>();
    List<Enchantment> enchantments = new ArrayList<>();
    String skullOwner = "Notch";
    boolean shiny = false;
    boolean hideAttributes = true;
    boolean hideEnchants = true;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public short getSubID() {
        return subID;
    }

    public void setSubID(short subID) {
        this.subID = subID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public List<Enchantment> getEnchantments() {
        return enchantments;
    }

    public void addEnchantment(org.bukkit.enchantments.Enchantment enchantment, int level, boolean ignore) {
        this.enchantments.add(new Enchantment(enchantment, level, ignore));
    }

    public String getSkullOwner() {
        return skullOwner;
    }

    public void setSkullOwner(String skullOwner) {
        this.skullOwner = skullOwner;
    }

    public boolean isShiny() {
        return shiny;
    }

    public void setShiny(boolean shiny) {
        this.shiny = shiny;
    }

    public boolean isHideAttributes() {
        return hideAttributes;
    }

    public void setHideAttributes(boolean hideAttributes) {
        this.hideAttributes = hideAttributes;
    }

    public boolean isHideEnchants() {
        return hideEnchants;
    }

    public void setHideEnchants(boolean hideEnchants) {
        this.hideEnchants = hideEnchants;
    }

    public Item(Material material) {
        this.material = material;
    }

    public Item(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public Item(Material material, int amount, short subID) {
        this.material = material;
        this.amount = amount;
        this.subID = subID;
    }

    public ItemStack build(){
        if(material != Material.PLAYER_HEAD){
            ItemStack is = new ItemStack(material, amount, subID);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(displayName);
            im.setLore(lore);
            enchantments.forEach(enchant ->{
                im.addEnchant(enchant.getEnchantment(), enchant.getLevel(), enchant.isIgnore());
            });
            if(shiny){
                im.addEnchant(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 1, true);
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            if(hideAttributes)
                im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            if(hideEnchants)
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            is.setItemMeta(im);
            return is;
        }else{
            ItemStack is = new ItemStack(material);
            SkullMeta im = (SkullMeta) is.getItemMeta();
            im.setDisplayName(displayName);
            im.setOwner(skullOwner);
            im.setLore(lore);
            enchantments.forEach(enchant ->{
                im.addEnchant(enchant.getEnchantment(), enchant.getLevel(), enchant.isIgnore());
            });
            if(shiny){
                im.addEnchant(org.bukkit.enchantments.Enchantment.ARROW_DAMAGE, 1, true);
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            if(hideAttributes)
                im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            if(hideEnchants)
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            is.setItemMeta(im);
            return is;
        }
    }

}
