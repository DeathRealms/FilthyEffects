package me.deathrealms.filthyeffects;

import me.deathrealms.realmsapi.XMaterial;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class CustomItem {
    private XMaterial material;
    private String name;
    private List<String> lore;
    private List<String> itemFlags;
    private boolean unbreakable;
    private ConfigurationSection enchantments;
    private ConfigurationSection effects;
    private ConfigurationSection modifiers;

    public XMaterial getMaterial() {
        return material;
    }

    public void setMaterial(XMaterial material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public List<String> getItemFlags() {
        return itemFlags;
    }

    public void setItemFlags(List<String> itemFlags) {
        this.itemFlags = itemFlags;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    public ConfigurationSection getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(ConfigurationSection enchantments) {
        this.enchantments = enchantments;
    }

    public ConfigurationSection getEffects() {
        return effects;
    }

    public void setEffects(ConfigurationSection effects) {
        this.effects = effects;
    }

    public ConfigurationSection getModifiers() {
        return modifiers;
    }

    public void setModifiers(ConfigurationSection modifiers) {
        this.modifiers = modifiers;
    }
}
