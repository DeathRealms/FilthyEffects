package me.deathrealms.filthyeffects;

import me.deathrealms.realmsapi.files.Serializer;

import static me.deathrealms.realmsapi.utils.ChatUtils.format;

public class Messages {
    private static final transient Messages instance = new Messages();
    public static String prefix = format("&8[&cFE&8] ");
    public static String noPermission = format("&cYou do not have permission to do this.");
    public static String reloadConfig = format("&7You have reloaded the configuration files!");
    public static String giveItem = format("&f%player% &ahas been given &f%amount%x %item_display_name%");
    public static String giveAllItem = format("&aAll online players have been given &f%amount%x %item_display_name%");
    public static String createItem = format("&aCustom item &f%item_name% &ahas been successfully created.");
    public static String removeItem = format("&cCustom item &f%item_name% &chas been successfully removed.");

    public static void load() {
        new Serializer().load(instance, Messages.class, "messages");
    }
}
