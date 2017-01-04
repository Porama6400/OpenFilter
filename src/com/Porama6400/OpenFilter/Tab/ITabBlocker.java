package com.Porama6400.OpenFilter.Tab;

import com.Porama6400.OpenFilter.OpenFilterPlugin;
import org.bukkit.event.server.TabCompleteEvent;

/**
 * Created by Porama2 on 4/1/2017.
 */
public interface ITabBlocker {
    public static ITabBlocker getTabBlocker() {
        try {
            Class.forName("org.bukkit.event.server.TabCompleteEvent");
            OpenFilterPlugin.getInstance().getLogger().info("TabCompleteBlocker mode : TabCompleteEvent");
            return new EventTabBlocker();
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("com.comphenix.protocol.ProtocolLibrary");
                OpenFilterPlugin.getInstance().getLogger().info("TabCompleteBlocker mode : ProtocolLib");
                return new ProtocolTabBlocker();
            }
            catch (ClassNotFoundException ee){
                OpenFilterPlugin.getInstance().getLogger().severe("ProtocolLib not found! Tab blocking disabled!");
                OpenFilterPlugin.getInstance().getLogger().severe("ProtocolLib is needed if server doesn't have TabCompleteEvent (Server too old)");
                return new ITabBlocker() {
                    @Override
                    public void Initialize() {

                    }
                };
            }
        }
    }

    void Initialize();
}
