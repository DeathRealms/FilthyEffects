package me.deathrealms.filthyeffects.commands.fe.subcommands;

import me.deathrealms.filthyeffects.FilthyEffects;
import me.deathrealms.filthyeffects.Messages;
import me.deathrealms.realmsapi.command.CommandType;
import me.deathrealms.realmsapi.command.SubCommand;
import me.deathrealms.realmsapi.source.CommandSource;
import me.deathrealms.realmsapi.xseries.XSound;

public class ReloadCommand extends SubCommand {
    private final FilthyEffects plugin;

    public ReloadCommand(FilthyEffects plugin) {
        super("reload", "fe.command.reload", CommandType.PLAYER_AND_CONSOLE);
        this.plugin = plugin;
    }

    @Override
    public void run(CommandSource source, String cmd, String label, String[] args) {
        Messages.load();
        plugin.items.reloadConfig();
        source.sendMessage(Messages.prefix + Messages.reloadConfig);
        if (source.isPlayer()) {
            source.getUser().playSound(XSound.BLOCK_NOTE_BLOCK_PLING);
        }
    }
}
