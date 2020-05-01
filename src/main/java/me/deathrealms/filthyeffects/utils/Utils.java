package me.deathrealms.filthyeffects.utils;

import me.deathrealms.filthyeffects.CustomItem;
import me.deathrealms.filthyeffects.FilthyEffects;
import me.deathrealms.filthyeffects.Messages;
import me.deathrealms.filthyeffects.listeners.EffectsListener;
import me.deathrealms.realmsapi.RealmsAPI;
import me.deathrealms.realmsapi.XEnchantment;
import me.deathrealms.realmsapi.XMaterial;
import me.deathrealms.realmsapi.XPotion;
import me.deathrealms.realmsapi.items.ItemBuilder;
import me.deathrealms.realmsapi.source.CommandSource;
import me.deathrealms.realmsapi.user.User;
import net.md_5.bungee.api.chat.ClickEvent;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Utils extends me.deathrealms.realmsapi.utils.Utils {
    private final FilthyEffects plugin;

    public Utils(FilthyEffects plugin) {
        this.plugin = plugin;
    }

    public void info(CommandSource source, String itemName) {
        CustomItem item = plugin.getItem(itemName);
        if (item == null) {
            source.sendMessage("&cCustom item &f" + itemName + " &cdoes not exist.");
        } else {
            source.sendMessage("&7&m----------------------------------------------------");
            source.sendMessage("&cItem: &f" + itemName);
            source.sendComponent("&cType: &f" + item.getMaterial().toWord(),
                    "fe.command.edit",
                    "/fe edit " + itemName + " settype ",
                    Collections.singletonList("&cClick to perform &f/fe edit " + itemName + " settype "),
                    ClickEvent.Action.SUGGEST_COMMAND);
            if (item.getName() != null) {
                source.sendComponent("&cName: &f" + item.getName(),
                        "fe.command.edit",
                        "/fe edit " + itemName + " setname ",
                        Collections.singletonList("&cClick to perform &f/fe edit " + itemName + " setname "),
                        ClickEvent.Action.SUGGEST_COMMAND);
            }
            if (item.isUnbreakable()) {
                source.sendComponent("&cUnbreakable: &f" + item.isUnbreakable(),
                        "fe.command.edit",
                        "/fe edit " + itemName + " setunbreakable ",
                        Collections.singletonList("&cClick to perform &f/fe edit " + itemName + " setunbreakable "),
                        ClickEvent.Action.SUGGEST_COMMAND);
            }
            if (item.getItemFlags() != null && !item.getItemFlags().isEmpty()) {
                source.sendMessage("&cItem Flags: ");
                for (String flag : item.getItemFlags()) {
                    source.sendMessage("  - " + WordUtils.capitalizeFully(flag.replace("_", " ")));
                }
            }
            if (item.getLore() != null && !item.getLore().isEmpty()) {
                source.sendMessage("");
                source.sendMessage("&cLore: ");
                for (String lore : item.getLore()) {
                    source.sendMessage("  - " + lore);
                }
            }
            if (item.getEnchantments() != null && !item.getEnchantments().getKeys(false).isEmpty()) {
                source.sendMessage("");
                source.sendMessage("&cEnchantments: ");
                for (String enchantment : item.getEnchantments().getKeys(false)) {
                    source.sendMessage("  - " + WordUtils.capitalizeFully(enchantment.replace("_", " ")) + " " + item.getEnchantments().getInt(enchantment));
                }
            }
            if (item.getEffects() != null && !item.getEffects().getKeys(false).isEmpty()) {
                source.sendMessage("");
                source.sendMessage("&cEffects: ");
                for (String effect : item.getEffects().getKeys(false)) {
                    XPotion effectType = XPotion.matchXPotion(effect);
                    source.sendMessage("  - " + WordUtils.capitalizeFully(effectType.getVanillaName().replace("_", " ")) + " " + item.getEffects().getInt(effect));
                }
            }
            if (item.getModifiers() != null && !item.getModifiers().getKeys(false).isEmpty()) {
                source.sendMessage("");
                source.sendMessage("&cModifiers: ");
                for (String modifier : item.getModifiers().getKeys(false)) {
                    source.sendMessage("  - " + WordUtils.capitalizeFully(modifier.replace("_", " ")) + " " + item.getModifiers().getInt(modifier));
                }
            }
            source.sendMessage("&7&m----------------------------------------------------");
        }
    }

    public void editItem(CommandSource source, String itemName, String option, String key, Object value) {
        if (plugin.getItem(itemName) == null) {
            source.sendMessage("&cCustom item &f" + itemName + " &cdoes not exist.");
        } else {
            switch (option.toLowerCase()) {
                case "addeffect": {
                    XPotion effectType = XPotion.matchXPotion(key);
                    if (effectType == null) {
                        source.sendMessage("&cPlease enter a valid effect type.");
                    } else {
                        int level = 1;
                        if (NumberUtils.isNumber(String.valueOf(value))) {
                            level = Integer.parseInt(String.valueOf(value));
                        }
                        plugin.items.set("items." + itemName + ".effects." + effectType.getVanillaName().toLowerCase(), level);
                        source.sendMessage("&aAdded &f" + effectType.getVanillaName().toLowerCase() + " " + level + " &aeffect to &f" + itemName);
                        plugin.loadCustomItems();
                    }
                    break;
                }
                case "addenchant": {
                    XEnchantment enchantment = XEnchantment.matchXEnchantment(key);
                    if (enchantment == null) {
                        source.sendMessage("&cPlease enter a valid enchantment.");
                    } else {
                        int level = 1;
                        if (NumberUtils.isNumber(String.valueOf(value))) {
                            level = Integer.parseInt(String.valueOf(value));
                        }
                        plugin.items.set("items." + itemName + ".enchantments." + enchantment.getVanillaName().toLowerCase(), level);
                        source.sendMessage("&aAdded &f" + enchantment.getVanillaName().toLowerCase() + " " + level + " &aeffect to &f" + itemName);
                        plugin.loadCustomItems();
                    }
                    break;
                }
                case "addmodifier": {
                    try {
                        Attribute attribute = Attribute.valueOf(key.toUpperCase());
                        int level = 1;
                        if (NumberUtils.isNumber(String.valueOf(value))) {
                            level = Integer.parseInt(String.valueOf(value));
                        }
                        plugin.items.set("items." + itemName + ".modifiers." + attribute.name().toLowerCase(), level);
                        source.sendMessage("&aAdded &f" + attribute.name().toLowerCase() + " " + level + " &amodifier to &f" + itemName);
                        plugin.loadCustomItems();
                    } catch (IllegalArgumentException ignored) {
                        source.sendMessage("&cPlease enter a valid modifier.");
                    }
                    break;
                }
                case "addflag": {
                    try {
                        ItemFlag itemFlag = ItemFlag.valueOf(key.toUpperCase());
                        List<String> flagList = plugin.items.getStringList("items." + itemName + ".flags");
                        if (flagList.contains(itemFlag.name().toLowerCase())) {
                            source.sendMessage("&cCustom item &f" + itemName + " &calready contains flag &f" + itemFlag.name().toLowerCase());
                            return;
                        }
                        flagList.add(itemFlag.name().toLowerCase());
                        plugin.items.set("items." + itemName + ".flags", flagList);
                        source.sendMessage("&aAdded &f" + itemFlag.name().toLowerCase() + " &aflag to &f" + itemName);
                        plugin.loadCustomItems();
                    } catch (IllegalArgumentException ignored) {
                        source.sendMessage("&cPlease enter a valid item flag.");
                    }
                    break;
                }
                case "settype": {
                    try {
                        ItemStack itemType = new ItemStack(Material.valueOf(ChatColor.stripColor(key.toUpperCase())));
                        plugin.items.set("items." + itemName + ".type", itemType.getType().name());
                        source.sendMessage("&aSet item type of &f" + itemName + " &ato &f" + itemType.getType().name().toLowerCase());
                        plugin.loadCustomItems();
                    } catch (IllegalArgumentException ignored) {
                        source.sendMessage("&cPlease enter a valid item type.");
                    }
                    break;
                }
                case "setname": {
                    plugin.items.set("items." + itemName + ".name", key);
                    source.sendMessage("&aSet name of &f" + itemName + " &ato &f" + key);
                    plugin.loadCustomItems();
                    break;
                }
                case "setunbreakable": {
                    plugin.items.set("items." + itemName + ".unbreakable", Boolean.parseBoolean(String.valueOf(key)));
                    source.sendMessage("&aSet unbreakable for &f" + itemName + " &ato &f" + Boolean.parseBoolean(String.valueOf(key)));
                    plugin.loadCustomItems();
                    break;
                }
                case "removeeffect": {
                    plugin.items.set("items." + itemName + ".effects." + key, null);
                    source.sendMessage("&cRemoved &f" + key + " &ceffect from &f" + itemName);
                    plugin.loadCustomItems();
                    break;
                }
                case "removeenchant": {
                    plugin.items.set("items." + itemName + ".enchantments." + key, null);
                    source.sendMessage("&cRemoved &f" + key + " &cenchantment from &f" + itemName);
                    plugin.loadCustomItems();
                    break;
                }
                case "removemodifier": {
                    plugin.items.set("items." + itemName + ".modifiers." + key, null);
                    source.sendMessage("&cRemoved &f" + key + " &cmodifier from &f" + itemName);
                    plugin.loadCustomItems();
                    break;
                }
                case "removeflag": {
                    List<String> flagList = plugin.items.getStringList("items." + itemName + ".flags");
                    flagList.remove(key);
                    plugin.items.set("items." + itemName + ".flags", flagList);
                    source.sendMessage("&cRemoved &f" + key + " &cflag from &f" + itemName);
                    plugin.loadCustomItems();
                    break;
                }
                default: {
                    source.sendMessage("&cPlease enter a valid option to edit.");
                }
            }
        }
    }

    public void removeItem(CommandSource source, String itemName) {
        if (plugin.getItem(itemName) == null) {
            source.sendMessage(Messages.prefix + "&cCustom item &f" + itemName + " &cdoes not exist.");
        } else {
            plugin.items.set("items." + itemName, null);
            source.sendMessage(Messages.prefix + Messages.removeItem.replace("%item_name%", itemName));
        }
    }

    public void createItem(User user, String itemName) {
        if (plugin.getItem(itemName) != null) {
            user.sendMessage(Messages.prefix + "&cCustom item &f" + itemName + " &calready exists.");
        } else {
            ItemStack item = user.getItemInMainHand();
            if (item.getType() == Material.AIR) {
                user.sendMessage(Messages.prefix + "&cCannot create custom item with item type &fAIR");
            } else {
                String itemSection = "items." + itemName;
                plugin.items.set(itemSection + ".type", item.getType().name());
                if (item.hasItemMeta()) {
                    if (item.getItemMeta().hasDisplayName()) {
                        plugin.items.set(itemSection + ".name", item.getItemMeta().getDisplayName());
                    }
                    if (item.getItemMeta().hasLore()) {
                        plugin.items.set(itemSection + ".lore", item.getItemMeta().getLore());
                    }
                    if (item.getItemMeta().hasEnchants()) {
                        for (Map.Entry<Enchantment, Integer> entry : item.getItemMeta().getEnchants().entrySet()) {
                            plugin.items.set(itemSection + ".enchantments." + entry.getKey().getKey().getKey().toLowerCase(), entry.getValue());
                        }
                    }
                }
                plugin.loadCustomItems();
                user.sendMessage(Messages.prefix + Messages.createItem.replace("%item_name%", itemName));
            }
        }
    }

    public void giveItem(CommandSource source, User user, String itemName, int amount, boolean giveAll) {
        EffectsListener effectsListener = new EffectsListener(plugin);
        CustomItem item = plugin.getItem(itemName);
        if (item == null) {
            source.sendMessage(Messages.prefix + "&cCould not find custom item: &f" + itemName);
        } else {
            XMaterial material = item.getMaterial();
            if (material == null) {
                source.sendMessage("&cInvalid type &f" + plugin.items.getString("items." + itemName + ".type", "") + " &cfor custom item &f" + itemName);
                return;
            }
            ItemBuilder builder = new ItemBuilder(material);
            List<String> lore = new ArrayList<>();
            if (item.getEffects() != null && !item.getEffects().getKeys(false).isEmpty()) {
                for (String effect : item.getEffects().getKeys(false)) {
                    XPotion effectType = XPotion.matchXPotion(effect);
                    if (effectType == null) {
                        source.sendMessage("&cInvalid effect &f" + effect + " &cfor custom item &f" + itemName);
                        continue;
                    }
                    lore.add("&7" + WordUtils.capitalizeFully(effectType.getVanillaName().replace("_", " ")).replace(" ", "_") + " " + numberToRomanNumeral(item.getEffects().getInt(effect)));
                }
            }
            if (item.getEnchantments() != null && !item.getEnchantments().getKeys(false).isEmpty()) {
                for (String enchantment : item.getEnchantments().getKeys(false)) {
                    XEnchantment xEnchantment = XEnchantment.matchXEnchantment(enchantment);
                    if (xEnchantment == null) {
                        source.sendMessage("&cInvalid enchantment &f" + enchantment + " &cfor custom item &f" + itemName);
                        continue;
                    }
                    int level = item.getEnchantments().getInt(enchantment, 1);
                    builder.addEnchantment(xEnchantment.parseEnchantment(), level);
                }
            }
            if (item.getModifiers() != null && !item.getModifiers().getKeys(false).isEmpty()) {
                for (String modifier : item.getModifiers().getKeys(false)) {
                    try {
                        UUID uuid = UUID.randomUUID();
                        Attribute attribute = Attribute.valueOf(modifier.toUpperCase());
                        String name = attribute.name();
                        AttributeModifier attributeModifier;
                        double modifierAmount = item.getModifiers().getDouble(modifier);
                        AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_NUMBER;

                        if (material.isHelmet()) {
                            attributeModifier = new AttributeModifier(uuid, name, modifierAmount, operation, EquipmentSlot.HEAD);
                        } else if (material.isChestplate()) {
                            attributeModifier = new AttributeModifier(uuid, name, modifierAmount, operation, EquipmentSlot.CHEST);
                        } else if (material.isLeggings()) {
                            attributeModifier = new AttributeModifier(uuid, name, modifierAmount, operation, EquipmentSlot.LEGS);
                        } else if (material.isBoots()) {
                            attributeModifier = new AttributeModifier(uuid, name, modifierAmount, operation, EquipmentSlot.FEET);
                        } else {
                            attributeModifier = new AttributeModifier(uuid, name, modifierAmount, operation, EquipmentSlot.HAND);
                        }
                        builder.addAttributeModifier(attribute, attributeModifier);
                    } catch (IllegalArgumentException ignored) {
                        source.sendMessage("&cInvalid modifier &f" + modifier + " &cfor custom item &f" + itemName);
                    }
                }
            }
            if (item.getName() != null) builder.setDisplayName(item.getName());
            if (item.getLore() != null && !item.getLore().isEmpty()) {
                lore.add("");
                lore.addAll(item.getLore());
            }
            if (item.isUnbreakable()) builder.setUnbreakable(true);
            if (item.getItemFlags() != null && !item.getItemFlags().isEmpty()) {
                for (String flag : item.getItemFlags()) {
                    try {
                        ItemFlag itemFlag = ItemFlag.valueOf(flag.toUpperCase());
                        builder.addItemFlag(itemFlag);
                    } catch (IllegalArgumentException ignored) {
                        source.sendMessage("&cInvalid item flag &f" + flag + " &cfor custom item &f" + itemName);
                    }
                }
            }
            builder.setLore(lore);
            ItemStack itemStack = builder.build();
            for (int i = 0; i < amount; i++) {
                if (!user.getInventory().addItem(itemStack).isEmpty()) {
                    user.getLocation().getWorld().dropItemNaturally(user.getLocation(), itemStack);
                }
            }
            if (giveAll) {
                RealmsAPI.broadcast(Messages.prefix + Messages.giveAllItem.replace("%item_name%", itemName)
                        .replace("%item_display_name%", item.getName() != null ? item.getName() : itemName)
                        .replace("%amount%", String.valueOf(amount)));
            } else {
                source.sendMessage(Messages.prefix + Messages.giveItem.replace("%item_name%", itemName)
                        .replace("%item_display_name%", item.getName() != null ? item.getName() : itemName)
                        .replace("%player%", user.getName())
                        .replace("%amount%", String.valueOf(amount)));
                if (!user.getName().equals(source.getName())) {
                    user.sendMessage(Messages.prefix + Messages.giveItem.replace("%item_name%", itemName)
                            .replace("%item_display_name%", item.getName() != null ? item.getName() : itemName)
                            .replace("%player%", user.getName())
                            .replace("%amount%", String.valueOf(amount)));
                }
            }
            effectsListener.removeAllBoundItemEffects(user);
            effectsListener.addItemEffects(user, true, false, true);
        }
    }
}
