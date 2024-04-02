package com.skysongdev.skysongitems.command.funcionaldesc;

import com.skysongdev.skysongitems.TextParser;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class DelLineFunctional implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta buffer;
        List<String> newItemLore = new ArrayList<String>();
        try{
            parseInt(strings[0]);
        } catch(NumberFormatException e){
            commandSender.sendMessage(ChatColor.of("RED").toString() + "[ERROR] Please type an actual number of line!");
            return false;
        }

        try{
            item.getItemMeta().getLore();
        }catch(Exception e){
            commandSender.sendMessage(ChatColor.of("RED").toString() + "[ERROR] Please hold the item you wish to edit!");
            return false;
        }
        buffer = item.getItemMeta();
        PersistentDataContainer bufferDataContainer = buffer.getPersistentDataContainer();
        if(bufferDataContainer.isEmpty() || bufferDataContainer.get(TextParser.deletedKey, PersistentDataType.STRING) == null){
            newItemLore = TextParser.parseText(item.getItemMeta().getLore());
            newItemLore = TextParser.deleteLine(1, parseInt(strings[0]), newItemLore);
            newItemLore = TextParser.translateToLore(newItemLore);
        } else{
            String bufferString = bufferDataContainer.get(TextParser.deletedKey, PersistentDataType.STRING);
            newItemLore = TextParser.parseText(item.getItemMeta().getLore(), bufferString);
            newItemLore = TextParser.deleteLine(1, parseInt(strings[0]), newItemLore);
            newItemLore = TextParser.translateToLore(newItemLore, bufferString);
        }
        buffer.setLore(newItemLore);
        item.setItemMeta(buffer);
        return true;
    }
}
