package com.Porama6400.OpenFilter;

import com.Porama6400.OpenFilter.Filters.Filter;
import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpenFilterCommandListener implements Listener {
    @EventHandler
    public void onServerCommand(ServerCommandEvent e) {
        onCommandHandler(e.getSender(), "/" + e.getCommand(), e);
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        onCommandHandler(e.getPlayer(), e.getMessage(), e);
    }

    public void onCommandHandler(CommandSender sender, String command, Cancellable cancellable) {
        List<String> args = null;
        if (command.contains(" ")) { 
            args = new ArrayList<>(Arrays.asList(command.split(" ")));
            command = args.get(0);
            args.remove(0);
        }
        for(Filter filter : OpenFilterPlugin.plugin.getFilterList()){
            filter.execute(sender,command,args,cancellable);
        }

    }
}
