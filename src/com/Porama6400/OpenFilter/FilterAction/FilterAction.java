package com.Porama6400.OpenFilter.FilterAction;

import com.Porama6400.OpenFilter.InvalidFilterFile;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

import java.util.List;

/**
 * Created by Porama6400
 */
public interface FilterAction {
    static FilterAction load_TBFP(String data) throws InvalidFilterFile {
        String[] parts;
        {
            if (data.contains(" ")) {
                parts = data.split(" ");
            } else parts = new String[]{data};
        }
        switch (parts[0]) {
            case "CANCEL":
                return new CancelFilterAction();
            case "FAKE_NOCOMMAND":
                return new UnknownCommandFilterAction();
            case "REPLY":
            case "BROADCAST":
                try {
                    StringBuilder msg = new StringBuilder(parts[1]);
                    for (int i = 2; i < parts.length; i++) {
                        msg.append(" ").append(parts[i]);
                    }
                    msg = new StringBuilder(ChatColor.translateAlternateColorCodes('&', msg.toString()));

                    if (parts[0] == "REPLY")
                        return new ReplyFilterAction(msg.toString());
                    else return new BroadcastFilterAction(msg.toString());
                } catch (IndexOutOfBoundsException ignored) {
                }
                break;
            case "FAKE_PLUGINS":
                return new FakePluginFilterAction(parts);
            case "REDIRECT":
                return new CommandRedirectFilterAction(parts[1]);
            case"EXECUTE":
                return new ExecuteCommandFilterAction(parts);
        }
        throw new InvalidFilterFile("Unknown filter action");
    }

    void run(CommandSender sender, String command, List<String> args, Cancellable event);
}
