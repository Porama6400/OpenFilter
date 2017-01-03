package com.Porama6400.OpenFilter;

import com.Porama6400.OpenFilter.Filters.Filter;
import com.Porama6400.OpenFilter.Filters.FilterGen;
import com.Porama6400.OpenFilter.Loader.TextBasedFilterProfileLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class OpenFilterPlugin extends JavaPlugin {
    public static OpenFilterPlugin plugin;
    OpenFilterCommandListener ofcl = new OpenFilterCommandListener();
    private List<Filter> filterList = new ArrayList<>();

    public List<Filter> getFilterList() {
        return filterList;
    }

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(ofcl, this);
        reloadFilters();
    }

    public void reloadFilters() {
        filterList = new ArrayList<>();
        new TextBasedFilterProfileLoader().load();
    }

    public void RegisterFilter(Filter filter) {
        filterList.add(filter);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 0){
            switch (args[0].toLowerCase()){
                case "generate":
                    FilterGen.builds();
                    sender.sendMessage(ChatColor.YELLOW + "[OpenFilter] " + ChatColor.GREEN + "Generation Completed!");
                    return false;
            }
        }
        sender.sendMessage(ChatColor.RED + "Please specified argument");
        return false;
    }
}
