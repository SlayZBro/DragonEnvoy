package me.slayz.commands;

import me.slayz.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.*;

public abstract class CommandHandler {

    public abstract void perform(CommandSender sender, String[] args);

    private static final HashMap<String, List<CommandHandler>> CMD_HANDLER_MAP = new HashMap<>();

    public static List<CommandHandler> getCommandHandlers(String command){
        return CMD_HANDLER_MAP.get(command);
    }

    public abstract String getLabel();

    public abstract boolean hasArgs();

    public CommandRequirements requirements;

    public ArrayList<String> aliases;

    public Permission permission;

    private String alias;

    public static Collection<List<CommandHandler>> getAllHandlers(){
        return CMD_HANDLER_MAP.values();
    }

    public void build() {
        if (CMD_HANDLER_MAP.containsKey(getLabel())) {
            List<CommandHandler> commandHandlers = CMD_HANDLER_MAP.get(getLabel());
            commandHandlers.add(this);
            CMD_HANDLER_MAP.put(getLabel(), commandHandlers);
        } else {
            CMD_HANDLER_MAP.put(getLabel(), new ArrayList<>(Collections.singletonList(this)));
        }
    }

    public abstract String getUsage();

    public abstract boolean requiresArgs();

    public boolean execute(CommandSender sender, String[] args, String label) {
        if (args.length > 0 && hasArgs()) {
            for (CommandHandler command : CMD_HANDLER_MAP.get(label.toLowerCase())) {
                if(command.aliases != null) {
                    if (command.aliases.contains(args[0].toLowerCase())) {
                        command.alias = args[0];
                        if (checkRequirements(command.requirements, sender, command, true)) {
                            command.perform(sender, args);
                        } else {
                            return false;
                        }
                    } else if (command.aliases.contains("{player}")) {
                        if (Bukkit.getServer().getPlayer(args[0]) != null || Bukkit.getServer().getOfflinePlayer(args[0]).hasPlayedBefore()) {
                            command.perform(sender, args);
                        } else {
                            sender.sendMessage(Utils.getMessage("incorrect-usage").replace("{usage}", command.getUsage()));
                            return false;
                        }
                    }
                }
            }
        } else {
            if(!hasArgs() || !requiresArgs()) {
                if (checkRequirements(requirements, sender, this, true)) {
                    perform(sender, args);
                } else {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean checkRequirements(CommandRequirements commandRequirements, CommandSender sender, CommandHandler command, boolean execute){
        for (CommandRequirement requirement : commandRequirements.getRequirements()) {
            if (requirement.equals(CommandRequirement.PLAYER)) {
                if (!(sender instanceof Player)) {
                    if(execute) {
                        missedRequirement(requirement, sender, command);
                    }
                    return false;
                }
            } else if (requirement.equals(CommandRequirement.PERMISSION)) {
                if (!sender.hasPermission(command.permission)) {
                    if(execute) {
                        missedRequirement(requirement, sender, command);
                    }
                    return false;
                }
            }else if(requirement.equals(CommandRequirement.OP)){
                if(!sender.isOp()){
                    if(execute) {
                        missedRequirement(requirement, sender, command);
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void missedRequirement(CommandRequirement requirement, CommandSender sender, CommandHandler command) {
        switch (requirement) {
            case PLAYER:
                sender.sendMessage(Utils.c(Utils.getMessage("error-players-only")));
                break;
            case PERMISSION:
                sender.sendMessage(Utils.c(Utils.getMessage("error-no-permission").replace("{node}", command.permission.getName())));
                break;
            case OP:
                sender.sendMessage(Utils.c(Utils.getMessage("error-no-op")));
        }
    }

    public void sendHelpMessage(CommandSender sender, List<String> commands, String message) {
        message = message.replace("{prefix}", Utils.getPrefix());
        if(message.contains("{commands}")){
            if(commands.size() > 0){
                for(String command: commands){
                    sender.sendMessage(Utils.c(message.replace("{commands}", command)));
                }
            }
        }else{
            sender.sendMessage(Utils.c(message));
        }
    }

}
