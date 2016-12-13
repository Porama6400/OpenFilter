package com.Porama6400.OpenFilter.FilterAction;

import com.Porama6400.OpenFilter.InvalidFilterFile;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

import java.util.List;

/**
 * Created by Porama6400
 */
public interface FilterAction {
    public static FilterAction Load(String data) throws InvalidFilterFile {
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
        }
        throw new InvalidFilterFile("Unknown filter action");
    }

    void run(CommandSender sender, String command, List<String> args, Cancellable event);
}
