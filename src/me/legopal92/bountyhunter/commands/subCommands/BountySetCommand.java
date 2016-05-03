package me.legopal92.bountyhunter.commands.subCommands;

import me.legopal92.bountyhunter.BountyHunter;
import me.legopal92.bountyhunter.utils.Bounty;
import me.legopal92.bountyhunter.utils.PrivateBounty;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by legop on 4/29/2016.
 */
public class BountySetCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 3 ){
            sender.sendMessage("Invalid arguments, correct format: " + getUsage());
            return;
        }

        Player player = (Player) sender;
        Player target = BountyHunter.getInstance().getServer().getPlayer(args[1]);
        PrivateBounty pb = new PrivateBounty(player.getUniqueId(), target.getUniqueId(), Integer.parseInt(args[2]));
        Bounty b = Bounty.getByPlayer(target.getUniqueId());
        b.setPrivateBounty(pb);
        sender.sendMessage("Bounty set.");
    }

    @Override
    public String getDescription() {
        return "Sets a private bounty on a player.";
    }

    @Override
    public String getUsage() {
        return "/bh set <PLAYER> [AMT]";
    }

    @Override
    public String getName() {
        return "set";
    }
}
