package com.Porama6400.OpenFilter.FilterAction;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

/**
 * Created by Porama6400
 */
public abstract class MessagingFilterAction implements FilterAction {

    private String message = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String Imessage) {
        message = Imessage;
    }

    public String getOutputMessage(CommandSender sender, String command) {
        String name = "";
        String customName = "";
        if (sender instanceof Player) {
            Player player = (Player) sender;
            name = player.getName();
            customName = player.getCustomName();
            if(customName == null) customName = name;
        } else {
            name = "console";
            customName = "Console";
        }
        message = message.replace("%name%", name);
        message = message.replace("%customname%", customName);
        message = message.replace("%command%", command);
        return message;
    }
}
