package me.slayz.listeners;

import org.bukkit.PortalType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCreatePortalEvent;

public class SpawnerSpawnListener implements Listener {

    @EventHandler
    public void onEntitySpawnPortal(EntityCreatePortalEvent e){
        if(e.getPortalType() == PortalType.ENDER){
            e.setCancelled(true);
        }
    }

}
