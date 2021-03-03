package me.slayz.commands.subcmds;

import me.slayz.Envoy;
import me.slayz.Placeholder;
import me.slayz.Utils;
import me.slayz.commands.CommandHandler;
import me.slayz.commands.CommandRequirement;
import me.slayz.commands.CommandRequirements;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.Arrays;

public class CmdSetWorld extends CommandHandler {

    public CmdSetWorld(){
        this.aliases = new ArrayList<>(Arrays.asList("setworld", "world"));
        this.requirements = new CommandRequirements(CommandRequirement.PERMISSION);
        this.permission = new Permission("envoy.setworld");
        this.build();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(args.length > 1){
            World world = Bukkit.getServer().getWorld(args[1]);
            if(world != null){
                Envoy.getInstance().getCfg("config").getConfig().set("world", world.getName());
                Envoy.getInstance().getCfg("config").saveConfig();
                sender.sendMessage(Placeholder.apply((Utils.getMessage("world.set")), new Placeholder("{world}", world.getName())));
            }else{
                sender.sendMessage(Placeholder.apply((Utils.getMessage("world.world-does-not-exist")), new Placeholder("{arg}", args[1])));
            }
        }else{
            sender.sendMessage(Placeholder.apply((Utils.getMessage("incorrect-usage")), new Placeholder("{usage}", getUsage())));
        }
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
        return "/envoy setworld {world}";
    }

    @Override
    public boolean requiresArgs() {
        return true;
    }
}
