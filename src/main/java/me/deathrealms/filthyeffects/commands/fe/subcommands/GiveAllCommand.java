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

public class GiveAllCommand extends SubCommand {
    private final FilthyEffects plugin;

    public GiveAllCommand(FilthyEffects plugin) {
        super("giveall", "fe.command.giveall", true);
        this.plugin = plugin;
    }

    @Override
    public void run(CommandSource source, String cmd, String label, String[] args) {
        Utils utils = new Utils(plugin);
        if (args.length == 0) {
            source.sendMessage("&cPlease provide an item to give.");
        } else if (args.length == 1) {
            if (RealmsAPI.getOnlinePlayers().isEmpty()) {
                source.sendMessage("&cThere are no players currently online.");
            } else {
                for (User target : RealmsAPI.getOnlineUsers()) {
                    utils.giveItem(source, target, args[0], 1, true);
                }
            }
        } else {
            if (RealmsAPI.getOnlinePlayers().isEmpty()) {
                source.sendMessage("&cThere are no players currently online.");
            } else {
                for (User target : RealmsAPI.getOnlineUsers()) {
                    if (!NumberUtils.isNumber(args[1])) {
                        source.sendMessage("&cPlease enter a valid amount.");
                    } else {
                        utils.giveItem(source, target, args[0], Integer.parseInt(args[1]), true);
                    }
                }
            }
        }
    }

    @Override
    public List<String> tabCompleteOptions(CommandSource source, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(plugin.items.getConfigSection("items").getKeys(false));
        } else {
            return emptyList();
        }
    }
}
