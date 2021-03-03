package me.slayz.commands.subcmds;

import me.slayz.Envoy;
import me.slayz.Placeholder;
import me.slayz.Utils;
import me.slayz.commands.CommandHandler;
import me.slayz.commands.CommandRequirement;
import me.slayz.commands.CommandRequirements;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.Arrays;

public class CmdSetRadius extends CommandHandler {

    public CmdSetRadius(){
        this.aliases = new ArrayList<>(Arrays.asList("setradius", "radius"));
        this.requirements = new CommandRequirements(CommandRequirement.PERMISSION);
        this.permission = new Permission("envoy.setradius");
        this.build();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(args.length > 2){
            if(Utils.isInteger(args[1]) && Utils.isInteger(args[2])){
                int min = Integer.parseInt(args[1]);
                int max = Integer.parseInt(args[2]);
                Envoy.getInstance().getCfg("config").getConfig().set("radius.min", min);
                Envoy.getInstance().getCfg("config").getConfig().set("radius.max", max);
                Envoy.getInstance().getCfg("config").saveConfig();
                sender.sendMessage(Placeholder.apply(Utils.c(Utils.getMessage("radius.set")), new Placeholder("{min}", min + ""), new Placeholder("{max}", max + "")));
            }else{
                sender.sendMessage(Utils.c(Utils.getMessage("radius.numbers-required")));
            }
        }else{
            sender.sendMessage(Placeholder.apply(Utils.c(Utils.getMessage("incorrect-usage")), new Placeholder("{usage}", getUsage())));
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
        return "/envoy setradius {min} {max}";
    }

    @Override
    public boolean requiresArgs() {
        return true;
    }
}
