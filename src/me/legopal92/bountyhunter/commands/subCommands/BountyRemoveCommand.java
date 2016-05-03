package me.legopal92.bountyhunter.commands.subCommands;

import me.legopal92.bountyhunter.BountyHunter;
import me.legopal92.bountyhunter.utils.Bounty;
import me.legopal92.bountyhunter.utils.PrivateBounty;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by legop on 4/29/2016.
 */
public class BountyRemoveCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2){
            sender.sendMessage("Invalid arguments, correct format: " + getUsage());
            return;
        }

        Player player = (Player) sender;
        Player target = BountyHunter.getInstance().getServer().getPlayer(args[1]);
        Bounty b = Bounty.getByPlayer(target.getUniqueId());
        PrivateBounty pb = b.getPrivateBounty(player.getUniqueId());
        b.removePrivate(pb);
        player.sendMessage("You have removed your bounty on " + args[1]);

    }

    @Override
    public String getDescription() {
        return "Removes a private bounty that you set on a player.";
    }

    @Override
    public String getUsage() {
        return "/bh remove <PLAYER>";
    }

    @Override
    public String getName() {
        return "remove";
    }
}
