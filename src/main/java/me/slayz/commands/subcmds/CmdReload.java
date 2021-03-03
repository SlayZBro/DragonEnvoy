package me.slayz.commands.subcmds;

import me.slayz.Envoy;
import me.slayz.commands.CommandHandler;
import me.slayz.commands.CommandRequirement;
import me.slayz.commands.CommandRequirements;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.Collections;

public class CmdReload extends CommandHandler {

    public CmdReload() {
        this.aliases = new ArrayList<>(Collections.singletonList("reload"));
        this.requirements = new CommandRequirements(CommandRequirement.PERMISSION);
        this.permission = new Permission("envoy.spawn");
        this.build();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Envoy.getInstance().reloadConfig();
        sender.sendMessage(ChatColor.GREEN+"Plugin reloaded!");

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
        return "/envoy reload";
    }

    @Override
    public boolean requiresArgs() {
        return true;
    }
}
