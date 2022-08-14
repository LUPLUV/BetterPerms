package dev.lupluv.bp.bungee.events;

import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import dev.lupluv.bp.bungee.utils.PermsUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TabcompleteEvent implements Listener {

    @EventHandler
    public void onTabComplet(TabCompleteEvent e){
        if(e.getSender() instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) e.getSender();
            String command = e.getCursor();
            String[] args = command.split(" ");
            if(command.startsWith("/hp ") || command.startsWith("/hperm ") || command.startsWith("/hperms ") || command.startsWith("/hypep") || command.startsWith("/hypeperm")
                    || command.startsWith("/hypeperms")){
                if((args.length == 2 && !command.endsWith(" ")) || (args.length == 1 && command.endsWith(" "))) {
                    e.getSuggestions().add("user");
                    e.getSuggestions().add("group");
                    e.getSuggestions().add("info");
                    e.getSuggestions().add("creategroup");
                    e.getSuggestions().add("deletegroup");
                    e.getSuggestions().add("listgroups");
                    e.getSuggestions().add("reload");
                    e.getSuggestions().add("sync");
                }else if(((args.length == 3 && !command.endsWith(" ")) || (args.length == 2 && command.endsWith(" "))) && !args[1].equalsIgnoreCase("info")
                        && !args[1].equalsIgnoreCase("reload") && !args[1].equalsIgnoreCase("sync") && !args[1].equalsIgnoreCase("listgroups")
                        && !args[1].equalsIgnoreCase("creategroup")){
                    if(args[1].equalsIgnoreCase("group") || args[1].equalsIgnoreCase("groups") || args[1].equalsIgnoreCase("deletegroup")){
                        for(IPermissionGroup g : PermsUtil.getGroups()){
                            e.getSuggestions().add(g.getName());
                        }
                    }else if(args[1].equalsIgnoreCase("user")) {
                        ProxyServer.getInstance().getPlayers().forEach(all -> {
                            e.getSuggestions().add(all.getName());
                        });
                    }
                }else if(((args.length == 4 && !command.endsWith(" ")) || (args.length == 3 && command.endsWith(" "))) && !args[1].equalsIgnoreCase("info")
                        && !args[1].equalsIgnoreCase("reload") && !args[1].equalsIgnoreCase("sync") && !args[1].equalsIgnoreCase("listgroups")
                        && !args[1].equalsIgnoreCase("creategroup") && !args[1].equalsIgnoreCase("deletegroup")){
                    if(args[1].equalsIgnoreCase("group")){
                        e.getSuggestions().add("info");
                        e.getSuggestions().add("permission");
                        e.getSuggestions().add("group");
                        e.getSuggestions().add("set");
                        e.getSuggestions().add("listmembers");
                        e.getSuggestions().add("clone");
                    }else if(args[1].equalsIgnoreCase("user")) {
                        e.getSuggestions().add("info");
                        e.getSuggestions().add("permission");
                        e.getSuggestions().add("group");
                    }
                }else if(((args.length == 5 && !command.endsWith(" ")) || (args.length == 4 && command.endsWith(" "))) && !args[1].equalsIgnoreCase("info")
                        && !args[1].equalsIgnoreCase("reload") && !args[1].equalsIgnoreCase("sync") && !args[1].equalsIgnoreCase("listgroups")
                        && !args[1].equalsIgnoreCase("creategroup") && !args[1].equalsIgnoreCase("deletegroup")){
                    if(args[1].equalsIgnoreCase("group")){
                        if(args[3].equalsIgnoreCase("permission") || args[3].equalsIgnoreCase("permissions")){
                            e.getSuggestions().add("add");
                            e.getSuggestions().add("remove");
                            e.getSuggestions().add("check");
                            e.getSuggestions().add("list");
                        }else if(args[3].equalsIgnoreCase("group") || args[3].equalsIgnoreCase("groups")){
                            e.getSuggestions().add("add");
                            e.getSuggestions().add("remove");
                            e.getSuggestions().add("check");
                            e.getSuggestions().add("list");
                        }else if(args[3].equalsIgnoreCase("set")){
                            e.getSuggestions().add("prefix");
                            e.getSuggestions().add("suffix");
                            e.getSuggestions().add("sortID");
                            e.getSuggestions().add("defaultGroup");
                            e.getSuggestions().add("displayName");
                            e.getSuggestions().add("color");
                        }
                    }else if(args[1].equalsIgnoreCase("user")) {
                        if(args[3].equalsIgnoreCase("permission") || args[3].equalsIgnoreCase("permissions")){
                            e.getSuggestions().add("add");
                            e.getSuggestions().add("remove");
                            e.getSuggestions().add("check");
                            e.getSuggestions().add("list");
                        }else if(args[3].equalsIgnoreCase("group") || args[3].equalsIgnoreCase("groups")){
                            e.getSuggestions().add("add");
                            e.getSuggestions().add("remove");
                            e.getSuggestions().add("check");
                            e.getSuggestions().add("list");
                        }
                    }
                }
            }
        }
    }

}
