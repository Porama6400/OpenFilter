package com.Porama6400.OpenFilter.Filters;

import com.Porama6400.OpenFilter.FilterAction.FilterAction;
import com.Porama6400.OpenFilter.FilterTarget;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;
import java.util.List;

public abstract class Filter {

    List<FilterAction> actions = new ArrayList<FilterAction>();
    FilterTarget target = FilterTarget.ALL;
    List<Permission> bypassPermissions = new ArrayList<>();

    FilterTarget getTarget() {
        return target;
    }

    void setTarget(FilterTarget target) {
        this.target = target;
    }

    public List<FilterAction> getActions() {
        return actions;
    }

    public void addPermission(Permission perm) {
        bypassPermissions.add(perm);
    }

    public void addPermission(List<Permission> perms) {
        bypassPermissions.addAll(perms);
    }

    public List<Permission> getPermissions(){
        return bypassPermissions;
    }

    public void registerPermissions() {
        for (Permission s : bypassPermissions) {
            try {
                Bukkit.getPluginManager().addPermission(s);
            } catch (Exception e) {
                //DO NOTHING , IT'S REGISTERED
            }
        }
    }

    public abstract void execute(CommandSender sender, String command, List<String> args, Cancellable event);
}
