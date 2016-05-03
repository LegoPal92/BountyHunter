package me.legopal92.bountyhunter.listeners;

import me.legopal92.bountyhunter.BountyHunter;
import me.legopal92.bountyhunter.economy.Bank;
import me.legopal92.bountyhunter.utils.Bounty;
import me.legopal92.bountyhunter.utils.ConfigManager;
import me.legopal92.bountyhunter.utils.PrivateBounty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by legop on 4/21/2016.
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (Bounty.getByPlayer(event.getPlayer().getUniqueId()) == null){
            new Bounty(event.getPlayer().getUniqueId(), ConfigManager.defaultBounty);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        BountyHunter.getInstance().getHelper().removeBoardView(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if (event.getEntity().getKiller() == null){
            return;
        }
        Bounty b = Bounty.getByPlayer(event.getEntity().getUniqueId());
        Player killer = event.getEntity().getKiller();
        for (PrivateBounty pb : b.getPrivateBounties()){
            Bank.withdraw(pb.getSender(), pb.getAmt());
        }
        Bank.deposit(killer.getUniqueId(), b.getTotalBounty());
        killer.sendMessage("You have collected " + b.getTotalBounty() + " for killing " + event.getEntity().getDisplayName());
        b.getPrivateBounties().clear();
        b.setBounty(ConfigManager.defaultBounty);
    }
}
