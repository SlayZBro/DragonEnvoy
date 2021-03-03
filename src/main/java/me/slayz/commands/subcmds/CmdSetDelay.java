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
import java.util.Collections;

public class CmdSetDelay extends CommandHandler {

    public CmdSetDelay(){
        this.aliases = new ArrayList<>(Collections.singletonList("setdelay"));
        this.requirements = new CommandRequirements(CommandRequirement.PERMISSION);
        this.permission = new Permission("envoy.setdelay");
        this.build();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(args.length > 1){
            long delay;
            try{
               delay = Long.parseLong(args[1]);
            }catch (NumberFormatException e){
                sender.sendMessage(Utils.getMessage("spawn-delay.not-a-number", new Placeholder("{args}", args[1])));
                return;
            }
            Envoy.getInstance().getCfg("config").getConfig().set("envoy-spawn-delay", delay);
            Envoy.getInstance().getCfg("config").saveConfig();
            sender.sendMessage(Utils.getMessage("spawn-delay.set", new Placeholder("{delay}", Envoy.getInstance().getCrateManager().formatDelay(Envoy.getInstance().getCrateManager().getSpawnDelay()))));
        }else{
            sender.sendMessage(Utils.getMessage("incorrect-usage", new Placeholder("{usage}", getUsage())));
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
        return "/envoy setdelay {delay}";
    }

    @Override
    public boolean requiresArgs() {
        return true;
    }
}
