package com.Porama6400.OpenFilter.Filters;

import com.Porama6400.OpenFilter.FilterAction.FilterAction;
import com.Porama6400.OpenFilter.FilterTarget;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.permissions.Permission;

import java.util.List;


public class BasicCommandFilter extends Filter {
    boolean blockTabComplete = false;
    private List<String> commands;

    public BasicCommandFilter(List<String> commands, Boolean blockTabComplete, FilterTarget target, List<FilterAction> filterActions, List<Permission> perms) {
        this.commands = commands;
        this.blockTabComplete = blockTabComplete;
        this.target = target;
        this.actions = filterActions;
        if (perms != null)
            this.bypassPermissions.addAll(perms);
        registerPermissions();
        updateTabCompleter();
    }

    public boolean isBlockTabComplete() {
        return blockTabComplete;
    }

    public void setBlockTabComplete(boolean blockTabComplete) {
        this.blockTabComplete = blockTabComplete;
    }

    public void updateTabCompleter() {
        if (blockTabComplete) {
            for (String cmd : commands) {
                PluginCommand plugincmd = Bukkit.getPluginCommand(cmd);
                if(plugincmd!=null) {
                    plugincmd.setTabCompleter(null);
                }
                else{
                    //USE PROTOCOL LIB INSTEAD
                }
            }
        }
    }

    public List<String> getFilteredCommands() {
        return commands;
    }

    @Override
    public void execute(CommandSender sender, String command, List<String> args, Cancellable event) {
        if (sender instanceof Player && target == FilterTarget.CONSOLE) return;
        if (sender instanceof ConsoleCommandSender && target == FilterTarget.PLAYER) return;
        boolean NA = true; //No Action
        for (String cmdf : commands) {
            if (command.equalsIgnoreCase("/" + cmdf)) NA = false;
        }
        if (!NA) {
            for (FilterAction action : getActions()) {
                action.run(sender, command, args, event);
            }
        }
    }
}
