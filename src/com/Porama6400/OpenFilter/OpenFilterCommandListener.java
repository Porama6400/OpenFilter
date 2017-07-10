package com.Porama6400.OpenFilter;

import com.Porama6400.OpenFilter.Filters.BasicCommandFilter;
import com.Porama6400.OpenFilter.Filters.Filter;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.permissions.Permission;

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

    private void onCommandHandler(CommandSender sender, String command, Cancellable cancellable) {
        List<String> args = null;
        if (command.contains(" ")) {
            args = new ArrayList<>(Arrays.asList(command.split(" ")));
            command = args.get(0);
            args.remove(0);
        }
        outerLoop:
        for (Filter filter : OpenFilterPlugin.getInstance().getFilterList()) {
            for (Permission perm : filter.getPermissions()) {
                if (sender.hasPermission(perm)) continue outerLoop;
            }
            filter.execute(sender, command, args, cancellable);
        }
    }
}
