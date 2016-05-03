package me.legopal92.bountyhunter;

import me.legopal92.bountyhunter.commands.BountyHunterCommand;
import me.legopal92.bountyhunter.listeners.PlayerListener;
import me.legopal92.bountyhunter.utils.*;
import me.legopal92.bountyhunter.utils.dataStore.BountyFile;
import me.legopal92.bountyhunter.utils.dataStore.SQLManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

/**
 * Created by legop on 4/20/2016.
 */
public class BountyHunter extends JavaPlugin {

    private static BountyHunter bh;
    private Economy econ;
    private DataStore ds;
    private ScoreBoardHelper helper;

    @Override
    public void onEnable(){
        bh = this;

        if (!getDataFolder().exists()){
            try{
                getDataFolder().mkdirs();
                File conf = new File(getDataFolder(), "config.yml");
                if (!conf.exists()){
                    conf.createNewFile();
                    new ConfigManager();
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if(getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("Vault not found, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (getConfig().getBoolean("mysql.enabled")){
            if (getConfig().getString("mysql.host").equalsIgnoreCase("host") || getConfig().getString("user").equalsIgnoreCase("user")){
                System.out.println("Mysql not properly configured, disabling plugin.");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
            ds = new SQLManager(getConfig().getString("mysql.host"), getConfig().getInt("mysql.port"), getConfig().getString("mysql.user"), getConfig().getString("mysql.pass"), getConfig().getString("mysql.database"));

        } else {
            try {
                ds = new BountyFile();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        ds.loadAllBounties();
        for (PrivateBounty pb : (List<PrivateBounty>) ds.loadAllPrivateBounties()){
            Bounty b  = Bounty.getByPlayer(pb.getTarget());
            b.setPrivateBounty(pb);
        }

        getServer().getPluginManager().registerEvents(new PlayerListener(), getInstance());
        getCommand("bh").setExecutor(new BountyHunterCommand());
        helper = new ScoreBoardHelper();
        setupEconomy();
    }

    @Override
    public void onDisable(){
        getServer().getScheduler().runTaskAsynchronously(getInstance(), new Runnable() {
            @Override
            public void run() {
                getDataStore().saveAllBounties(Bounty.getBounties());
                for (Bounty b : Bounty.getBounties()){
                    getDataStore().saveAllPrivateBounties(b.getPrivateBounties());
                }
            }
        });

    }

    public static BountyHunter getInstance(){ return bh; }

    public Economy getEconomy(){ return econ; }

    public DataStore getDataStore(){ return ds; }

    public ScoreBoardHelper getHelper(){ return helper; }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> serviceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if(serviceProvider == null) {
            return false;
        }
        this.econ = serviceProvider.getProvider();
        return true;
    }
}
