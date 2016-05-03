package me.legopal92.bountyhunter.commands.subCommands;

import com.google.common.collect.Maps;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

/**
 * Created by legop on 4/29/2016.
 */
public class BountyHelpCommand implements SubCommand {
    public static HashMap<String, String> help = Maps.newHashMap();

    @Override
    public void execute(CommandSender sender, String[] args) {
        for (String s : help.keySet()){
            sender.sendMessage(s + " : " + help.get(s));
        }
    }

    @Override
    public String getDescription() {
        return "Display the BountyHunter help command.";
    }

    @Override
    public String getUsage() {
        return "/bh";
    }

    @Override
    public String getName() {
        return "help";
    }
}
