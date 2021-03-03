package me.slayz.commands.subcmds;

import me.slayz.Envoy;
import me.slayz.commands.CommandHandler;
import me.slayz.commands.CommandRequirement;
import me.slayz.commands.CommandRequirements;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.Collections;

public class CmdSpawn extends CommandHandler {

    public CmdSpawn() {
        this.aliases = new ArrayList<>(Collections.singletonList("spawn"));
        this.requirements = new CommandRequirements(CommandRequirement.PERMISSION);
        this.permission = new Permission("envoy.spawn");
        this.build();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(sender instanceof Player)
            Envoy.getInstance().getCrateManager().createCrate((Player)sender);

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
        return "/envoy spawn";
    }

    @Override
    public boolean requiresArgs() {
        return true;
    }
}
