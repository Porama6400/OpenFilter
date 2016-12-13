package com.Porama6400.OpenFilter;

import com.Porama6400.OpenFilter.Filters.Filter;
import com.Porama6400.OpenFilter.Loader.TextBasedFilterProfileLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class OpenFilterPlugin extends JavaPlugin {
    public static OpenFilterPlugin plugin;
    private List<Filter> filterList = new ArrayList<>();
    OpenFilterCommandListener ofcl = new OpenFilterCommandListener();

    public List<Filter> getFilterList() {
        return filterList;
    }

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(ofcl,this);
        new TextBasedFilterProfileLoader().load();
    }

    public void RegisterFilter(Filter filter) {
        filterList.add(filter);
    }
}
