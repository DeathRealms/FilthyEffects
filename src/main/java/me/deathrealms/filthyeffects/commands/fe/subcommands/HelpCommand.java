package me.deathrealms.filthyeffects.commands.fe.subcommands;

import me.deathrealms.filthyeffects.Messages;
import me.deathrealms.realmsapi.command.SubCommand;
import me.deathrealms.realmsapi.source.CommandSource;
import net.md_5.bungee.api.chat.ClickEvent;

public class HelpCommand extends SubCommand {

    public HelpCommand() {
        super("help", "fe.command.help", true);
    }

    @Override
    public void run(CommandSource source, String cmd, String label, String[] args) {
        source.sendMessage("&7&m----------------------------------------------------");
        source.sendMessage(Messages.prefix + "&c<> &cRequired &8- &7() Optional");
        source.sendMessage("");
        source.sendComponent(Messages.prefix + "&f/%s help", "fe.command.help", "/%s help",
                newList("&cShows this help menu.", "", "&cClick to perform &f/%s help", "&7fe.command.help"), ClickEvent.Action.RUN_COMMAND, cmd);

        source.sendComponent(Messages.prefix + "&f/%s reload", "fe.command.reload", "/%s reload",
                newList("&cReloads all configuration files.", "", "&cClick to perform &f/%s reload", "&7fe.command.reload"), ClickEvent.Action.RUN_COMMAND, cmd);

        source.sendComponent(Messages.prefix + "&f/%s give &c<player> <item> &7(amount)", "fe.command.give", "/%s give ",
                newList("&cGives a custom item to a player.", "", "&cClick to perform &f/%s give", "&7fe.command.give"), ClickEvent.Action.SUGGEST_COMMAND, cmd);

        source.sendComponent(Messages.prefix + "&f/%s giveall &c<item> &7(amount)", "fe.command.giveall", "/%s giveall ",
                newList("&cGives a custom item to all online players.", "", "&cClick to perform &f/%s giveall", "&7fe.command.giveall"), ClickEvent.Action.SUGGEST_COMMAND, cmd);

        source.sendComponent(Messages.prefix + "&f/%s create &c<item>", "fe.command.create", "/%s create ",
                newList("&cCreates a custom item.", "", "&cClick to perform &f/%s create", "&7fe.command.create"), ClickEvent.Action.SUGGEST_COMMAND, cmd);

        source.sendComponent(Messages.prefix + "&f/%s remove &c<item>", "fe.command.remove", "/%s remove ",
                newList("&cRemoves a custom item.", "", "&cClick to perform &f/%s remove", "&7fe.command.remove"), ClickEvent.Action.SUGGEST_COMMAND, cmd);

        source.sendComponent(Messages.prefix + "&f/%s edit &c<item> <option> <key> &7(value)", "fe.command.edit", "/%s edit ",
                newList("&cEdits a custom item.", "", "&cClick to perform &f/%s edit", "&7fe.command.edit"), ClickEvent.Action.SUGGEST_COMMAND, cmd);

        source.sendComponent(Messages.prefix + "&f/%s info &c<item>", "fe.command.info", "/%s info ",
                newList("&cView info of a custom item.", "", "&cClick to perform &f/%s info", "&7fe.command.info"), ClickEvent.Action.SUGGEST_COMMAND, cmd);

        source.sendComponent(Messages.prefix + "&f/%s list", "fe.command.list", "/%s list",
                newList("&cView a list of all custom item.", "", "&cClick to perform &f/%s list", "&7fe.command.list"), ClickEvent.Action.RUN_COMMAND, cmd);
        source.sendMessage("");
        source.sendMessage(Messages.prefix + "&7&oHover for more info.");
        source.sendMessage("&7&m----------------------------------------------------");
    }
}
