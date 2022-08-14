package dev.lupluv.bp.spigot.commands;

import dev.lupluv.bp.spigot.utils.InventoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CpguiCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cpgui.open")){
                p.sendMessage("Keine Rechte");
            }else{
                if(strings.length == 0){
                    p.openInventory(InventoryManager.getGroupsInventory(p));
                }
            }
        }
        return false;
    }
}
