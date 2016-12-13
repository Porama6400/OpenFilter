package com.Porama6400.OpenFilter.Filters;

import com.Porama6400.OpenFilter.FilterAction.FilterAction;
import com.Porama6400.OpenFilter.FilterTarget;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import java.util.List;


public class BasicCommandFilter extends Filter {
    private List<String> commands;

    public BasicCommandFilter(List<String> commands, FilterTarget target, List<FilterAction> filterActions) {
        this.commands = commands;
        this.target = target;
        this.actions = filterActions;

    }

    public List<String> getFilteredCommands() {
        return commands;
    }

    @Override
    public void execute(CommandSender sender, String command, List<String> args, Cancellable event) {
        if(sender instanceof Player && target == FilterTarget.CONSOLE) return;
        if(sender instanceof ConsoleCommandSender && target == FilterTarget.PLAYER) return;
        boolean NA = true; //No Action
        for (String cmdf : commands) {
            if(command.equalsIgnoreCase("/" + cmdf)) NA = false;
        }
        if(!NA) {
            for (FilterAction action : getActions()) {
                action.run(sender, command, args, event);
            }
        }
    }
}
