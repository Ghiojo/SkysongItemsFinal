package com.skysongdev.skysongitems.command.skilldesc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class SkillDesc implements CommandExecutor {
    AddSkill addSkill = new AddSkill();
    SetSkill setSkill = new SetSkill();
    DelSkill delSkill = new DelSkill();
    DelLineSkill delLine = new DelLineSkill();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

            String[] newArgs = Arrays.copyOfRange(strings, 1, strings.length);
            switch (strings[0].toLowerCase()) {
                case "add":
                    if (strings.length > 1) {
                        addSkill.onCommand(commandSender, command, s, newArgs);
                    }
                    break;
                case "set":
                    if (strings.length > 1) {
                        setSkill.onCommand(commandSender, command, s, newArgs);
                    }
                    break;
                case "del":
                case "delete":
                    delSkill.onCommand(commandSender, command, s, newArgs);
                    break;
                case "delline":
                case "dl":
                    if (strings.length > 1) {
                        delLine.onCommand(commandSender, command, s, newArgs);
                    }
                    break;
                default:
                    return false;
            }
            return true;
    }
}
