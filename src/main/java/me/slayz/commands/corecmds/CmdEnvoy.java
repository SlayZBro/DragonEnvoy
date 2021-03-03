package me.slayz.commands.corecmds;

import me.slayz.Envoy;
import me.slayz.Utils;
import me.slayz.commands.CommandHandler;
import me.slayz.commands.CommandRequirement;
import me.slayz.commands.CommandRequirements;
import com.tapgb.envoy.commands.subcmds.*;
import me.slayz.commands.subcmds.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class CmdEnvoy extends CommandHandler implements CommandExecutor {

    public CmdEnvoy(){
        this.requirements = new CommandRequirements(CommandRequirement.PERMISSION);
        this.permission = new Permission("envoy.help");
        this.build();
        new CmdSchedule();
        new CmdSetDelay();
        new CmdSetRadius();
        new CmdSetWorld();
        new CmdSpawn();
        new CmdSetEgg();
        new CmdReload();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        for(String message: Envoy.getInstance().getCfg("messages").getConfig().getStringList("help-message")){
            if(message.contains("{commands}")){
                for(CommandHandler commandHandler: CommandHandler.getCommandHandlers("envoy")){
                    String usage = commandHandler.getUsage();
                    sender.sendMessage(Utils.c(message.replace("{commands}", usage)));
                }
            }else{
                sender.sendMessage(Utils.c(message));
            }
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
        return "/envoy";
    }

    @Override
    public boolean requiresArgs() {
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return execute(sender, args, command.getName());
    }
}
