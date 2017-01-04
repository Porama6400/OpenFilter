package com.Porama6400.OpenFilter;

import com.Porama6400.OpenFilter.Filters.Filter;
import com.Porama6400.OpenFilter.Filters.FilterGen;
import com.Porama6400.OpenFilter.Loader.IFilterLoader;
import com.Porama6400.OpenFilter.Loader.TextBasedFilterProfileLoader;
import com.Porama6400.OpenFilter.Tab.ITabBlocker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class OpenFilterPlugin extends JavaPlugin {
    private static OpenFilterPlugin plugin;
    OpenFilterCommandListener commandListener;
    IFilterLoader[] filterLoaders = {
            new TextBasedFilterProfileLoader()
    };
    private List<Filter> filterList = new ArrayList<>();
    private ITabBlocker tabBlocker;

    public static OpenFilterPlugin getInstance() {
        return plugin;
    }

    public ITabBlocker getTabBlocker() {
        return tabBlocker;
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    @Override
    public void onEnable() {
        plugin = this;
        commandListener = new OpenFilterCommandListener();
        tabBlocker = ITabBlocker.getTabBlocker();
        tabBlocker.Initialize();
        Bukkit.getPluginManager().registerEvents(commandListener, this);
        reloadFilters();
    }

    public void reloadFilters() {
        filterList = new ArrayList<>();
        for(IFilterLoader loader : filterLoaders){
            loader.load();
        }
    }

    public void RegisterFilter(Filter filter) {
        filterList.add(filter);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "generate":
                    FilterGen.builds();
                    sender.sendMessage(ChatColor.YELLOW + "[OpenFilter] " + ChatColor.GREEN + "Generation Completed!");
                    return false;
                case "reload":
                    reloadFilters();
                    sender.sendMessage(ChatColor.YELLOW + "[OpenFilter] " + ChatColor.GREEN + "Configuration reloaded!");
                    return false;
            }
        }
        sender.sendMessage(ChatColor.RED + "Please specified argument");
        return false;
    }
}
