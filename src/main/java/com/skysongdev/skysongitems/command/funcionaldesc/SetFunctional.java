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

public class SetFunctional implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta buffer;
        List<String> newItemLore = new ArrayList<String>();
        try{
            item.getItemMeta().getLore();
        }catch(Exception e){
            commandSender.sendMessage(ChatColor.of("RED").toString() + "[ERROR] Please hold the item you wish to lore!");
            return false;
        }
        buffer = item.getItemMeta();
        PersistentDataContainer bufferDataContainer = buffer.getPersistentDataContainer();
        if(strings.length > 0){
            if(bufferDataContainer.isEmpty() || bufferDataContainer.get(TextParser.deletedKey, PersistentDataType.STRING) == null){
                newItemLore = TextParser.parseText(item.getItemMeta().getLore());
                newItemLore = TextParser.manipulateSection(newItemLore, strings, 1, true);
                newItemLore = TextParser.translateToLore(newItemLore);
            } else{
                String bufferString = bufferDataContainer.get(TextParser.deletedKey, PersistentDataType.STRING);
                newItemLore = TextParser.parseText(item.getItemMeta().getLore(), bufferString);
                newItemLore = TextParser.manipulateSection(newItemLore, strings, 1, true);
                newItemLore = TextParser.translateToLore(newItemLore, bufferString);
            }

            buffer.setLore(newItemLore);
            item.setItemMeta(buffer);
            player.updateInventory();
        }else{
            if(strings.length < 1){
                commandSender.sendMessage(ChatColor.of("RED").toString() + "[ERROR] Please type in lore for the item!");
            }
            return false;
        }
        return true;
    }
}
