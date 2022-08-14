package dev.lupluv.bp.bungee.commands;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.driver.permission.Permission;
import de.dytanic.cloudnet.driver.permission.PermissionUserGroupInfo;
import dev.lupluv.bp.bungee.utils.PermsUtil;
import dev.lupluv.bp.bungee.utils.Strings;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Collection;

public class HypepermsCmd extends Command {

    public HypepermsCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof ProxiedPlayer){
            /*
              - Main Commands -
              /hp user <user>
              /hp group <group>
              /hp info
              /hp creategroup <group>
              /hp deletegroup <group>
              /hp listgroups

              - User Sub Commands -
              /hp user <user> info
              /hp user <user> permission <add/remove/check/clear/list> [node]
              /hp user <user> group <add/remove/check/clear/list> [node]

              - Group Sub Commands -
              /hp group <group> info
              /hp group <group> permission <add/remove/check/clear/list> [node]
              /hp group <group> group <add/remove/check/clear/list> [node]
              /hp group <group> set <prefix/suffix/sortid/color/displayname/name> <value>
              /hp group <group> listmembers
              /hp group <group> clone <name>
            */

            ProxiedPlayer p = (ProxiedPlayer) commandSender;

            if(p.hasPermission("hypemania.perms.use")){
                if(strings.length == 0){
                    p.sendMessage(new TextComponent(Strings.prefix + "§eRunning §cBetterPerms v0.0.1"));
                    p.sendMessage(new TextComponent(Strings.prefix + "Use §6/hp help §7to view available commands."));
                }else{
                    if(strings.length == 1){
                        if(strings[0].equalsIgnoreCase("help")){
                            p.sendMessage(new TextComponent(Strings.prefix + "- Main Commands -"));
                            p.sendMessage(new TextComponent("§c> §6/hp user <user>"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group>"));
                            p.sendMessage(new TextComponent("§c> §6/hp info"));
                            p.sendMessage(new TextComponent("§c> §6/hp creategroup <group>"));
                            p.sendMessage(new TextComponent("§c> §6/hp deletegroup <group>"));
                            p.sendMessage(new TextComponent("§c> §6/hp listgroups"));
                            p.sendMessage(new TextComponent("§c> §6/hp reload"));
                        }else if(strings[0].equalsIgnoreCase("user")){
                            p.sendMessage(new TextComponent(Strings.prefix + "- User Sub Commands -"));
                            p.sendMessage(new TextComponent("§c> §6/hp user <user> info"));
                            p.sendMessage(new TextComponent("§c> §6/hp user <user> permission"));
                            p.sendMessage(new TextComponent("§c> §6/hp user <user> group"));
                        }else if(strings[0].equalsIgnoreCase("group") || strings[0].equalsIgnoreCase("groups")){
                            p.sendMessage(new TextComponent(Strings.prefix + "- Group Sub Commands -"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group> info"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group> permission"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group> group"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group> set"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group> listmembers"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group> clone <name>"));
                        }else if(strings[0].equalsIgnoreCase("info")){
                            p.sendMessage(new TextComponent(Strings.prefix + "§eRunning §cBetterPerms v0.0.1"));
                            p.sendMessage(new TextComponent(Strings.prefix + "by LUPLUV"));
                            p.sendMessage(new TextComponent(Strings.prefix + "Use §6/hp help §7to view available commands."));
                        }else if(strings[0].equalsIgnoreCase("listgroups")){
                            p.sendMessage(new TextComponent(Strings.prefix + "- Grouplist -"));
                            p.sendMessage(new TextComponent("§c   > §7(name, sortid, prefix)"));
                            for(IPermissionGroup g : PermsUtil.getGroups()){
                                p.sendMessage(new TextComponent(" §c- §6" + g.getName() + " §7- §e" + g.getSortId() + "§7 - §6\"" + g.getPrefix() + "\""));
                            }
                        }else if(strings[0].equalsIgnoreCase("reload") || strings[0].equalsIgnoreCase("rl")){
                            p.sendMessage(new TextComponent(Strings.prefix + "§cReloading §7Permission Groups and Users on all Nodes..."));
                            CloudNetDriver.getInstance().getPermissionManagement().reload();
                            p.sendMessage(new TextComponent(Strings.prefix + "§7The Reload is finished §asuccessful§7!"));
                        }else if(strings[0].equalsIgnoreCase("sync")){
                            p.sendMessage(new TextComponent(Strings.prefix + "§aStarting §7syncronisation with LuckPerms API"));
                            PermsUtil.syncWithLP();
                            p.sendMessage(new TextComponent(Strings.prefix + "§7The Syncronisation is finished §asuccessful"));
                        }
                    }else if(strings.length == 2){
                        if(strings[0].equalsIgnoreCase("user")){
                            p.sendMessage(new TextComponent(Strings.prefix + "- User Sub Commands -"));
                            p.sendMessage(new TextComponent("§c> §6/hp user <user> info"));
                            p.sendMessage(new TextComponent("§c> §6/hp user <user> permission"));
                            p.sendMessage(new TextComponent("§c> §6/hp user <user> group"));
                        }else if(strings[0].equalsIgnoreCase("group") || strings[0].equalsIgnoreCase("groups")){
                            p.sendMessage(new TextComponent(Strings.prefix + "- Group Sub Commands -"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group> info"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group> permission"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group> group"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group> set"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group> listmembers"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group> clone <name>"));
                        }
                    }else if(strings.length == 3){
                        if(strings[0].equalsIgnoreCase("user")){
                            IPermissionUser user = PermsUtil.getPermissionUser(strings[1]);
                            if(user != null){
                                if(strings[2].equalsIgnoreCase("info")){
                                    p.sendMessage(new TextComponent(Strings.prefix + "- User Info §6" + user.getName() + "§7 -"));
                                    p.sendMessage(new TextComponent("§c- §6UUID: §7" + user.getUniqueId().toString()));
                                    if(ProxyServer.getInstance().getPlayer(user.getName()) != null) {
                                        p.sendMessage(new TextComponent("§c- §6Status: §aonline"));
                                    }else{
                                        p.sendMessage(new TextComponent("§c- §6Status: §coffline"));
                                    }
                                    p.sendMessage(new TextComponent("§c- §6Groups:"));
                                    for(IPermissionGroup group : PermsUtil.getGroups(user)){
                                        p.sendMessage(new TextComponent("   §c> §7" + group.getName()));
                                    }
                                    p.sendMessage(new TextComponent("§c- §6Data:"));
                                    p.sendMessage(new TextComponent("   §c> §ePrefix: §7\"" + CloudNetDriver.getInstance().getPermissionManagement().getHighestPermissionGroup(user)
                                            .getPrefix() + "\""));
                                    p.sendMessage(new TextComponent("   §c> §eSuffix: §7\"" + CloudNetDriver.getInstance().getPermissionManagement().getHighestPermissionGroup(user)
                                            .getSuffix() + "\""));
                                    p.sendMessage(new TextComponent("   §c> §ePrimary Group: §7\"" + CloudNetDriver.getInstance().getPermissionManagement().getHighestPermissionGroup(user)
                                            .getName() + "\""));
                                }else if(strings[2].equalsIgnoreCase("permission") || strings[2].equalsIgnoreCase("permissions")) {
                                    p.sendMessage(new TextComponent(Strings.prefix + "- Inherted Permissions Sub Commands -"));
                                    p.sendMessage(new TextComponent("§c> §6add <node>"));
                                    p.sendMessage(new TextComponent("§c> §6remove <node>"));
                                    p.sendMessage(new TextComponent("§c> §6check <node>"));
                                    p.sendMessage(new TextComponent("§c> §6list"));
                                }else if(strings[2].equalsIgnoreCase("group") || strings[2].equalsIgnoreCase("groups")) {
                                    p.sendMessage(new TextComponent(Strings.prefix + "- Inherted Groups Sub Commands -"));
                                    p.sendMessage(new TextComponent("§c> §6add <group>"));
                                    p.sendMessage(new TextComponent("§c> §6remove <group>"));
                                    p.sendMessage(new TextComponent("§c> §6check <group>"));
                                    p.sendMessage(new TextComponent("§c> §6list"));
                                }else{
                                    p.sendMessage(new TextComponent(Strings.prefix + "- User Sub Commands -"));
                                    p.sendMessage(new TextComponent("§c> §6/hp user <user> info"));
                                    p.sendMessage(new TextComponent("§c> §6/hp user <user> permission"));
                                    p.sendMessage(new TextComponent("§c> §6/hp user <user> group"));
                                }
                            }else{
                                p.sendMessage(new TextComponent(Strings.playerNotFound));
                            }
                        }else if(strings[0].equalsIgnoreCase("group") || strings[0].equalsIgnoreCase("groups")){
                            IPermissionGroup group = CloudNetDriver.getInstance().getPermissionManagement().getGroup(strings[1]);
                            if(group != null){
                                if(strings[2].equalsIgnoreCase("info")){
                                    p.sendMessage(new TextComponent(Strings.prefix + "- Group Info §6" + group.getName() + "§7 -"));
                                    p.sendMessage(new TextComponent("§c- §6Display Name: §7" + group.getDisplay()));
                                    p.sendMessage(new TextComponent("§c- §6Sort ID: §7" + group.getSortId()));
                                    p.sendMessage(new TextComponent("§c- §6Inherted Groups:"));
                                    for(String g : group.getGroups()){
                                        p.sendMessage(new TextComponent("   §c> §7" + g));
                                    }
                                    p.sendMessage(new TextComponent("§c- §6Data:"));
                                    p.sendMessage(new TextComponent("   §c> §ePrefix: §7\"" + group.getPrefix() + "\""));
                                    p.sendMessage(new TextComponent("   §c> §eSuffix: §7\"" + group.getSuffix() + "\""));
                                    p.sendMessage(new TextComponent("   §c> §eColor: §7\"" + group.getColor() + "\""));
                                    p.sendMessage(new TextComponent("   §c> §eDisplay Name: §7\"" + group.getDisplay() + "\""));
                                    p.sendMessage(new TextComponent("   §c> §eSort ID: §7\"" + group.getSortId() + "\""));
                                }else if(strings[2].equalsIgnoreCase("listmembers")){
                                    p.sendMessage(new TextComponent(Strings.prefix + "- Memberlist §6" + group.getName() + "§7 -"));
                                    for(IPermissionUser u : PermsUtil.getAllUsers()) {
                                        if(u.inGroup(group.getName())) {
                                            p.sendMessage(new TextComponent(" §c- §6" + u.getName()));
                                        }
                                    }
                                }else if(strings[2].equalsIgnoreCase("permission") || strings[2].equalsIgnoreCase("permissions")) {
                                    p.sendMessage(new TextComponent(Strings.prefix + "- Inherted Permissions Sub Commands -"));
                                    p.sendMessage(new TextComponent("§c> §6add <node>"));
                                    p.sendMessage(new TextComponent("§c> §6remove <node>"));
                                    p.sendMessage(new TextComponent("§c> §6check <node>"));
                                    p.sendMessage(new TextComponent("§c> §6list"));
                                }else if(strings[2].equalsIgnoreCase("group") || strings[2].equalsIgnoreCase("groups")) {
                                    p.sendMessage(new TextComponent(Strings.prefix + "- Inherted Groups Sub Commands -"));
                                    p.sendMessage(new TextComponent("§c> §6add <group>"));
                                    p.sendMessage(new TextComponent("§c> §6remove <group>"));
                                    p.sendMessage(new TextComponent("§c> §6check <group>"));
                                    p.sendMessage(new TextComponent("§c> §6list"));
                                }else{
                                    p.sendMessage(new TextComponent(Strings.prefix + "- Group Sub Commands -"));
                                    p.sendMessage(new TextComponent("§c> §6/hp group <group> info"));
                                    p.sendMessage(new TextComponent("§c> §6/hp group <group> permission"));
                                    p.sendMessage(new TextComponent("§c> §6/hp group <group> group"));
                                    p.sendMessage(new TextComponent("§c> §6/hp group <group> set"));
                                    p.sendMessage(new TextComponent("§c> §6/hp group <group> listmembers"));
                                    p.sendMessage(new TextComponent("§c> §6/hp group <group> clone <name>"));
                                }
                            }
                        }else{
                            p.sendMessage(new TextComponent(Strings.prefix + "- Main Commands -"));
                            p.sendMessage(new TextComponent("§c> §6/hp user <user>"));
                            p.sendMessage(new TextComponent("§c> §6/hp group <group>"));
                            p.sendMessage(new TextComponent("§c> §6/hp info"));
                            p.sendMessage(new TextComponent("§c> §6/hp creategroup <group>"));
                            p.sendMessage(new TextComponent("§c> §6/hp deletegroup <group>"));
                            p.sendMessage(new TextComponent("§c> §6/hp listgroups"));
                            p.sendMessage(new TextComponent("§c> §6/hp reload"));
                        }
                    }else if(strings.length == 4){
                        if(strings[0].equalsIgnoreCase("user")){
                            IPermissionUser user = PermsUtil.getPermissionUser(strings[1]);
                            if(user != null){
                                if(strings[2].equalsIgnoreCase("permission") || strings[2].equalsIgnoreCase("permissions")) {
                                    if(strings[3].equalsIgnoreCase("list")){
                                        p.sendMessage(new TextComponent(Strings.prefix + "- Inherted Permissionslist §6" + user.getName() + " -"));
                                        p.sendMessage(new TextComponent("   §c> §7(node, potency, time, group)"));
                                        for(Permission permission : user.getPermissions()){
                                            p.sendMessage(new TextComponent(" §c- §6" + permission.getName() + " §7- §e" + permission.getPotency() + "§7 - §6"
                                                    + permission.getTimeOutMillis()));
                                        }
                                        for(String s : user.getGroupPermissions().keySet()){
                                            for(Permission permission : user.getGroupPermissions().get(s)){
                                                p.sendMessage(new TextComponent(" §c- §6" + permission.getName() + " §7- §e" + permission.getPotency() + "§7 - §6"
                                                        + permission.getTimeOutMillis() + "§7 - §b" + s));
                                            }
                                        }
                                    }else{
                                        p.sendMessage(new TextComponent(Strings.prefix + "- Inherted Permissions Sub Commands -"));
                                        p.sendMessage(new TextComponent("§c> §6add <group>"));
                                        p.sendMessage(new TextComponent("§c> §6remove <group>"));
                                        p.sendMessage(new TextComponent("§c> §6check <group>"));
                                        p.sendMessage(new TextComponent("§c> §6list"));
                                    }
                                }else if(strings[2].equalsIgnoreCase("group") || strings[2].equalsIgnoreCase("groups")) {
                                    if(strings[3].equalsIgnoreCase("list")){
                                        p.sendMessage(new TextComponent(Strings.prefix + "- Inherted Groupslist §6" + user.getName() + " -"));
                                        p.sendMessage(new TextComponent("   §c> §7(name, primary, prefix)"));
                                        for(IPermissionGroup group : PermsUtil.getGroups(user)){
                                            if(group.getName().equalsIgnoreCase(CloudNetDriver.getInstance().getPermissionManagement().getHighestPermissionGroup(user).getName())) {
                                                p.sendMessage(new TextComponent(" §c- §6" + group.getName() + " §7- §atrue §7- §6\"" + group.getPrefix() + "\""));
                                            }else{
                                                p.sendMessage(new TextComponent(" §c- §6" + group.getName() + " §7- §cfalse §7- §6\"" + group.getPrefix() + "\""));
                                            }
                                        }
                                    }else{
                                        p.sendMessage(new TextComponent(Strings.prefix + "- Inherted Groups Sub Commands -"));
                                        p.sendMessage(new TextComponent("§c> §6add <group>"));
                                        p.sendMessage(new TextComponent("§c> §6remove <group>"));
                                        p.sendMessage(new TextComponent("§c> §6check <group>"));
                                        p.sendMessage(new TextComponent("§c> §6list"));
                                    }
                                }else{
                                    p.sendMessage(new TextComponent(Strings.prefix + "- User Sub Commands -"));
                                    p.sendMessage(new TextComponent("§c> §6/hp user <user> info"));
                                    p.sendMessage(new TextComponent("§c> §6/hp user <user> permission"));
                                    p.sendMessage(new TextComponent("§c> §6/hp user <user> group"));
                                }
                            }else{
                                p.sendMessage(new TextComponent(Strings.playerNotFound));
                            }
                        }
                    }else if(strings.length == 5){
                        if(strings[0].equalsIgnoreCase("user")){
                            IPermissionUser user = PermsUtil.getPermissionUser(strings[1]);
                            if(user != null){
                                if(strings[2].equalsIgnoreCase("permission") || strings[2].equalsIgnoreCase("permissions")) {
                                    if(strings[3].equalsIgnoreCase("add")){
                                        String perm = strings[4];
                                        if(!PermsUtil.checkForPermission(user, perm)){
                                            user.addPermission(perm);
                                            p.sendMessage(new TextComponent(Strings.prefix + "§aThe Permission was added!"));
                                        }else{
                                            p.sendMessage(new TextComponent(Strings.prefix + "§cThe User already has this Permission!"));
                                        }
                                    }else if(strings[3].equalsIgnoreCase("remove")){
                                        String perm = strings[4];
                                        if(PermsUtil.checkForPermission(user, perm)){
                                            user.removePermission(perm);
                                            p.sendMessage(new TextComponent(Strings.prefix + "§aThe Permission was removed!"));
                                        }else{
                                            p.sendMessage(new TextComponent(Strings.prefix + "§cThe User doesnt have this Permission!"));
                                        }
                                    }else if(strings[3].equalsIgnoreCase("check")){
                                        String perm = strings[4];
                                        if(PermsUtil.checkForPermission(user, perm)){
                                            p.sendMessage(new TextComponent(Strings.prefix + "§cThe User has this Permission!"));
                                        }else{
                                            p.sendMessage(new TextComponent(Strings.prefix + "§cThe User doesnt have this Permission!"));
                                        }
                                    }
                                }else if(strings[2].equalsIgnoreCase("group") || strings[2].equalsIgnoreCase("groups")) {
                                    if(strings[3].equalsIgnoreCase("add")){
                                        String perm = strings[4];
                                        if(!PermsUtil.checkForPermission(user, perm)){
                                            user.addPermission(perm);
                                            p.sendMessage(new TextComponent(Strings.prefix + "§aThe Permission was added!"));
                                        }else{
                                            p.sendMessage(new TextComponent(Strings.prefix + "§cThe User already has this Permission!"));
                                        }
                                    }else if(strings[3].equalsIgnoreCase("remove")){
                                        String perm = strings[4];
                                        if(PermsUtil.checkForPermission(user, perm)){
                                            user.removePermission(perm);
                                            p.sendMessage(new TextComponent(Strings.prefix + "§aThe Permission was removed!"));
                                        }else{
                                            p.sendMessage(new TextComponent(Strings.prefix + "§cThe User doesnt have this Permission!"));
                                        }
                                    }else if(strings[3].equalsIgnoreCase("check")){
                                        String perm = strings[4];
                                        if(PermsUtil.checkForPermission(user, perm)){
                                            p.sendMessage(new TextComponent(Strings.prefix + "§cThe User has this Permission!"));
                                        }else{
                                            p.sendMessage(new TextComponent(Strings.prefix + "§cThe User doesnt have this Permission!"));
                                        }
                                    }
                                }else{
                                    p.sendMessage(new TextComponent(Strings.prefix + "- User Sub Commands -"));
                                    p.sendMessage(new TextComponent("§c> §6/hp user <user> info"));
                                    p.sendMessage(new TextComponent("§c> §6/hp user <user> permission"));
                                    p.sendMessage(new TextComponent("§c> §6/hp user <user> group"));
                                }
                            }else{
                                p.sendMessage(new TextComponent(Strings.playerNotFound));
                            }
                        }
                    }
                }
            }else{
                p.sendMessage(new TextComponent(Strings.noPerms));
            }

        }
    }
}























