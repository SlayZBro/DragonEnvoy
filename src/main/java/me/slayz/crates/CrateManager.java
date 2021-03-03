package me.slayz.crates;

import me.slayz.Envoy;
import me.slayz.Placeholder;
import me.slayz.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CrateManager {


    public  int crate = 0;

    @Getter
    @Setter
    private  ArrayList<Crate> activeCrate = new ArrayList<>();
    private Block egg;

    private  List<String> types;



    public CrateManager() {
        types = registerTypes();
        startTask();
    }

    public boolean isType(String type) {
        return types.contains(type);
    }

    private int task = -1;

    @Getter
    private long time = 0;

    public long getSpawnDelay() {
        return Envoy.getInstance().getCfg("config").getConfig().getLong("envoy-spawn-delay");
    }

    public void startTask() {
        this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Envoy.getInstance(), () -> {
            time++;
            if(time+10*60 == getSpawnDelay()){
                Bukkit.broadcastMessage(Utils.getMessage("envoy-start-10min", new Placeholder("{prefix}", Utils.getPrefix())));
            }
            if (time == getSpawnDelay()) {
                createCrate(null);
                time = 0;
            }
        }, 20, 20);
    }

    public void cancelTask() {
        if (task != -1) {
            Bukkit.getScheduler().cancelTask(task);
            task = -1;
        }
    }



    public  Crate createCrate(String type, Location location, Player p) {
        if (location != null) {
            try {
                 return new Crate(type, location,p);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void createCrate(Player p){
        despawnEgg();
        Bukkit.broadcastMessage(Utils.getMessage("envoy-start", new Placeholder("{prefix}", Utils.getPrefix())));


        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run(){
                if(i+1 > types.size()) {
                    Crate.enderDragon.remove();
                    Crate.enderDragon = null;
                    Bukkit.broadcastMessage(ChatColor.AQUA+"Envoy has ended.");
                    respawnEgg();
                    this.cancel();

                }else {

                    if(i>0){
                        Location location = Crate.enderDragon.getLocation().clone();
                        location.setY(255);

                        for(int i = 255; i > 0; i--){
                            Location clone = location.clone();
                            clone.setY(i);
                            if(clone.getBlock().getType() != null && clone.getBlock().getType() != Material.AIR){
                                location.setY(i+1);
                                break;
                            }
                            if(i == 1){
                                location.setY(1);
                                break;
                            }
                        }

                        Envoy.getInstance().getCrateManager().addActiveCrate(createCrate(types.get(i),location,p));


                    }else {
                        Envoy.getInstance().getCrateManager().addActiveCrate(createCrate(types.get(i),generateEnvoyLocation(),p));


                    }

                    System.out.println("Spawned #" + i + "(" + types.get(i) + ")");
                    i++;

                }
            }
        }.runTaskTimer(Envoy.getInstance(),0,Envoy.getInstance().getConfig().getInt("crate-fall")*20);
    }

    private List<String> registerTypes() {
        return new ArrayList<>(Envoy.getInstance().getCfg("crates").getConfig().getConfigurationSection("crates").getKeys(false));
    }

    public Location generateEnvoyLocation() {
        World world = Bukkit.getServer().getWorld(Envoy.getInstance().getCfg("config").getConfig().getString("world"));
        if (world != null) {
            int minRadius = Envoy.getInstance().getCfg("config").getConfig().getInt("radius.min");
            int maxRadius = Envoy.getInstance().getCfg("config").getConfig().getInt("radius.max");
            int x = Utils.randomInt(minRadius, maxRadius);
            int z = Utils.randomInt(minRadius, maxRadius);
            Location location = new Location(world, x, 255, z);
            for(int i = 255; i > 0; i--){
                Location clone = location.clone();
                clone.setY(i);
                if(clone.getBlock().getType() != null && clone.getBlock().getType() != Material.AIR){
                    location.setY(i+1);
                    break;
                }
                if(i == 1){
                    location.setY(1);
                    break;
                }
            }
            return location;
        }
        return null;
    }

    public boolean isActive() {
        return getActiveCrate() != null;
    }

    public String formatDelay(long delay) {
        long seconds = delay;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        StringBuilder stringBuilder = new StringBuilder();
        boolean last = false;
        if (days > 0) {
            String end = days > 1 ? "s" : "";
            stringBuilder.append(days + " Day" + end);
            last = true;
        }
        if (hours % 24 > 0) {
            String end = hours % 24 > 1 ? "s" : "";
            if(last) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(hours % 24 + " Hour" + end);
            last = true;
        }
        if (minutes % 60 > 0) {
            if(last){
                stringBuilder.append(" ");
            }
            String end = minutes % 60 > 1 ? "s" : "";
            stringBuilder.append(minutes % 60 + " Minute" + end);
            last = true;
        }
        if (seconds % 60 > 0) {
            String end = seconds % 60 > 1 ? "s" : "";
            if(last){
                stringBuilder.append(" ");
            }
            stringBuilder.append(seconds % 60 + " Second" + end);
        }
        return stringBuilder.toString();

    }

    public void despawnEgg(){
        if(Envoy.getInstance().getConfig().contains("egg.x")){
            int x = Envoy.getInstance().getConfig().getInt("egg.x");
            int y = Envoy.getInstance().getConfig().getInt("egg.y");
            int z = Envoy.getInstance().getConfig().getInt("egg.z");
            World world = Bukkit.getWorld(Envoy.getInstance().getConfig().getString("world"));

            Location l = new Location(world,x,y,z);

            egg = l.getBlock();
            if(egg!=null && egg.getType() != Material.AIR){
                egg.setType(Material.AIR);
            }
        }
    }

    public void respawnEgg(){
        if(Envoy.getInstance().getConfig().contains("egg.x")){
            int x = Envoy.getInstance().getConfig().getInt("egg.x");
            int y = Envoy.getInstance().getConfig().getInt("egg.y");
            int z = Envoy.getInstance().getConfig().getInt("egg.z");
            World world = Bukkit.getWorld(Envoy.getInstance().getConfig().getString("world"));

            Location l = new Location(world,x,y,z);

            egg = l.getBlock();
            egg.setType(Material.DRAGON_EGG);
        }
    }

    public long getTime() {
        return time;
    }

    public  ArrayList<Crate> getActiveCrate() {
        return activeCrate;
    }

    public void addActiveCrate(Crate activeCrate) {
       this.activeCrate.add(activeCrate);
    }
    public  List<String> getTypes() {
        return types;
    }
}
