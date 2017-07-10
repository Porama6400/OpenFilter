package com.Porama6400.OpenFilter.FilterAction;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

import java.util.List;

/**
 * Created by Porama6400
 */
public class FakePluginFilterAction implements FilterAction {
    private String msg;

    public FakePluginFilterAction(String[] parts) {
      int count = parts.length - 1;
        msg = "Plugins (" + count + "): ";
        msg += ChatColor.GREEN + parts[1];
        if (parts.length > 2) {
            for (int i = 2; i < parts.length; i++) {
                msg += ChatColor.RESET + ", " + ChatColor.GREEN + parts[i];
            }
        }
    }

    @Override
    public void run(CommandSender sender, String command, List<String> args, Cancellable event) {
        sender.sendMessage(msg);
    }
}
