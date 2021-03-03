package me.slayz.crates.contents;

import me.slayz.Envoy;
import me.slayz.Placeholder;
import me.slayz.crates.Crate;
import me.slayz.items.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class Content {

    private Crate crate;
    private ContentType contentType;
    private String id;

    public Content(Crate crate, ContentType type, String id){
        this.crate = crate;
        this.contentType = type;
        this.id = id;
    }

    public void spawn(Player player){
        switch (contentType){
            case ITEM:
                ItemStack itemStack = ItemBuilder.buildItem(Envoy.getInstance().getCfg("crates"), "crates." + crate.getId() + ".items." + id);
                if(itemStack != null){
                    crate.getLocation().getWorld().dropItem(crate.getLocation().add(0, 0.5, 0), itemStack);
                }
                break;
            case COMMAND:
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Placeholder.apply(Envoy.getInstance().getCfg("crates").getConfig().getString("crates." + crate.getId() + ".items." + id + ".command"), new Placeholder("{player}", player.getName())));
                break;
        }
    }

}
