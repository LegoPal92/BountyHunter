package me.legopal92.bountyhunter.utils.dataStore;

import com.google.common.collect.Lists;
import me.legopal92.bountyhunter.BountyHunter;
import me.legopal92.bountyhunter.utils.Bounty;
import me.legopal92.bountyhunter.utils.DataStore;
import me.legopal92.bountyhunter.utils.PrivateBounty;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Created by legop on 4/23/2016.
 */
public class BountyFile implements DataStore{

    private File configFile;
    private File pConfigFile;
    private FileConfiguration config;
    private FileConfiguration pConfig;

    public  BountyFile() throws Exception{
        configFile = new File(BountyHunter.getInstance().getDataFolder(), "bounties.yml");
        pConfigFile = new File(BountyHunter.getInstance().getDataFolder(), "private_bounties.yml");
        if (!configFile.exists()){
            configFile.createNewFile();
        }
        if (!pConfigFile.exists()){
            pConfigFile.createNewFile();
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        pConfig = YamlConfiguration.loadConfiguration(pConfigFile);
    }

    public List<Bounty> loadAllBounties(){
        List<Bounty> bounties = Lists.newArrayList();
        for (String s : config.getKeys(false)){
            UUID u = UUID.fromString(s);
            int amt = config.getInt(s);
            Bounty b = new Bounty(u, amt);
            bounties.add(b);
        }
        return bounties;
    }

    @Override
    public void saveAllBounties(List<Bounty> bounties) {
        for (Bounty b : bounties){
            config.set(b.getPlayer().toString(), b.getBounty());
        }
        try {
            config.save(configFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveAllPrivateBounties(List<PrivateBounty> bounties) {
        for (PrivateBounty pb : bounties){
            pConfig.set(pb.getTarget() + "." + pb.getSender(), pb.getAmt());
        }
        try {
            pConfig.save(pConfigFile);
        } catch (Exception e){
            e.printStackTrace();;
        }
    }

    @Override
    public List<PrivateBounty> loadAllPrivateBounties() {
        List<PrivateBounty> bounties = null;
        for (String s : pConfig.getKeys(false)){
            ConfigurationSection cs = pConfig.getConfigurationSection(s);
            UUID u = UUID.fromString(s);
            for (String s2 : cs.getKeys(false)){
                UUID u2 = UUID.fromString(s2);
                int amt = cs.getInt(s2);
                PrivateBounty pb = new PrivateBounty(u2, u, amt);
                bounties.add(pb);
            }
        }
        return bounties;

    }

    @Override
    public void saveBounty(Bounty b) {
        config.set(b.getPlayer().toString(), b.getBounty());

    try {
        config.save(configFile);
    } catch (Exception e){
        e.printStackTrace();
    }
    }

    @Override
    public void savePrivateBounty(PrivateBounty pb) {
        pConfig.set(pb.getTarget() + "." + pb.getSender(), pb.getAmt());

        try {
            pConfig.save(pConfigFile);
        } catch (Exception e){
            e.printStackTrace();;
        }
    }

    @Override
    public void removePrivateBounty(PrivateBounty b){
        pConfig.set(b.getTarget() + "." + b.getSender(), "");
    }
}
