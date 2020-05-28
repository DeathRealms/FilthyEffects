package me.deathrealms.filthyeffects.listeners;

import me.deathrealms.filthyeffects.FilthyEffects;
import me.deathrealms.filthyeffects.utils.Utils;
import me.deathrealms.realmsapi.RealmsAPI;
import me.deathrealms.realmsapi.user.User;
import me.deathrealms.realmsapi.xseries.XPotion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class EffectsListener implements Listener {
    private final FilthyEffects plugin;

    public EffectsListener(FilthyEffects plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        User user = RealmsAPI.getUser(event.getPlayer());
        ItemStack droppedItem = event.getItemDrop().getItemStack();
        if (droppedItem.getItemMeta().hasLore()) {
            if (!user.getItemInMainHand().isSimilar(droppedItem)) {
                removeAllBoundItemEffects(user);
                addItemEffects(user, true, false, false);
            }
        }
    }

    @EventHandler
    public void onPlayerInvClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            User user = RealmsAPI.getUser((Player) event.getPlayer());
            removeAllBoundItemEffects(user);
            addItemEffects(user, true, false, true);
        }
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {
        User user = RealmsAPI.getUser(event.getPlayer());
        if (event.getBrokenItem().getItemMeta().hasLore()) {
            removeAllBoundItemEffects(user);
            addItemEffects(user, true, false, true);
        }
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        User user = RealmsAPI.getUser(event.getPlayer());
        ItemStack previousItem = user.getItemInMainHand();
        if (previousItem != null && previousItem.hasItemMeta() && previousItem.getItemMeta().hasLore()) {
            removeAllBoundItemEffects(user);
            addItemEffects(user, true, false, false);
        }
        ItemStack heldItem = user.getInventory().getItem(event.getNewSlot());
        if (heldItem != null && heldItem.getItemMeta().hasLore()) {
            List<String> heldItemLore = heldItem.getItemMeta().getLore();
            addItemEffects(user, false, false, true, heldItemLore);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() == null) return;
        if (!(event.getEntity() instanceof Player)) return;
        User user = RealmsAPI.getUser(event.getEntity().getUniqueId());
        ItemStack item = event.getItem().getItemStack();
        if (item.getItemMeta().hasLore()) {
            RealmsAPI.runTask(false, () -> addItemEffects(user, false, false, true), 1);
        }
    }

    @EventHandler
    public void onPlayerInvClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
            User user = RealmsAPI.getUser(event.getWhoClicked().getUniqueId());
            RealmsAPI.runTask(false, () -> {
                removeAllBoundItemEffects(user);
                addItemEffects(user, true, false, true);
            }, 1);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        User user = RealmsAPI.getUser(event.getPlayer());
        removeAllBoundItemEffects(user);
        addItemEffects(user, true, false, true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        User user = RealmsAPI.getUser(event.getPlayer());
        removeAllBoundItemEffects(user);
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().getName().equals(plugin.getName())) {
            for (User user : RealmsAPI.getOnlineUsers()) {
                removeAllBoundItemEffects(user);
            }
        }
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        if (event.getPlugin().getName().equals(plugin.getName())) {
            for (User user : RealmsAPI.getOnlineUsers()) {
                addItemEffects(user, true, false, true);
            }
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        User user = RealmsAPI.getUser(event.getPlayer());
        RealmsAPI.runTask(false, () -> {
            removeAllBoundItemEffects(user);
            addItemEffects(user, true, false, true);
        }, 1);
    }

    public void addItemEffects(User user, boolean addArmor, boolean addInventory, boolean addHeld) {
        addItemEffects(user, addArmor, addInventory, addHeld, null);
    }

    private void addItemEffects(User user, boolean addArmor, boolean addInventory, boolean addHeld, List<String> itemLore) {
        if (addArmor) {
            ItemStack[] armorList = user.getInventory().getArmorContents();
            for (ItemStack armor : armorList) {
                if (armor != null && armor.hasItemMeta() && armor.getItemMeta().hasLore()) {
                    List<String> equippedItemLore = armor.getItemMeta().getLore();
                    addAllBoundItemEffects(user, equippedItemLore);
                }
            }
        }
        if (addInventory) {
            if (itemLore != null) {
                addAllBoundItemEffects(user, itemLore);
            } else {
                ItemStack[] invList = user.getInventory().getContents();
                for (ItemStack item : invList) {
                    if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                        List<String> invItemLore = item.getItemMeta().getLore();
                        addAllBoundItemEffects(user, invItemLore);
                    }
                }
            }
        } else if (addHeld) {
            if (itemLore != null) {
                addAllBoundItemEffects(user, itemLore);
            } else {
                ItemStack heldItem = user.getItemInMainHand();
                if (heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
                    List<String> heldItemLore = heldItem.getItemMeta().getLore();
                    addAllBoundItemEffects(user, heldItemLore);
                }
            }
        }
    }

    private void addAllBoundItemEffects(User user, List<String> heldItemLore) {
        for (XPotion potion : XPotion.VALUES) {
            for (String alias : potion.getAliases()) {
                int loreLine = Utils.listContainsString(heldItemLore, potion.name());
                int loreLineAlias = Utils.listContainsString(heldItemLore, alias);
                if (loreLine != -1) {
                    String[] effect = heldItemLore.get(loreLine).split(" ");
                    if (effect.length == 2) {
                        int level = -1;
                        try {
                            level = Utils.romanNumeralToNumber(ChatColor.stripColor(effect[1]));
                        } catch (NumberFormatException ignored) {
                        }
                        if (level > 0 && level <= Integer.MAX_VALUE) {
                            addItemEffects(user, XPotion.matchXPotion(ChatColor.stripColor(effect[0])).parsePotionEffectType(), level);
                        }
                    }
                }
                if (loreLineAlias != -1) {
                    String[] effect = heldItemLore.get(loreLineAlias).split(" ");
                    if (effect.length == 2) {
                        int level = -1;
                        try {
                            level = Utils.romanNumeralToNumber(ChatColor.stripColor(effect[1]));
                        } catch (NumberFormatException ignored) {
                        }
                        if (level > 0 && level <= Integer.MAX_VALUE) {
                            addItemEffects(user, XPotion.matchXPotion(ChatColor.stripColor(effect[0])).parsePotionEffectType(), level);
                        }
                    }
                }
            }
        }
    }

    private void addItemEffects(User user, PotionEffectType type, int level) {
        user.getBase().addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, level - 1, false));
        user.getBase().setMetadata(type.toString(), new FixedMetadataValue(plugin, true));
    }

    private void removeItemEffects(User user, PotionEffectType type) {
        user.getBase().removeMetadata(type.toString(), plugin);
        user.getBase().removePotionEffect(type);
    }

    public void removeAllBoundItemEffects(User user) {
        for (XPotion potion : XPotion.VALUES) {
            PotionEffectType type = potion.parsePotionEffectType();
            if (type == null) continue;
            if (user.getBase().hasMetadata(type.toString())) {
                removeItemEffects(user, type);
            }
        }
    }
}
