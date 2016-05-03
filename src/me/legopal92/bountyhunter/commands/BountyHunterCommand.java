package me.legopal92.bountyhunter.commands;

import com.google.common.collect.Maps;
import me.legopal92.bountyhunter.commands.subCommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

/**
 * Created by legop on 4/29/2016.
 */
public class BountyHunterCommand implements CommandExecutor{

    public static HashMap<String, SubCommand> commands = Maps.newHashMap();

    public BountyHunterCommand(){
        registerCommands(
                new BountyHelpCommand(),
                new BountyCheckCommand(),
                new BountySetCommand(),
                new BountyRemoveCommand(),
                new BountyScoreboardCommand()
        );
    }

    private void registerCommands(SubCommand... cmds){
        for (SubCommand sc : cmds){
            commands.put(sc.getName(), sc);
            BountyHelpCommand.help.put(sc.getName(), sc.getDescription());
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args) {
        if (args.length == 0){
            commands.get("help").execute(commandSender, args);
            return true;
        }
        String c = args[0];
        SubCommand sc = commands.get(c);
        if (sc == null){
            commandSender.sendMessage("Invalid command. Usage /bh for commands.");
            return true;
        }
        sc.execute(commandSender, args);

        return true;
    }
}
