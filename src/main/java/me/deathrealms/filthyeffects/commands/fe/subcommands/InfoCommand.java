package me.deathrealms.filthyeffects.commands.fe.subcommands;

import me.deathrealms.filthyeffects.FilthyEffects;
import me.deathrealms.filthyeffects.utils.Utils;
import me.deathrealms.realmsapi.command.SubCommand;
import me.deathrealms.realmsapi.source.CommandSource;

import java.util.ArrayList;
import java.util.List;

public class InfoCommand extends SubCommand {
    private FilthyEffects plugin;

    public InfoCommand(FilthyEffects plugin) {
        super("info", "fe.command.info", true);
        this.plugin = plugin;
    }

    @Override
    public void run(CommandSource source, String cmd, String label, String[] args) {
        Utils utils = new Utils(plugin);
        if (args.length == 0) {
            source.sendMessage("&cPlease enter an item to get info about.");
        } else {
            utils.info(source, args[0]);
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
