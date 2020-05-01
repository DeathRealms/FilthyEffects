package me.deathrealms.filthyeffects.commands.fe.subcommands;

import me.deathrealms.filthyeffects.FilthyEffects;
import me.deathrealms.filthyeffects.utils.Utils;
import me.deathrealms.realmsapi.RealmsAPI;
import me.deathrealms.realmsapi.command.SubCommand;
import me.deathrealms.realmsapi.source.CommandSource;
import me.deathrealms.realmsapi.user.User;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

public class GiveCommand extends SubCommand {
    private final FilthyEffects plugin;

    public GiveCommand(FilthyEffects plugin) {
        super("give", "fe.command.give", true);
        this.plugin = plugin;
    }

    @Override
    public void run(CommandSource source, String cmd, String label, String[] args) {
        Utils utils = new Utils(plugin);
        if (args.length == 0) {
            source.sendMessage("&cPlease enter a player to give an item to.");
        } else if (args.length == 1) {
            source.sendMessage("&cPlease provide an item to give.");
        } else if (args.length == 2) {
            User target = RealmsAPI.getUser(args[0]);
            if (target.getBase() == null) {
                source.sendMessage("&cPlayer not found");
            } else {
                utils.giveItem(source, target, args[1], 1, false);
            }
        } else {
            User target = RealmsAPI.getUser(args[0]);
            if (target.getBase() == null) {
                source.sendMessage("&cPlayer not found");
            } else {
                if (!NumberUtils.isNumber(args[2])) {
                    source.sendMessage("&cPlease enter a valid amount.");
                } else {
                    utils.giveItem(source, target, args[1], Integer.parseInt(args[2]), false);
                }
            }
        }
    }

    @Override
    public List<String> tabCompleteOptions(CommandSource source, String[] args) {
        if (args.length == 1) {
            return getOnlinePlayers(source);
        } else if (args.length == 2) {
            return new ArrayList<>(plugin.items.getConfigSection("items").getKeys(false));
        } else {
            return emptyList();
        }
    }
}
