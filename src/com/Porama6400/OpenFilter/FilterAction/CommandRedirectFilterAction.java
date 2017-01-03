package com.Porama6400.OpenFilter.FilterAction;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

import java.util.List;

/**
 * Created by Porama2 on 21/12/2016.
 */
public class CommandRedirectFilterAction implements FilterAction {
    String to;

    public CommandRedirectFilterAction(String to) {
        this.to = to;
    }

    @Override
    public void run(CommandSender sender, String command, List<String> args, Cancellable event) {
        String out = "/" + to;
        for (String s : args) {
            out += " " + s;
        }
        Bukkit.dispatchCommand(sender, out);
    }
}
