package com.Porama6400.OpenFilter.Tab;

import com.Porama6400.OpenFilter.Filters.BasicCommandFilter;
import com.Porama6400.OpenFilter.Filters.Filter;
import com.Porama6400.OpenFilter.OpenFilterPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public class EventTabBlocker implements Listener, ITabBlocker {

    @EventHandler
    public void onTabComplete(TabCompleteEvent e) {
        outerLoop:
        for (Filter filter : OpenFilterPlugin.getInstance().getFilterList()) {
            if (filter instanceof BasicCommandFilter) {
                BasicCommandFilter bcf = (BasicCommandFilter) filter;
                if (!bcf.isBlockTabComplete()) continue;
                for (Permission perm : bcf.getPermissions()) {
                    if (e.getSender().hasPermission(perm)) continue outerLoop;
                }

                for (String cmd : bcf.getFilteredCommands()) {
                    if (e.getBuffer().toLowerCase().startsWith("/" + cmd.toLowerCase())) {
                        e.setCancelled(true);
                        return;
                    }
                }

                List<String> toRemove = new ArrayList<>();
                for (String cmd : bcf.getFilteredCommands()) {
                    for (String tccmd : e.getCompletions()) {
                        if (cmd.toLowerCase() == tccmd.toLowerCase()) {
                            toRemove.add(tccmd);
                        }
                    }
                }
                if (toRemove.size() > 0) {
                    ArrayList<String> comps = new ArrayList<>(e.getCompletions());
                    comps.removeAll(toRemove);
                    e.setCompletions(comps);
                }
            }
        }
    }

    @Override
    public void Initialize() {
        Bukkit.getPluginManager().registerEvents(this, OpenFilterPlugin.getInstance());
    }
}
