package com.Porama6400.OpenFilter.Filters;

import com.Porama6400.OpenFilter.OpenFilterPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Porama2 on 3/1/2017.
 */
public class FilterGen {
    private static final File folder = new File(OpenFilterPlugin.getInstance().getDataFolder().getAbsolutePath() + File.separator + "generate");

    public static void builds() {
        folder.mkdirs();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getDescription().getCommands() == null) continue;
            File file = new File(folder.getAbsolutePath() + File.separator + plugin.getName() + "_FILTER.tbfp");
            BufferedWriter buff = null;
            try {
                FileOutputStream fos = new FileOutputStream(file);
                buff = new BufferedWriter(new OutputStreamWriter(fos));

                buff.write("#Auto generated : Hide all command for plugin \"" + plugin.getName() + "\"");
                buff.newLine();
                buff.write("FILTER");
                buff.newLine();
                buff.write("TARGET PLAYER");
                buff.newLine();
                buff.write("TAB_COMPLETE BLOCK");
                buff.newLine();
                buff.write("PERMISSIONS");
                buff.newLine();
                buff.write("-openfilter.bypass");
                buff.newLine();
                buff.write("CMDS");
                Map<String, Map<String, Object>> str = plugin.getDescription().getCommands();
                for (Map.Entry<String, Map<String, Object>> entry : str.entrySet()) {
                    String command = entry.getKey();
                    buff.newLine();
                    buff.write("-" + command);
                    buff.newLine();
                    buff.write("-" + plugin.getName().toLowerCase() + ":" + command);
                    List<String> aliases = (List<String>) entry.getValue().get("aliases");
                    if (aliases == null) continue;
                    for (String als : aliases) {
                        buff.newLine();
                        buff.write("-" + als);
                        buff.newLine();
                        buff.write("-" + plugin.getName().toLowerCase() + ":" + als);
                    }
                }
                buff.newLine();
                buff.write("ACTION");
                buff.newLine();
                buff.write("-FAKE_NOCOMMAND");
                buff.newLine();
                buff.write("END");
                buff.flush();
                buff.close();
            } catch (Exception e) {
                try {
                    buff.close();
                } catch (IOException e1) {
                    OpenFilterPlugin.getInstance().getLogger().severe(file.getName() + " : Can't close output stream!");
                }

                if (file.exists())
                    file.delete();
                OpenFilterPlugin.getInstance().getLogger().warning("Failed to generate filter for " + plugin.getName());
                e.printStackTrace();
            }
        }
    }
}
