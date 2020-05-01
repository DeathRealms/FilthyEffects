package me.deathrealms.filthyeffects.commands.fe.subcommands;

import me.deathrealms.filthyeffects.FilthyEffects;
import me.deathrealms.filthyeffects.utils.Utils;
import me.deathrealms.realmsapi.command.SubCommand;
import me.deathrealms.realmsapi.user.User;

public class CreateCommand extends SubCommand {
    private final FilthyEffects plugin;

    public CreateCommand(FilthyEffects plugin) {
        super("create", "fe.command.create", false);
        this.plugin = plugin;
    }

    @Override
    public void run(User user, String cmd, String label, String[] args) {
        Utils utils = new Utils(plugin);
        if (args.length == 0) {
            user.sendMessage("&cPlease enter the name of the custom item you wish to create.");
        } else {
            utils.createItem(user, args[0]);
        }
    }
}
