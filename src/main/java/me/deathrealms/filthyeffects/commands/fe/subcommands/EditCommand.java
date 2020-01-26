package me.deathrealms.filthyeffects.commands.fe.subcommands;

import me.deathrealms.filthyeffects.FilthyEffects;
import me.deathrealms.filthyeffects.utils.Utils;
import me.deathrealms.realmsapi.XEnchantment;
import me.deathrealms.realmsapi.XPotion;
import me.deathrealms.realmsapi.command.SubCommand;
import me.deathrealms.realmsapi.source.CommandSource;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditCommand extends SubCommand {
    private FilthyEffects plugin;

    public EditCommand(FilthyEffects plugin) {
        super("edit", "fe.command.edit", true);
        this.plugin = plugin;
    }

    @Override
    public void run(CommandSource source, String cmd, String label, String[] args) {
        Utils utils = new Utils(plugin);
        if (args.length == 0) {
            source.sendMessage("&cPlease enter a custom item to edit.");
        } else if (args.length == 1) {
            if (plugin.getItem(args[0]) == null) {
                source.sendMessage("&cCustom item &f" + args[0] + " &cdoes not exist.");
            } else {
                source.sendMessage("&cPlease enter an option to edit.");
            }
        } else if (args.length == 2) {
            switch (args[1].toLowerCase()) {
                case "addeffect": {
                    source.sendMessage("&cPlease enter an effect to add.");
                    break;
                }
                case "addenchant": {
                    source.sendMessage("&cPlease enter an enchantment to add.");
                    break;
                }
                case "addmodifier": {
                    source.sendMessage("&cPlease enter a modifier to add.");
                    break;
                }
                case "addflag": {
                    source.sendMessage("&cPlease enter an item flag to add.");
                    break;
                }
                case "setname": {
                    source.sendMessage("&cPlease enter a name to set.");
                    break;
                }
                case "setunbreakable": {
                    source.sendMessage("&cPlease enter true or false.");
                    break;
                }
                case "removeeffect": {
                    source.sendMessage("&cPlease enter an effect to remove.");
                    break;
                }
                case "removeenchant": {
                    source.sendMessage("&cPlease enter an enchantment to remove.");
                    break;
                }
                case "removemodifier": {
                    source.sendMessage("&cPlease enter a modifier to remove.");
                    break;
                }
                case "removeflag": {
                    source.sendMessage("&cPlease enter an item flag to remove.");
                    break;
                }
                default: {
                    source.sendMessage("&cPlease enter a valid option.");
                }
            }
        } else if (args[1].equalsIgnoreCase("setname")) {
            utils.editItem(source, args[0], args[1], String.join(" ", Arrays.copyOfRange(args, 2, args.length)), null);
        } else if (args.length == 3) {
            utils.editItem(source, args[0], args[1], args[2], null);
        } else {
            utils.editItem(source, args[0], args[1], args[2], args[3]);
        }
    }

    @Override
    public List<String> tabCompleteOptions(CommandSource source, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(plugin.items.getConfigSection("items").getKeys(false));
        } else if (args.length == 2) {
            return newList("addeffect", "addenchant", "addmodifier", "addflag", "setname", "setunbreakable", "removeeffect", "removeenchant", "removemodifier",
                    "removeflag");
        }
        switch (args[1].toLowerCase()) {
            case "addeffect": {
                List<String> effects = new ArrayList<>();
                for (XPotion effectType : XPotion.VALUES) {
                    effects.add(effectType.getVanillaName().toLowerCase());
                }
                return effects;
            }
            case "addenchant": {
                List<String> enchants = new ArrayList<>();
                for (XEnchantment enchantment : XEnchantment.VALUES) {
                    enchants.add(enchantment.getVanillaName().toLowerCase());
                }
                return enchants;
            }
            case "addmodifier": {
                List<String> modifiers = new ArrayList<>();
                for (Attribute attribute : Attribute.values()) {
                    modifiers.add(attribute.name().toLowerCase());
                }
                return modifiers;
            }
            case "addflag": {
                List<String> currentFlags = plugin.items.getStringList("items." + args[0] + ".flags");
                List<String> flags = new ArrayList<>();
                for (ItemFlag flag : ItemFlag.values()) {
                    if (currentFlags != null && !currentFlags.isEmpty()) {
                        if (currentFlags.contains(flag.name().toLowerCase())) continue;
                    }
                    flags.add(flag.name().toLowerCase());
                }
                return flags;
            }
            case "setunbreakable": {
                return newList("true", "false");
            }
            case "removeeffect": {
                ConfigurationSection effects = plugin.items.getConfigSection("items." + args[0] + ".effects");
                if (effects != null) {
                    return new ArrayList<>(effects.getKeys(false));
                } else {
                    return emptyList();
                }
            }
            case "removeenchant": {
                ConfigurationSection enchantments = plugin.items.getConfigSection("items." + args[0] + ".enchantments");
                if (enchantments != null) {
                    return new ArrayList<>(enchantments.getKeys(false));
                } else {
                    return emptyList();
                }
            }
            case "removemodifier": {
                ConfigurationSection modifiers = plugin.items.getConfigSection("items." + args[0] + ".modifiers");
                if (modifiers != null) {
                    return new ArrayList<>(modifiers.getKeys(false));
                } else {
                    return emptyList();
                }
            }
            case "removeflag": {
                List<String> flags = plugin.items.getStringList("items." + args[0] + ".flags");
                if (flags != null && !flags.isEmpty()) {
                    return flags;
                } else {
                    return emptyList();
                }
            }
            default: {
                return emptyList();
            }
        }
    }
}
