package dev.lupluv.bp.spigot.utils;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.driver.permission.Permission;
import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.dytanic.cloudnet.ext.bridge.player.ICloudOfflinePlayer;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import dev.lupluv.bp.bungee.utils.PermsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryManager {

    public static Inventory getGroupsInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*6, "§aGruppen");

        if(!CloudNetDriver.getInstance().getPermissionManagement().getGroups().isEmpty()) {
            Collection<IPermissionGroup> groupCollection = CloudNetDriver.getInstance().getPermissionManagement().getGroups();
            Collection<IPermissionGroup> coll = groupCollection.stream().sorted(Comparator.comparing(IPermissionGroup::getSortId)).collect(Collectors.toList());
            if(coll.size() <= 9*5) {
                for (IPermissionGroup g : coll) {
                    Item gItem = new Item(Material.LIME_DYE);
                    gItem.setDisplayName("§a" + g.getName() + "§7 | " + g.getPotency());
                    gItem.setLore(Lore.create("§7Prefix: §r" + g.getPrefix().replace("&", "§")
                            , "§7Suffix: §r" + g.getSuffix().replace("&", "§")
                            , "§7Color: §r" + g.getColor().replace("&", "§") + g.getColor()
                            , "§7Display: §r" + g.getDisplay().replace("&", "§"), "§7SortID: " + g.getSortId(), "§7DefaultGroup: " + g.isDefaultGroup()));
                    inv.addItem(gItem.build());
                }
            }else if(coll.size() <= 9*10){

            }
        }else{
            ItemStack none = Util.createCustomSkull("", "§", Lore.create(""));
            inv.addItem(none);
        }

        Item groups = new Item(Material.GOLD_INGOT);
        groups.setDisplayName("§aGruppen");
        groups.setShiny(true);
        inv.setItem(inv.getSize()-1-8, groups.build());

        Item players = new Item(Material.PLAYER_HEAD);
        players.setDisplayName("§aSpieler");
        players.setSkullOwner(p.getName());
        inv.setItem(inv.getSize()-1-7, players.build());

        return inv;
    }

    public static Inventory getEditGroupMainInventory(Player p, String group, int potency){
        Inventory inv = Bukkit.createInventory(null, 9*3, "§7Gruppen » §a" + group + "§7 | " + potency);

        ItemStack settings = Util.createCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlc" +
                "y5taW5lY3JhZnQubmV0L3RleHR1cmUvZmE0NDk4NmViZmI4NzU3ZGExMzc1Zjc0OTYxMDM0NTRlNm" +
                "ZjMWM1ZWY3M2QwMTNmYWNlNzQ5OWIxMzE4NGY3In19fQ==", "§aSettings", Lore.create("§7Klicke um Einstellungen zu bearbeiten."));
        inv.setItem(11, settings);

        Item perms = new Item(Material.NETHER_STAR);
        perms.setDisplayName("§aPermissions");
        perms.setLore(Lore.create("§7Klicke um die Permissions zu bearbeiten."));
        inv.setItem(13, perms.build());

        ItemStack delete = Util.createCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleH" +
                "R1cmUvY2VmMTE5ZjA4ODUxYTcyYTVmMTBmYmMzMjQ3ZDk1ZTFjMDA2MzYwZDJiNGY0MTJiMjNjZTA1" +
                "NDA5Mjc1NmIwYyJ9fX0=", "§4Löschen", Lore.create("§7Klicke um die Gruppe zu löschen."));
        inv.setItem(15, delete);

        ItemStack back = Util.createCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3Jh" +
                "ZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1Y" +
                "mMxMmE5In19fQ==", "§7Zurück zu allen Gruppen", null);
        inv.setItem(inv.getSize()-1-8, back);

        return inv;
    }

    public static Inventory getEditGroupPermissionsInventory(Player p, String groupName, String notToShowPerm){
        Inventory inv = Bukkit.createInventory(null, 9*6, "§7Gruppen » §a" + groupName + "§7 » Permissions");

        if(notToShowPerm == null) notToShowPerm = "#23489734#23456#643";

        IPermissionGroup group = CloudNetDriver.getInstance().getPermissionManagement().getGroup(groupName);
        for(String g : group.getGroupPermissions().keySet()){
            for(Permission perm : group.getGroupPermissions().get(g)) {
                if(!perm.getName().equalsIgnoreCase(notToShowPerm)) {
                    Item item = new Item(Material.BLAZE_ROD);
                    item.setDisplayName("§b" + g + " §7- §a" + perm.getName());
                    item.setLore(Lore.create("§7Potency: " + perm.getPotency(), "§7TimeOutMillis: " + perm.getTimeOutMillis(), "§7Gruppe: " + g, "§cRechtsklick zum löschen."));
                    inv.addItem(item.build());
                }
            }
        }
        for(Permission perm : group.getPermissions()){
            Item item = new Item(Material.BLAZE_ROD);
            item.setDisplayName("§7- §a" + perm.getName());
            item.setLore(Lore.create("§7Potency: " + perm.getPotency(), "§7TimeOutMillis: " + perm.getTimeOutMillis(), "§cRechtsklick zum löschen."));
            inv.addItem(item.build());
        }

        ItemStack back = Util.createCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3Jh" +
                "ZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1Y" +
                "mMxMmE5In19fQ==", "§7Zurück zur Gruppe", null);
        inv.setItem(inv.getSize()-1-8, back);

        return inv;
    }

    public static Inventory getPlayersInventory(Player p, boolean showOffline){
        Inventory inv = Bukkit.createInventory(null, 9*6, "§aSpieler");

        IPlayerManager pm = BridgePlayerManager.getInstance();
        for(ICloudPlayer online : pm.getOnlinePlayers()){
            Item i = new Item(Material.PLAYER_HEAD);
            i.setDisplayName("§a" + online.getName());
            i.setLore(Lore.create("§7Haupt Gruppe: " + CloudNetDriver.getInstance().getPermissionManagement()
                    .getHighestPermissionGroup(CloudNetDriver.getInstance().getPermissionManagement().getUser(online.getUniqueId())).getName()));
            i.setSkullOwner(online.getName());
            inv.addItem(i.build());
        }

        if(showOffline){
            for(ICloudOfflinePlayer registeredPlayer : pm.getRegisteredPlayers()){
                if(pm.getOnlinePlayer(registeredPlayer.getUniqueId()) == null) {
                    Item i = new Item(Material.PLAYER_HEAD);
                    i.setDisplayName("§a" + registeredPlayer.getName() + " §c(Offline)");
                    i.setLore(Lore.create("§7Haupt Gruppe: " + CloudNetDriver.getInstance().getPermissionManagement()
                            .getHighestPermissionGroup(CloudNetDriver.getInstance().getPermissionManagement().getUser(registeredPlayer.getUniqueId())).getName()));
                    i.setSkullOwner(registeredPlayer.getName());
                    inv.addItem(i.build());
                }
            }
        }

        Item showOfflineItem = new Item(Material.BREWING_STAND);
        if(!showOffline) {
            showOfflineItem.setDisplayName("§aZeige offline Spieler");
            showOfflineItem.setLore(Lore.create("§7Aktuell: §cdeaktiviert"));
        }else{
            showOfflineItem.setDisplayName("§aZeige offline Spieler");
            showOfflineItem.setLore(Lore.create("§7Aktuell: §aaktiviert"));
        }
        inv.setItem(inv.getSize()-1, showOfflineItem.build());

        Item groups = new Item(Material.GOLD_INGOT);
        groups.setDisplayName("§aGruppen");
        inv.setItem(inv.getSize()-1-8, groups.build());

        Item players = new Item(Material.PLAYER_HEAD);
        players.setDisplayName("§aSpieler");
        players.setSkullOwner(p.getName());
        players.setShiny(true);
        inv.setItem(inv.getSize()-1-7, players.build());

        return inv;
    }

    public static Inventory getEditPlayerMainInventory(Player p, String player){
        Inventory inv = Bukkit.createInventory(null, 9*3, "§7Spieler » §a" + player);

        ItemStack groups = Util.createCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlc" +
                "y5taW5lY3JhZnQubmV0L3RleHR1cmUvZmE0NDk4NmViZmI4NzU3ZGExMzc1Zjc0OTYxMDM0NTRlNm" +
                "ZjMWM1ZWY3M2QwMTNmYWNlNzQ5OWIxMzE4NGY3In19fQ==", "§aGruppen", Lore.create("§7Klicke um die Gruppen zu bearbeiten."));
        inv.setItem(11, groups);

        Item perms = new Item(Material.NETHER_STAR);
        perms.setDisplayName("§aPermissions");
        perms.setLore(Lore.create("§7Klicke um die Permissions zu bearbeiten."));
        inv.setItem(13, perms.build());

        return inv;
    }

    public static Inventory getEditPlayerPermissionsInventory(Player p, String playerName, String notToShowPerm){
        Inventory inv = Bukkit.createInventory(null, 9*6, "§7Spieler » §a" + playerName + "§7 » Permissions");

        if(notToShowPerm == null) notToShowPerm = "#23489734#23456#643";

        IPermissionUser user = PermsUtil.getPermissionUser(playerName);
        for(String g : user.getGroupPermissions().keySet()){
            for(Permission perm : user.getGroupPermissions().get(g)) {
                if(!perm.getName().equalsIgnoreCase(notToShowPerm)) {
                    Item item = new Item(Material.BLAZE_ROD);
                    item.setDisplayName("§b" + g + " §7- §a" + perm.getName());
                    item.setLore(Lore.create("§7Potency: " + perm.getPotency(), "§7TimeOutMillis: " + perm.getTimeOutMillis(), "§7Gruppe: " + g, "§cRechtsklick zum löschen."));
                    inv.addItem(item.build());
                }
            }
        }
        for(Permission perm : user.getPermissions()){
            Item item = new Item(Material.BLAZE_ROD);
            item.setDisplayName("§7- §a" + perm.getName());
            item.setLore(Lore.create("§7Potency: " + perm.getPotency(), "§7TimeOutMillis: " + perm.getTimeOutMillis(), "§cRechtsklick zum löschen."));
            inv.addItem(item.build());
        }

        ItemStack back = Util.createCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3Jh" +
                "ZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1Y" +
                "mMxMmE5In19fQ==", "§7Zurück zur Gruppe", null);
        inv.setItem(inv.getSize()-1-8, back);

        return inv;
    }

}
