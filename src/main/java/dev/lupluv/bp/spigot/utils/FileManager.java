package dev.lupluv.bp.spigot.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    public FileManager() throws IOException {
        loadFiles();
    }

    public void loadFiles() throws IOException {
        File folder = new File("plugins//BetterPerms");
        File configFile = new File("plugins//BetterPerms//config.yml");
        if(!folder.exists()) folder.mkdir();
        if(!configFile.exists()){
            configFile.createNewFile();
            FileConfiguration cfg = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(configFile);
            cfg.set("License", "hypemania2873valid04054");
            cfg.save(configFile);
        }
    }

    public String getLicence(){
        File configFile = new File("plugins//BetterPerms//config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
        return cfg.getString("License");
    }

}
