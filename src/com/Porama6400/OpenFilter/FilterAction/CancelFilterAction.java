package com.Porama6400.OpenFilter.FilterAction;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

import java.util.List;

/**
 * Created by Porama6400
 */
public class CancelFilterAction implements FilterAction{
    @Override
    public void run(CommandSender sender, String command, List<String> args, Cancellable event) {
        event.setCancelled(true);
    }
}
