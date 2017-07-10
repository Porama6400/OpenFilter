package com.Porama6400.OpenFilter.Loader;

import com.Porama6400.OpenFilter.FilterAction.FilterAction;
import com.Porama6400.OpenFilter.FilterTarget;
import com.Porama6400.OpenFilter.Filters.BasicCommandFilter;
import com.Porama6400.OpenFilter.Filters.Filter;
import com.Porama6400.OpenFilter.InvalidFilterFile;
import com.Porama6400.OpenFilter.OpenFilterPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Porama6400
 */
public class YamlFilterProfileLoader implements IFilterLoader {
    @Override
    public String getFileExtension() {
        return ".yfp";
    }

    @Override
    public void LoadFilter(FileInputStream fis) throws IOException, InvalidFilterFile {
        try {
            ILoad(fis);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void ILoad(FileInputStream fis) throws InvalidFilterFile, IOException, InvalidConfigurationException {
        InputStreamReader isr = new InputStreamReader(fis);
        YamlConfiguration yml = new YamlConfiguration();
        yml.load(isr);
        isr.close();

        if (!yml.getBoolean("enabled")) return;
        List<String> keys = new ArrayList<>(yml.getConfigurationSection("filters").getKeys(false));
        for (String keyEntry : keys) {
            String sectionKey = "filters." + keyEntry + ".";
            if (!yml.getBoolean(sectionKey + "enable")) {
                OpenFilterPlugin.getInstance().getLogger().info("Skipped " + keyEntry);
                continue;
            }
            OpenFilterPlugin.getInstance().getLogger().info("Enabling " + keyEntry);
            try {
                //START LOAD

                //FILTER TARGET
                FilterTarget target = FilterTarget.PLAYER;
                String targetString = yml.getString(sectionKey + "target");
                if (targetString != null) target = FilterTarget.valueOf(targetString);

                //TAB COMPLETE BLOCKING
                Object booleanBlockTabComplete = yml.get(sectionKey + "block_tab_complete");
                if (booleanBlockTabComplete == null) booleanBlockTabComplete = false;

                List<String> commandsString = yml.getStringList(sectionKey + "commands");
                List<String> actionsString = yml.getStringList(sectionKey + "actions");
                List<String> permsString = yml.getStringList(sectionKey + "permissions");

                List<Permission> permissionsList = new ArrayList<>();
                for (String permEntry : permsString) {
                    try {
                        Permission permObj = new Permission(permEntry);
                        permissionsList.add(permObj);
                        Bukkit.getPluginManager().addPermission(permObj);
                    } catch (Exception e) {
                        //PERMISSION ALREADY REGISTERED
                    }
                }

                List<FilterAction> actions = new ArrayList<>();
                for (String action : actionsString) {
                    actions.add(FilterAction.load_TBFP(action));
                }

                Filter filter = new BasicCommandFilter(commandsString, (boolean) booleanBlockTabComplete, target, actions, permissionsList);
                OpenFilterPlugin.getInstance().RegisterFilter(filter);

            }
            catch (Exception e){
                OpenFilterPlugin.getInstance().getLogger().warning("Unable to load " + keyEntry );
                e.printStackTrace();
            }
        }
    }
}
