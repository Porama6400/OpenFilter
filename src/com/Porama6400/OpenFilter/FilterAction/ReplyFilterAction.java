package com.Porama6400.OpenFilter.FilterAction;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

import java.util.List;

public class ReplyFilterAction extends MessagingFilterAction {
    public ReplyFilterAction(String msg) {
        setMessage(msg);
    }

    @Override
    public void run(CommandSender sender, String command, List<String> args, Cancellable event) {
        sender.sendMessage(getOutputMessage(sender, command));
    }
}
