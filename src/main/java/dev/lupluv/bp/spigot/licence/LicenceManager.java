package dev.lupluv.bp.spigot.licence;

import dev.lupluv.bp.bungee.utils.Strings;
import dev.lupluv.bp.spigot.SpigotMain;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class LicenceManager {
    public String licence;

    private static LicenceManager instance;

    public static LicenceManager getInstance() {
        if(instance == null){
            instance = new LicenceManager();
        }
        return instance;
    }

    public void doOnEnable(){
        try {
            checkLicence(new URL("https://nightpixel.net/licensesystem"));
        } catch (IOException e) {
        }
        if(licence.contains(SpigotMain.getFileManager().getLicence())){
            Bukkit.getConsoleSender().sendMessage(Strings.prefix + "§2Die angegebene Lizenz ist gültig.");
        }else{
            Bukkit.getConsoleSender().sendMessage(Strings.prefix + "§cDie Lizenz ist nicht gültig.");
            Bukkit.getPluginManager().disablePlugin(SpigotMain.getPlugin());
        }
    }

    public void checkLicence(URL url) throws IOException {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder(128000);
        try{
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            int count;
            char[] data = new char[5000];
            while ((count = reader.read(data)) != -1){
                builder.append(data, 0, count);
            }
        }finally {
            reader.close();
        }
        licence = builder.toString();
        Bukkit.getConsoleSender().sendMessage(Strings.prefix + "§7Es wurde eine Lizenz gefunden: " + SpigotMain.getFileManager().getLicence());
    }

}
