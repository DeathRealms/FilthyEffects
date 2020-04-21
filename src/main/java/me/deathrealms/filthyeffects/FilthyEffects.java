package me.deathrealms.filthyeffects;

import me.deathrealms.filthyeffects.commands.fe.FECommand;
import me.deathrealms.filthyeffects.listeners.EffectsListener;
import me.deathrealms.realmsapi.RealmsAPI;
import me.deathrealms.realmsapi.XMaterial;
import me.deathrealms.realmsapi.files.CustomFile;
import me.deathrealms.realmsapi.packets.MinecraftVersion;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class FilthyEffects extends RealmsAPI {
    public CustomFile items;
    private Map<String, CustomItem> customItems;

    public CustomItem getItem(String itemName) {
        loadCustomItems();
        if (customItems.containsKey(itemName.toLowerCase())) {
            return customItems.get(itemName.toLowerCase());
        }
        return null;
    }

    @Override
    public void onEnable() {
        if (!MinecraftVersion.isCurrentEqualOrHigher(MinecraftVersion.v1_13_R1)) {
            getLogger().severe(String.format("Disabled due to unsupported server version %s", MinecraftVersion.getCurrent().getShortVersion())
                    + ". Please use version 1.13 or higher.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        super.onEnable();
        Messages.load();
        items = new CustomFile("items");
        items.createFile(true);
        Bukkit.getPluginManager().registerEvents(new EffectsListener(this), this);
        new FECommand(this);
        loadCustomItems();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void loadCustomItems() {
        customItems = new HashMap<>();
        ConfigurationSection items = this.items.getConfigSection("items");
        if (items != null) {
            for (String customItem : items.getKeys(false)) {
                String itemName = customItem.toLowerCase();
                CustomItem item = new CustomItem();
                item.setMaterial(XMaterial.matchXMaterial(items.getString(itemName + ".type")));
                item.setName(items.getString(itemName + ".name"));
                item.setLore(items.getStringList(itemName + ".lore"));
                item.setItemFlags(items.getStringList(itemName + ".flags"));
                item.setUnbreakable(items.getBoolean(itemName + ".unbreakable"));
                item.setEnchantments(this.items.getConfigSection("items." + itemName + ".enchantments"));
                item.setEffects(this.items.getConfigSection("items." + itemName + ".effects"));
                item.setModifiers(this.items.getConfigSection("items." + itemName + ".modifiers"));
                customItems.put(itemName, item);
            }
        }
    }
}
