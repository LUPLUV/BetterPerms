package dev.lupluv.bp.bungee.utils;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.driver.permission.Permission;
import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.dytanic.cloudnet.ext.bridge.player.ICloudOfflinePlayer;
import dev.lupluv.bp.bungee.BungeeMain;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.query.QueryOptions;

import java.util.ArrayList;
import java.util.List;

public class PermsUtil {

    public static IPermissionUser getPermissionUser(String name){
        IPermissionUser permissionUser = CloudNetDriver.getInstance().getPermissionManagement().getFirstUser(name);
        return permissionUser;
    }

    public static IPermissionGroup getPermissionHighestGroup(IPermissionUser user){
        return CloudNetDriver.getInstance().getPermissionManagement().getHighestPermissionGroup(user);
    }

    public static List<IPermissionGroup> getGroups(){
        return CloudNetDriver.getInstance().getPermissionManagement().getGroups().stream().toList();
    }

    public static List<IPermissionUser> getAllUsers(){
        List<IPermissionUser> ul = new ArrayList<>();
        for(ICloudOfflinePlayer p : BridgePlayerManager.getInstance().getRegisteredPlayers()){
            ul.add(CloudNetDriver.getInstance().getPermissionManagement().getUser(p.getUniqueId()));
        }
        return ul;
    }

    public static List<IPermissionGroup> getGroups(IPermissionUser user) {
        return CloudNetDriver.getInstance().getPermissionManagement().getGroups(user).stream().toList();
    }

    public static boolean checkForPermission(IPermissionUser user, String permission){
        return user.hasPermission(permission).asBoolean();
    }

    public static boolean checkForPermission(IPermissionUser user, String permission, String group){
        return user.hasPermission(group, new Permission(permission)).asBoolean();
    }

    public static boolean checkForPermission(IPermissionGroup pgroup, String permission){
        return pgroup.hasPermission(permission).asBoolean();
    }

    public static boolean checkForPermission(IPermissionGroup pgroup, String permission, String group){
        return pgroup.hasPermission(group, new Permission(permission)).asBoolean();
    }

    public static void syncWithLP(){
        syncGroups();
        syncPlayers();
    }

    public static void syncGroups(){
        PermsUtil.getAllUsers().forEach(allUsers ->{
            getGroups(allUsers).forEach(allG ->{
                allUsers.removeGroup(allG.getName());
            });
            CloudNetDriver.getInstance().getPermissionManagement().updateUser(allUsers);
        });
        PermsUtil.getGroups().forEach(cpGroups ->{
            CloudNetDriver.getInstance().getPermissionManagement().deleteGroup(cpGroups);
        });
        CloudNetDriver.getInstance().getPermissionManagement().reload();
        List<Group> lpGroups = BungeeMain.getLp().getGroupManager().getLoadedGroups().stream().toList();
        for(Group all : lpGroups){
            IPermissionGroup cpGroup = CloudNetDriver.getInstance().getPermissionManagement().addGroup(all.getName(), getLuckPermsWeight(all));
            cpGroup.setSortId(10001-cpGroup.getPotency());
            cpGroup.setPrefix(getLuckPermsPrefix(all));
            cpGroup.setSuffix(getLuckPermsSuffix(all));
            if(all.getDisplayName() != null) {
                cpGroup.setDisplay(all.getDisplayName());
            }
            cpGroup.setColor("&7");
            cpGroup.setDefaultGroup(all.getName().equalsIgnoreCase("default"));
            for(String perm : getPermissions(all)){
                cpGroup.addPermission(perm);
            }
            CloudNetDriver.getInstance().getPermissionManagement().updateGroup(cpGroup);
        }
        CloudNetDriver.getInstance().getPermissionManagement().reload();
    }

    public static void syncPlayers(){
        List<User> lpUsers = BungeeMain.getLp().getUserManager().getLoadedUsers().stream().toList();
        for(User all : lpUsers){
            IPermissionUser cpUser = CloudNetDriver.getInstance().getPermissionManagement().getUser(all.getUniqueId());
            for(String perm : getPermissions(all)){
                cpUser.addPermission(perm);
            }
            all.getInheritedGroups(QueryOptions.defaultContextualOptions()).forEach(inG ->{
                cpUser.addGroup(inG.getName());
            });
            CloudNetDriver.getInstance().getPermissionManagement().updateUser(cpUser);
        }
        CloudNetDriver.getInstance().getPermissionManagement().reload();
    }

    public static String getLuckPermsPrefix(Group group){
        String pr = "";
        for(Node node : group.getNodes()){
            if(node.getType() == NodeType.PREFIX){
                pr = node.getKey().replace("prefix." + getLuckPermsWeight(group) + ".", "");
            }
        }
        return pr;
    }

    public static String getLuckPermsSuffix(Group group){
        String pr = "";
        for(Node node : group.getNodes()){
            if(node.getType() == NodeType.SUFFIX){
                pr = node.getKey().replace("suffix." + getLuckPermsWeight(group) + ".", "");
            }
        }
        return pr;
    }

    public static int getLuckPermsWeight(Group group){
        int weigth = 69;
        for(Node node : group.getNodes()){
            if(node.getType() == NodeType.WEIGHT){
                weigth = Integer.parseInt(node.getKey().replace("weight.", ""));
            }
        }
        return weigth;
    }

    public static List<String> getPermissions(Group group){
        List<String> l = new ArrayList<>();
        for(Node node : group.getNodes()){
            if(node.getType() == NodeType.PERMISSION){
                l.add(node.getKey());
            }
        }
        return l;
    }

    public static List<String> getPermissions(User user){
        List<String> l = new ArrayList<>();
        for(Node node : user.getNodes()){
            if(node.getType() == NodeType.PERMISSION){
                l.add(node.getKey());
            }
        }
        return l;
    }

    public static List<String> getParentGroups(Group group){
        List<String> l = new ArrayList<>();
        for(Node node : group.getNodes()){
            if(node.getType() == NodeType.INHERITANCE){
                l.add(node.getKey());
            }
        }
        return l;
    }

}
