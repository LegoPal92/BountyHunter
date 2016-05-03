package me.legopal92.bountyhunter.utils;

import me.legopal92.bountyhunter.BountyHunter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by legop on 4/29/2016.
 */
public class ConfigManager {

    private File con;
    private FileConfiguration config;

    public static int defaultBounty;

    public ConfigManager() throws Exception{
        con = new File(BountyHunter.getInstance().getDataFolder(), "config.yml");
        if(!con.exists()){
            con.createNewFile();
        }
        config = YamlConfiguration.loadConfiguration(con);
        if (!config.contains("version")){
            config.addDefault("version", 1);
            config.addDefault("mysql.host", "host");
            config.addDefault("mysql.port", 3306);
            config.addDefault("mysql.database", "database");
            config.addDefault("mysql.enabled", false);
            config.addDefault("mysql.user", "user");
            config.addDefault("mysql.pass", "pass");
            config.addDefault("defaultBounty", 50);

            config.options().copyDefaults(true);
            config.save(con);
        }

        defaultBounty = config.getInt("defaultBounty");
    }
}
