package dev.lupluv.bp.spigot.utils;

public class Enchantment {

    org.bukkit.enchantments.Enchantment enchantment;
    int level;
    boolean ignore;

    public org.bukkit.enchantments.Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public Enchantment(org.bukkit.enchantments.Enchantment enchantment, int level, boolean ignore) {
        this.enchantment = enchantment;
        this.level = level;
        this.ignore = ignore;
    }
}
