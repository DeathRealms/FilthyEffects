package me.deathrealms.filthyeffects.commands.fe.subcommands;

import me.deathrealms.filthyeffects.FilthyEffects;
import me.deathrealms.filthyeffects.Messages;
import me.deathrealms.realmsapi.command.SubCommand;
import me.deathrealms.realmsapi.source.CommandSource;
import me.deathrealms.realmsapi.user.User;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.configuration.ConfigurationSection;

public class ListCommand extends SubCommand {
    private FilthyEffects plugin;

    public ListCommand(FilthyEffects plugin) {
        super("list", "fe.command.list", true);
        this.plugin = plugin;
    }

    @Override
    public void run(CommandSource source, String cmd, String label, String[] args) {
        ConfigurationSection itemList = plugin.items.getConfigSection("items");
        if (itemList == null || itemList.getKeys(false).isEmpty()) {
            source.sendMessage(Messages.prefix + "&cThere are no custom items available.");
        } else {
            source.sendMessage("&7&m----------------------------------------------------");
            source.sendMessage("&cCustom Items:");
            for (String items : itemList.getKeys(false)) {
                if (source.isPlayer()) {
                    User user = source.getUser();
                    user.sendComponent("  - " + items, "/%s info " + items, newList("&cClick to perform &f/%s info " + items), ClickEvent.Action.RUN_COMMAND, cmd);
                } else {
                    source.sendMessage("  - " + items);
                }
            }
            source.sendMessage("&7&m----------------------------------------------------");
        }
    }
}
