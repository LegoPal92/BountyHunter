package me.legopal92.bountyhunter.commands.subCommands;

import me.legopal92.bountyhunter.BountyHunter;
import me.legopal92.bountyhunter.utils.Bounty;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by legop on 4/29/2016.
 */
public class BountyCheckCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1 || args.length != 2){
            sender.sendMessage("Invalid arguments, correct format: " + getUsage());
            return;
        }

        if (args.length == 1){
            int amt = Bounty.getByPlayer(((Player) sender).getUniqueId()).getTotalBounty();

            sender.sendMessage("Your bounty is: " + amt);
            return;
        }
        int amt = Bounty.getByPlayer(BountyHunter.getInstance().getServer().getPlayer(args[1]).getUniqueId()).getTotalBounty();
        sender.sendMessage(args[1] + "'s bounty is: " + amt);
        return;
    }

    @Override
    public String getDescription() {
        return "Check yours, or another player's bounty.";
    }

    @Override
    public String getUsage() {
        return "/bh check OR /bh check <PLAYER>";
    }

    @Override
    public String getName() {
        return "check";
    }
}
