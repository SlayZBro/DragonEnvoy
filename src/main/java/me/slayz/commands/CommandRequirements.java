package me.slayz.commands;

public class CommandRequirements {

    private CommandRequirement[] requirements;

    public CommandRequirement[] getRequirements(){
        return requirements;
    }

    public CommandRequirements(CommandRequirement... requirements){
        this.requirements = requirements;
    }

}
