package me.slayz.listeners;

import me.slayz.Envoy;
import me.slayz.crates.Crate;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class FallingBlockListener implements Listener {

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent e){
        if(e.getEntity() instanceof FallingBlock){
            FallingBlock fallingBlock = (FallingBlock) e.getEntity();
            if(Envoy.getInstance().getCrateManager().isActive()) {
                for (Crate crate : Envoy.getInstance().getCrateManager().getActiveCrate()) {
                    if (crate.getFallingBlock().equals(fallingBlock)) {
                        e.setCancelled(true);
                        crate.getLocation().getBlock().setType(Material.CHEST);
                        spawnFireworks(crate.getLocation(),5,crate.getColor());
                    }
                }
            }
        }
    }


    public static void spawnFireworks(Location location, int amount,Color color){
        Location loc = location;
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(color).flicker(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();

        for(int i = 0;i<amount; i++){
            Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
            fw2.setFireworkMeta(fwm);
        }
    }

}
