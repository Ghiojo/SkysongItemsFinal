package com.skysongdev.skysongitems.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class getLoreCommand implements CommandExecutor {
    Plugin myPlugin;
    public getLoreCommand(Plugin inPlugin){
        myPlugin = inPlugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;

        for(int i = 0; i < player.getInventory().getItemInMainHand().getItemMeta().getLore().size(); i++) {
            myPlugin.getLogger().info(player.getInventory().getItemInMainHand().getItemMeta().getLore().get(i));
        }

        return true;
    }
}
