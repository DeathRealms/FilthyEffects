package me.deathrealms.filthyeffects.commands.fe;

import me.deathrealms.filthyeffects.FilthyEffects;
import me.deathrealms.filthyeffects.Messages;
import me.deathrealms.filthyeffects.commands.fe.subcommands.*;
import me.deathrealms.realmsapi.command.Command;
import me.deathrealms.realmsapi.source.CommandSource;

public class FECommand extends Command {

    public FECommand(FilthyEffects plugin) {
        super("filthyeffects", "fe.command", true, "fe");
        setNoPermissionMessage(Messages.noPermission);
        addSubCommands(new HelpCommand(), new ReloadCommand(plugin), new GiveCommand(plugin), new GiveAllCommand(plugin),
                new CreateCommand(plugin), new RemoveCommand(plugin), new EditCommand(plugin), new InfoCommand(plugin),
                new ListCommand(plugin));
    }

    @Override
    public void run(CommandSource source, String label, String[] args) {
        source.sendMessage("&cUnknown command. Type &7\"/%s help\" &cfor help.", label);
    }
}
