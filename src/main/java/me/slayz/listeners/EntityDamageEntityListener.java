package me.slayz.listeners;

import me.slayz.Envoy;
import me.slayz.crates.Crate;
import me.slayz.crates.contents.Content;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageEntityListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e){
        if(Envoy.getInstance().getCrateManager().isActive()) {
            for (Crate crate : Envoy.getInstance().getCrateManager().getActiveCrate()) {
                if (crate.getEnderDragon() != null && !crate.getEnderDragon().isDead()) {
                    if (e.getEntity().equals(crate.getEnderDragon())) {
                        EnderDragon enderDragon = crate.getEnderDragon();
                        if (enderDragon.getHealth() - e.getDamage() <= 0) {
                            if (e.getDamager() instanceof Player) {
                                Player player = (Player) e.getDamager();
                                for (Content content : crate.generateEnderDragonContents()) {
                                    content.spawn(player);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
