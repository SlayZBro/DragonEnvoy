package me.slayz.listeners;

import me.slayz.Envoy;
import me.slayz.crates.Crate;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CrateClickListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (Envoy.getInstance().getCrateManager().isActive()) {
                for(Crate crate : Envoy.getInstance().getCrateManager().getActiveCrate()){
                    if(e.getClickedBlock().equals(crate.getLocation().getBlock())){
                        e.setCancelled(true);
                        e.getClickedBlock().setType(Material.AIR);
                        crate.claim(e.getPlayer());
                    }
                }




            }
        }

    }
}
