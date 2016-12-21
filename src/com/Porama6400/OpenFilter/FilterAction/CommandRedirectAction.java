package com.Porama6400.OpenFilter.FilterAction;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

import java.util.List;

/**
 * Created by Porama2 on 21/12/2016.
 */
public class CommandRedirectAction implements FilterAction {
    String cmd, to;

    public CommandRedirectAction(String command, String to) {
        cmd = command;
        this.to = to;
    }

    @Override
    public void run(CommandSender sender, String command, List<String> args, Cancellable event) {
        if (!command.startsWith("/" + cmd)) return;
        event.setCancelled(true);
        String out = "";
        for (int i = cmd.length(); i < command.length(); i++) {
            out += command.charAt(i);
        }
        Bukkit.dispatchCommand(sender, "/" + cmd + out);
    }
}
