package com.Porama6400.OpenFilter.Tab;

import com.Porama6400.OpenFilter.Filters.BasicCommandFilter;
import com.Porama6400.OpenFilter.Filters.Filter;
import com.Porama6400.OpenFilter.OpenFilterPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Porama2 on 4/1/2017.
 */
public class ProtocolTabBlocker implements ITabBlocker {
    private HashMap<Player, String> requestMap = new HashMap<>();

    public void Initialize() {
        PacketAdapter CBTabComplete = new PacketAdapter(new PacketAdapter.AdapterParameteters()
                .plugin(OpenFilterPlugin.getInstance()).serverSide().types(new PacketType[]{PacketType.Play.Server.TAB_COMPLETE})) {

            public void onPacketSending(PacketEvent event) {
                if (!requestMap.containsKey(event.getPlayer())) {
                    return; //IN CASE THAT OTHER PLUGIN SEND PROTOCOL? WEIRD?
                }
                Player player = event.getPlayer();
                String message = requestMap.get(player);

                String[] rawEntrys = event.getPacket().getStringArrays().read(0);
                List<String> entrys = new ArrayList<String>(Arrays.asList(rawEntrys));

                outerLoop:
                for (Filter filter : OpenFilterPlugin.getInstance().getFilterList()) {
                    if (filter instanceof BasicCommandFilter) {
                        BasicCommandFilter bcf = (BasicCommandFilter) filter;
                        if (!bcf.isBlockTabComplete()) continue;

                        for (Permission perm : bcf.getPermissions()) {
                            if (event.getPlayer().hasPermission(perm)) continue outerLoop;
                        }

                        for (String cmd : bcf.getFilteredCommands()) {
                            if (message.toLowerCase().startsWith("/" + cmd.toLowerCase())) {
                                event.setCancelled(true);
                                return;
                            }
                        }

                        List<String> toRemove = new ArrayList<>();
                        for (String cmd : bcf.getFilteredCommands()) {
                            for (String tccmd : entrys) {
                                if (cmd.toLowerCase() == tccmd.toLowerCase()) {
                                    toRemove.add(tccmd);
                                }
                            }
                        }
                        entrys.removeAll(toRemove);
                    }
                }

                PacketContainer packetOut = new PacketContainer(PacketType.Play.Server.TAB_COMPLETE);
                String[] listout = new String[entrys.size()];
                listout = entrys.toArray(listout);
                packetOut.getStringArrays().write(0, listout);
                event.setPacket(packetOut);
            }

        };


        PacketAdapter SBTabComplete = new PacketAdapter(new PacketAdapter.AdapterParameteters()
                .plugin(OpenFilterPlugin.getInstance()).clientSide().types(new PacketType[]{PacketType.Play.Client.TAB_COMPLETE})) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                String msg = packet.getStrings().read(0);
                //packet.getBooleans().read(1); //NOT USED
                //boolean hasPos = packet.getBooleans().read(2);
                //if (hasPos) {
                //    packet.getBlockPositionModifier().read(3);
                //}
                requestMap.put(event.getPlayer(), msg);
                Bukkit.getScheduler().runTaskLater(OpenFilterPlugin.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        if (requestMap.containsKey(event.getPlayer())) {
                            requestMap.remove(event.getPlayer());
                        }
                    }
                }, 3);
            }
        };

        ProtocolLibrary.getProtocolManager().addPacketListener(CBTabComplete);
        ProtocolLibrary.getProtocolManager().addPacketListener(SBTabComplete);
    }
}
