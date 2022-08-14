package dev.lupluv.bp.bungee.utils;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

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
            Configuration cfg = YamlConfiguration.getProvider(YamlConfiguration.class).load(configFile);
            cfg.set("License", "hypemania2873valid04054");
            YamlConfiguration.getProvider(YamlConfiguration.class).save(cfg, configFile);
        }
    }

    public String getLicence() {
        File configFile = new File("plugins//BetterPerms//config.yml");
        Configuration cfg = null;
        try {
            cfg = YamlConfiguration.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cfg.getString("License");
    }

}
