package me.legopal92.bountyhunter.commands.subCommands;

import me.legopal92.bountyhunter.BountyHunter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by legop on 4/29/2016.
 */
public class BountyScoreboardCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1){
            sender.sendMessage("Invalid arguments, correct format: " + getUsage());
            return;
        }
        if (!BountyHunter.getInstance().getHelper().getViewers().contains(((Player) sender).getUniqueId())){
            BountyHunter.getInstance().getHelper().showBoard(((Player) sender).getUniqueId());
            return;
        }
        BountyHunter.getInstance().getHelper().removeBoardView(((Player) sender).getUniqueId());

    }

    @Override
    public String getDescription() {
        return "Display a scoreboard showing a leaderboard of the top five players.";
    }

    @Override
    public String getUsage() {
        return "/bh scoreboard";
    }

    @Override
    public String getName() {
        return "scoreboard";
    }
}
