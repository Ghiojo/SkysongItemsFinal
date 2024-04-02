package com.skysongdev.skysongitems.command.physicaldesc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class PhysicalDesc implements CommandExecutor {
    AddPhysical addPhys = new AddPhysical();
    SetPhysical setPhys = new SetPhysical();
    DelPhys delPhys = new DelPhys();

    DelLinePhysical delLine = new DelLinePhysical();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        String[] newArgs = Arrays.copyOfRange(strings, 1, strings.length);
        switch (strings[0].toLowerCase()) {
            case "add":
                if(strings.length > 1)
                    addPhys.onCommand(commandSender, command, s, newArgs);
                else
                    return false;
                break;
            case "set":
                if(strings.length > 1)
                    setPhys.onCommand(commandSender, command, s, newArgs);
                else
                    return false;
                break;
            case "del":
            case "delete":
                delPhys.onCommand(commandSender, command, s, newArgs);
                break;
            case "delline":
            case "dl":
                if(strings.length > 1) {
                    delLine.onCommand(commandSender, command, s, newArgs);
                }
                break;
            default:
                return false;
        }
        return true;
    }
}
