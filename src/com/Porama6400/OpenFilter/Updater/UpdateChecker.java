package com.Porama6400.OpenFilter.Updater;

import com.Porama6400.OpenFilter.OpenFilterPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Porama2 on 4/1/2017.
 */
public class UpdateChecker {
    private final URL pluginyml = new URL("https://raw.githubusercontent.com/UnnamedCheese/OpenFilter/master/plugin.yml");
    private final int line = 4;
    private boolean needUpdate = false;

    public UpdateChecker() throws MalformedURLException {
    }

    public void CheckForUpdate(boolean silence) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    needUpdate = check(); //check if need update
                    if (needUpdate) {
                        if (!silence) {
                            OpenFilterPlugin.getInstance().getLogger().info("Newer version of plugin available for download!");
                            OpenFilterPlugin.getInstance().getLogger().info("Please check this link for update:");
                            OpenFilterPlugin.getInstance().getLogger().info("https://github.com/UnnamedCheese/OpenFilter/");
                        }
                    } else {
                        if (!silence)
                            OpenFilterPlugin.getInstance().getLogger().info("Plugin is up-to-date!");
                    }
                } catch (IOException e) {
                    if (!silence)
                        OpenFilterPlugin.getInstance().getLogger().warning("Can't connect to the server");
                } catch (Exception e) {
                    if (!silence) {
                        OpenFilterPlugin.getInstance().getLogger().warning("Can't parse update file");
                        OpenFilterPlugin.getInstance().getLogger().warning("Can cause by updater itself update");
                        OpenFilterPlugin.getInstance().getLogger().warning("Please check for update manually");
                        e.printStackTrace();
                    }
                }
            }
        }.runTaskAsynchronously(OpenFilterPlugin.getInstance());
    }

    private boolean check() throws IOException {
        Scanner scanner = new Scanner(pluginyml.openStream());
        for (int i = 0; i < (line - 1); i++) {
            scanner.nextLine();
        }
        String VersionLine = scanner.nextLine();
        scanner.close();
        String[] SplitLine = VersionLine.split(": ");
        String[] SplitVer = SplitLine[1].split("\\.");
        int NetMajor = Integer.parseInt(SplitVer[0]);
        int NetMinor = Integer.parseInt(SplitVer[1]);
        String[] Lver = OpenFilterPlugin.getInstance().getDescription().getVersion().split("\\.");
        int LocalMajor = Integer.parseInt(Lver[0]);
        int LocalMinor = Integer.parseInt(Lver[1]);
        if (LocalMajor < NetMajor) return true;
        // L (>)= N
        if (LocalMinor < NetMinor) return true;
        //EQUAL or NEWER
        return false;
    }
}
