package com.Porama6400.OpenFilter.Filters;

import com.Porama6400.OpenFilter.OpenFilterPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by Porama2 on 3/1/2017.
 */
public class FilterGen {
    public static final File folder = new File(OpenFilterPlugin.plugin.getDataFolder().getAbsolutePath() + File.separator + "generate");

    public static void builds() {
        folder.mkdirs();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getDescription().getCommands() == null) continue;
            File file = new File(folder.getAbsolutePath() + File.separator + plugin.getName() + "_FILTER");

            try {
                FileOutputStream fos = new FileOutputStream(file);
                BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fos));

                br.write("#Auto generated : Hide all command for plugin \"" + plugin.getName() + "\"");
                br.newLine();
                br.write("FILTER");
                br.newLine();
                br.write("TARGET PLAYER");
                br.newLine();
                br.write("CMDS");
                Map<String, Map<String, Object>> str = plugin.getDescription().getCommands();
                for (Map.Entry<String, Map<String, Object>> entry : str.entrySet()) {
                    String command = entry.getKey();
                    br.newLine();
                    br.write("-" + command);
                    br.newLine();
                    br.write("-" + plugin.getName().toLowerCase() + ":" + command);
                    List<String> aliases = (List<String>) entry.getValue().get("aliases");
                    if(aliases == null) continue;
                    for(String als : aliases){
                        br.newLine();
                        br.write("-" + als);
                        br.newLine();
                        br.write("-" + plugin.getName().toLowerCase() + ":" + als);
                    }
                }
                br.newLine();
                br.write("ACTION");
                br.newLine();
                br.write("-FAKE_NOCOMMAND");
                br.newLine();
                br.write("END");
                br.flush();
                br.close();

            } catch (Exception e) {
                if (file.exists())
                    file.delete();
                e.printStackTrace();
            }
        }
    }
}
