package me.slayz.commands.subcmds;

import me.slayz.Envoy;
import me.slayz.Placeholder;
import me.slayz.Utils;
import me.slayz.commands.CommandHandler;
import me.slayz.commands.CommandRequirement;
import me.slayz.commands.CommandRequirements;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.Collections;


public class CmdSetEgg extends CommandHandler {

    public CmdSetEgg(){
        this.aliases = new ArrayList<>(Collections.singletonList("setegg"));
        this.requirements = new CommandRequirements(CommandRequirement.PERMISSION);
        this.permission = new Permission("envoy.setworld");
        this.build();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(sender instanceof Player) {

            Player p = (Player) sender;

            Block b = getTargetBlock(p, 5);
            if (b != null && b.getType() == Material.DRAGON_EGG) {

                Envoy.getInstance().getConfig().set("egg.x", b.getX());
                Envoy.getInstance().getConfig().set("egg.y", b.getY());
                Envoy.getInstance().getConfig().set("egg.z", b.getZ());
                Envoy.getInstance().saveConfig();

                sender.sendMessage(ChatColor.GREEN + "Egg location saved!");
            } else {
                sender.sendMessage(Placeholder.apply((Utils.getMessage("world.world-does-not-exist")), new Placeholder("{arg}", args[1])));
            }

        }
    }

    public final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

    @Override
    public String getLabel() {
        return "envoy";
    }

    @Override
    public boolean hasArgs() {
        return true;
    }

    @Override
    public String getUsage() {
        return "/envoy setegg";
    }

    @Override
    public boolean requiresArgs() {
        return true;
    }
}
