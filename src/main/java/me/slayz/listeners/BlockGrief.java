package me.slayz.listeners;

import me.slayz.crates.Crate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockGrief implements Listener {

    @EventHandler
    public void greif(EntityExplodeEvent e){
        if(e.getEntity() == Crate.getEnderDragon())
            e.setCancelled(true);
    }
}
