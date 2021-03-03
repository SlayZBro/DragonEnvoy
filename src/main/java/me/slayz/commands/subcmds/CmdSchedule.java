package me.slayz.commands.subcmds;

import me.slayz.Envoy;
import me.slayz.Placeholder;
import me.slayz.Utils;
import me.slayz.commands.CommandHandler;
import me.slayz.commands.CommandRequirement;
import me.slayz.commands.CommandRequirements;
import me.slayz.crates.CrateManager;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.Arrays;

public class CmdSchedule extends CommandHandler {

    public CmdSchedule(){
        this.aliases = new ArrayList<>(Arrays.asList("schedule", "delay"));
        this.requirements = new CommandRequirements(CommandRequirement.PERMISSION);
        this.permission =new Permission("envoy.schedule");
        this.build();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        CrateManager crateManager = Envoy.getInstance().getCrateManager();
        sender.sendMessage(Utils.getMessage("next-envoy", new Placeholder("{next_envoy}", crateManager.formatDelay(crateManager.getSpawnDelay() - crateManager.getTime()))));
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
        return "/envoy schedule";
    }

    @Override
    public boolean requiresArgs() {
        return true;
    }
}
