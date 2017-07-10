package com.Porama6400.OpenFilter.FilterAction;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

import java.util.List;

public class CommandRedirectFilterAction implements FilterAction {
    private String to;

    public CommandRedirectFilterAction(String to) {
        this.to = to;
    }

    @Override
    public void run(CommandSender sender, String command, List<String> args, Cancellable event) {
        StringBuilder out = new StringBuilder("/" + to);
        for (String s : args) {
            out.append(" ").append(s);
        }
        Bukkit.dispatchCommand(sender, out.toString());
    }
}
