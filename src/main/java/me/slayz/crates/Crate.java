package me.slayz.crates;

import me.slayz.Envoy;
import me.slayz.Placeholder;
import me.slayz.Utils;
import me.slayz.crates.contents.Content;
import me.slayz.crates.contents.ContentType;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Crate {

    private String id;

    private List<Content> contents;

    private Location location;

    private boolean active;

    public static EnderDragon enderDragon;

    private Player lastAttack;

    private FallingBlock fallingBlock;

    private Color color;

    public void setLastAttack(Player player){
        this.lastAttack = player;
    }

    public Crate(String id, Location location,Player p) throws IllegalArgumentException {
        if (Envoy.getInstance().getCrateManager().isType(id)) {
            this.id = id;
            this.location = location;
            this.contents = createContents();
            setColor();
            activate();
            if(p!=null)
                p.sendMessage(Utils.getMessage("envoy-started", new Placeholder("{location}", "X: " + location.getBlockX() + " Y: " + location.getBlockY() + " Z: " + location.getBlockZ()), new Placeholder("{type}", id)));

        } else throw new IllegalArgumentException("Invalid Type");
    }

    public List<Content> createContents() {
        List<Content> contents = new ArrayList<>();
        List<Content> potentialContents = generateAllContents(false);
        for (int i = 0; i < getMaxContents(); i++) {
            contents.add(potentialContents.get(Utils.randomInt(0, potentialContents.size() - 1)));
        }
        return contents;
    }

    public List<Content> generateEnderDragonContents(){
        List<Content> contents = new ArrayList<>();
        List<Content> potentialContents = generateAllContents(true);
        for (int i = 0; i < 1; i++) {
            contents.add(potentialContents.get(Utils.randomInt(0, potentialContents.size() - 1)));
        }
        return contents;
    }

    private List<Content> generateAllContents(boolean ender) {
        List<Content> contents = new ArrayList<>();
        String path;
        if(ender){
            path = "crates." + id + ".ender.items";
        }else{
            path = "crates." + id + ".items";
        }
        for (String item : Envoy.getInstance().getCfg("crates").getConfig().getConfigurationSection(path).getKeys(false)) {
            for (int i = 0; i < Envoy.getInstance().getCfg("crates").getConfig().getInt(path + "." + item + ".chance"); i++) {
                contents.add(new Content(this, ContentType.valueOf(Envoy.getInstance().getCfg("crates").getConfig().getString(path + "." + item + ".type").toUpperCase()), item));
            }
        }
        return contents;
    }

    public void setColor(){
        String color = Envoy.getInstance().getCfg("crates").getConfig().getString("crates." + id + ".color");

        if(color.equalsIgnoreCase("white"))
            this.color = Color.WHITE;
        else if(color.equalsIgnoreCase("blue"))
            this.color = Color.BLUE;
        else
            this.color = Color.RED;


    }

    public int getMaxContents() {
        return Envoy.getInstance().getCfg("crates").getConfig().getInt("crates." + id + ".contents");
    }

    public void activate() {
        active = true;
        if(enderDragon == null) {
            this.enderDragon = (EnderDragon) location.getWorld().spawnEntity(location.clone().add(0, 25, 0), EntityType.ENDER_DRAGON);
            enderDragon.setCustomName(ChatColor.translateAlternateColorCodes('&',Envoy.getInstance().getConfig().getString("dragon-name")));
            enderDragon.setMaxHealth(Envoy.getInstance().getConfig().getDouble("dragon-health"));
            enderDragon.setHealth(Envoy.getInstance().getConfig().getDouble("dragon-health"));
        }

        this.fallingBlock = location.getWorld().spawnFallingBlock(location.clone().add(0, 25, 0), Material.DRAGON_EGG, (byte) 0);
        fallingBlock.setDropItem(false);
        fallingBlock.setHurtEntities(false);
        fallingBlock.setTicksLived(1);
    }



    public void claim(Player player) {
        for (Content content : contents) {
            content.spawn(player);
        }
        player.sendMessage(Utils.getMessage("envoy-claimed.user"));
        Bukkit.broadcastMessage(Utils.getMessage("envoy-claimed.broadcast", new Placeholder("{player}", player.getName())));
    }

    public Location getLocation() {
        return location;
    }

    public FallingBlock getFallingBlock() {
        return fallingBlock;
    }

    public static EnderDragon getEnderDragon() {
        return enderDragon;
    }


    public String getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }
}
