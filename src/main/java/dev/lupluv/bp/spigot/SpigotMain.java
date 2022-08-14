package dev.lupluv.bp.spigot;

import dev.lupluv.bp.spigot.commands.CpguiCmd;
import dev.lupluv.bp.spigot.events.ClickEvent;
import dev.lupluv.bp.spigot.licence.LicenceManager;
import dev.lupluv.bp.spigot.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SpigotMain extends JavaPlugin {

    private static SpigotMain plugin;
    private static FileManager fileManager;

    @Override
    public void onEnable() {

        plugin = this;

        getCommand("cpgui").setExecutor(new CpguiCmd());
        try {
            fileManager = new FileManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LicenceManager.getInstance().doOnEnable();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ClickEvent(), this);

    }

    @Override
    public void onDisable() {

    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static SpigotMain getPlugin() {
        return plugin;
    }
}
