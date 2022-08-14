package dev.lupluv.bp.spigot.events;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.driver.permission.Permission;
import dev.lupluv.bp.bungee.utils.PermsUtil;
import dev.lupluv.bp.spigot.utils.InventoryManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getType() == Material.AIR) return;
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        Material mat = item.getType();
        Inventory inv = e.getClickedInventory();
        String title = e.getView().getTitle();
        if(title.equalsIgnoreCase("§aGruppen")){
            e.setCancelled(true);
            if(mat == Material.LIME_DYE){
                String name = item.getItemMeta().getDisplayName().replace("§a", "").replace("§7 | ", "");
                IPermissionGroup group = null;
                for(IPermissionGroup g : CloudNetDriver.getInstance().getPermissionManagement().getGroups()){
                    if(name.equalsIgnoreCase(g.getName() + g.getPotency())){
                        group = g;
                    }
                }
                p.openInventory(InventoryManager.getEditGroupMainInventory(p, group.getName(), group.getPotency()));
            }else if(mat == Material.PLAYER_HEAD){
                p.openInventory(InventoryManager.getPlayersInventory(p, false));
            }
        }else if(title.startsWith("§7Gruppen » §a") && !title.endsWith("§7 » Permissions")){
            e.setCancelled(true);
            String name = title.replace("§7Gruppen » §a", "").replace("§7 | ", "");
            IPermissionGroup group = null;
            if(mat == Material.NETHER_STAR){
                for(IPermissionGroup g : CloudNetDriver.getInstance().getPermissionManagement().getGroups()){
                    if(name.equalsIgnoreCase(g.getName() + g.getPotency())){
                        group = g;
                    }
                }
                p.openInventory(InventoryManager.getEditGroupPermissionsInventory(p, group.getName(), null));
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§7Zurück zu allen Gruppen")){
                p.openInventory(InventoryManager.getGroupsInventory(p));
            }
        }else if(title.endsWith("§7 » Permissions") && title.startsWith("§7Gruppen » §a")){
            e.setCancelled(true);
            String groupName = title.replace("§7 » Permissions", "").replace("§7Gruppen » §a", "");
            IPermissionGroup group = CloudNetDriver.getInstance().getPermissionManagement().getGroup(groupName);
            if(mat == Material.BLAZE_ROD){
                if(e.getAction() == InventoryAction.PICKUP_HALF || e.getClick() == ClickType.RIGHT) {
                    if (!item.getItemMeta().getDisplayName().contains("§b")) {
                        String permName = item.getItemMeta().getDisplayName().replace("§7- §a", "");
                        group.removePermission(permName);
                        CloudNetDriver.getInstance().getPermissionManagement().updateGroup(group);
                        CloudNetDriver.getInstance().getPermissionManagement().reload();
                        p.openInventory(InventoryManager.getEditGroupPermissionsInventory(p, group.getName(), null));
                    }else{
                        String permName = item.getItemMeta().getDisplayName().replace("§b", "").replace(" §7- §a", "");
                        for(String s : group.getGroupPermissions().keySet()){
                            for(Permission ps : group.getGroupPermissions().get(s)){
                                if(permName.equalsIgnoreCase(s + ps.getName())){
                                    group.removePermission(s, ps.getName());
                                }
                            }
                        }
                        CloudNetDriver.getInstance().getPermissionManagement().updateGroup(group);
                        CloudNetDriver.getInstance().getPermissionManagement().reload();
                        p.openInventory(InventoryManager.getEditGroupPermissionsInventory(p, group.getName(), null));
                    }
                }
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§7Zurück zur Gruppe")){
                p.openInventory(InventoryManager.getEditGroupMainInventory(p, group.getName(), group.getPotency()));
            }
        }else if(title.equalsIgnoreCase("§aSpieler")){
            e.setCancelled(true);
            if(mat == Material.BREWING_STAND){
                if(item.getItemMeta().getLore().get(0).equalsIgnoreCase("§7Aktuell: §cdeaktiviert")){
                    p.openInventory(InventoryManager.getPlayersInventory(p, true));
                }else if(item.getItemMeta().getLore().get(0).equalsIgnoreCase("§7Aktuell: §aaktiviert")){
                    p.openInventory(InventoryManager.getPlayersInventory(p, false));
                }
            }else if(mat == Material.GOLD_INGOT){
                p.openInventory(InventoryManager.getGroupsInventory(p));
            }else if(mat == Material.PLAYER_HEAD){
                if(!item.getItemMeta().getDisplayName().equalsIgnoreCase("§aSpieler")){
                    p.openInventory(InventoryManager.getEditPlayerMainInventory(p, item.getItemMeta().getDisplayName().replace("§a", "")
                            .replace(" §c(Offline)", "")));
                }
            }
        }else if(title.startsWith("§7Spieler » §a") && !title.endsWith("§7 » Permissions")){
            e.setCancelled(true);
            String name = title.replace("§7Gruppen » §a", "").replace("§7 | ", "");
            IPermissionUser u = null;
            if(mat == Material.NETHER_STAR){
                u = PermsUtil.getPermissionUser(title.replace("§7Spieler » §a", ""));
                p.openInventory(InventoryManager.getEditPlayerPermissionsInventory(p, u.getName(), null));
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§7Zurück zu allen Spielern")){
                p.openInventory(InventoryManager.getPlayersInventory(p, false));
            }
        }else if(title.endsWith("§7 » Permissions") && title.startsWith("§7Spieler » §a")){
            e.setCancelled(true);
            String playerName = title.replace("§7 » Permissions", "").replace("§7Gruppen » §a", "");
            IPermissionUser user = PermsUtil.getPermissionUser(playerName);
            if(mat == Material.BLAZE_ROD){
                if(e.getAction() == InventoryAction.PICKUP_HALF || e.getClick() == ClickType.RIGHT) {
                    if (!item.getItemMeta().getDisplayName().contains("§b")) {
                        String permName = item.getItemMeta().getDisplayName().replace("§7- §a", "");
                        user.removePermission(permName);
                        CloudNetDriver.getInstance().getPermissionManagement().updateUser(user);
                        CloudNetDriver.getInstance().getPermissionManagement().reload();
                        p.openInventory(InventoryManager.getEditPlayerPermissionsInventory(p, user.getName(), null));
                    }else{
                        String permName = item.getItemMeta().getDisplayName().replace("§b", "").replace(" §7- §a", "");
                        for(String s : user.getGroupPermissions().keySet()){
                            for(Permission ps : user.getGroupPermissions().get(s)){
                                if(permName.equalsIgnoreCase(s + ps.getName())){
                                    user.removePermission(s, ps.getName());
                                }
                            }
                        }
                        CloudNetDriver.getInstance().getPermissionManagement().updateUser(user);
                        CloudNetDriver.getInstance().getPermissionManagement().reload();
                        p.openInventory(InventoryManager.getEditPlayerPermissionsInventory(p, user.getName(), null));
                    }
                }
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§7Zurück zum Spieler")){
                p.openInventory(InventoryManager.getEditPlayerMainInventory(p, user.getName()));
            }
        }
    }

}
