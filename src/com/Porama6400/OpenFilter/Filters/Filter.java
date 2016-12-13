package com.Porama6400.OpenFilter.Filters;

import com.Porama6400.OpenFilter.FilterAction.FilterAction;
import com.Porama6400.OpenFilter.FilterTarget;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;

import java.util.ArrayList;
import java.util.List;

public abstract class Filter {

    List<FilterAction> actions = new ArrayList<FilterAction>();
    FilterTarget target = FilterTarget.ALL;

    FilterTarget getTarget() {
        return target;
    }

    void setTarget(FilterTarget target) {
        this.target = target;
    }

    public List<FilterAction> getActions() {
        return actions;
    }

    public abstract void execute(CommandSender sender, String command, List<String> args, Cancellable event);
}
