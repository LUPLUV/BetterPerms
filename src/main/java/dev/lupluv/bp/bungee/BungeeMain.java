package dev.lupluv.bp.bungee;

import dev.lupluv.bp.bungee.commands.HypepermsCmd;
import dev.lupluv.bp.bungee.events.TabcompleteEvent;
import dev.lupluv.bp.bungee.licence.LicenceManager;
import dev.lupluv.bp.bungee.utils.FileManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.io.IOException;

public class BungeeMain extends Plugin {

    private static BungeeMain plugin;

    private static FileManager fileManager;

    @Override
    public void onEnable() {

        plugin = this;
        try {
            fileManager = new FileManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LicenceManager.getInstance().doOnEnable();

        PluginManager pm = getProxy().getPluginManager();
        pm.registerCommand(this, new HypepermsCmd("hypeperms", null, "hypeperm", "hp", "hypep", "hperm", "hperms"));
        pm.registerListener(this, new TabcompleteEvent());

    }

    @Override
    public void onDisable() {

    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static BungeeMain getPlugin() {
        return plugin;
    }

    public static LuckPerms getLp() {
        return LuckPermsProvider.get();
    }
}
