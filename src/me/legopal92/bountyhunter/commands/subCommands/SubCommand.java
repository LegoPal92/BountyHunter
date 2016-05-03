package me.legopal92.bountyhunter.commands.subCommands;

import org.bukkit.command.CommandSender;

/**
 * Created by legop on 4/29/2016.
 */
public interface SubCommand {

    public void execute(CommandSender sender, String[] args);

    public String getDescription();

    public String getUsage();

    public String getName();
}
