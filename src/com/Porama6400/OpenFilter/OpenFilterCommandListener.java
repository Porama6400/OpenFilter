package com.Porama6400.OpenFilter;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Arrays;
import java.util.List;

public class OpenFilterCommandListener implements Listener {
    @EventHandler
    public void onServerCommand(ServerCommandEvent e) {
        onCommand(e.getSender(), e.getCommand());
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        onCommand(e.getPlayer(), e.getMessage());
    }

    public void onCommand(CommandSender sender, String command) {
        List<String> args;
        {   //PREPAIR CMD AND ARGS
            String StrArgs[] = command.split(" ");
            command = StrArgs[0];
            args = Arrays.asList(StrArgs);
            args.remove(0);
        }

    }
}
