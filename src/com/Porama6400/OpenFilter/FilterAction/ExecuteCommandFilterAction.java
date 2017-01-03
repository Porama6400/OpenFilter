package com.Porama6400.OpenFilter.FilterAction;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

import java.util.List;

/**
 * Created by Porama2 on 21/12/2016.
 */
public class ExecuteCommandFilterAction implements FilterAction {
    private final String command;

    public ExecuteCommandFilterAction(String[] args) {
        String cmd = null;
        for(int i = 1;i< args.length;i++){
            if(cmd == null){
                cmd = args[i];
            }
            else cmd += " " + args[i];
        }
        command = cmd;
    }

    @Override
    public void run(CommandSender sender, String command, List<String> args, Cancellable event) {
        event.setCancelled(true);
        Bukkit.dispatchCommand(sender, command);
    }
}
