package me.deathrealms.filthyeffects.commands.fe.subcommands;

import me.deathrealms.filthyeffects.FilthyEffects;
import me.deathrealms.filthyeffects.Messages;
import me.deathrealms.realmsapi.command.SubCommand;
import me.deathrealms.realmsapi.source.CommandSource;

public class ReloadCommand extends SubCommand {
    private FilthyEffects plugin;

    public ReloadCommand(FilthyEffects plugin) {
        super("reload", "fe.command.reload", true);
        this.plugin = plugin;
    }

    @Override
    public void run(CommandSource source, String cmd, String label, String[] args) {
        Messages.load();
        plugin.items.reloadConfig();
        source.sendMessage(Messages.prefix + Messages.reloadConfig);
    }
}
