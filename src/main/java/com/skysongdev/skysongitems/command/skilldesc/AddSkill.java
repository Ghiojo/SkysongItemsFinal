package com.skysongdev.skysongitems.command.skilldesc;

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
import java.util.Arrays;
import java.util.List;

public class AddSkill implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta buffer;
        List<String> newItemLore = new ArrayList<String>();
        PersistentDataContainer bufferDataContainer;
        try{
            item.getItemMeta().getLore();
        }catch(Exception e){
            commandSender.sendMessage(ChatColor.of("RED").toString() + "[ERROR] Please hold the item you wish to lore!");
            return false;
        }
        if(strings.length >= 1){
            switch(strings[0]){
                case "-n":
                    if(Arrays.copyOfRange(strings, 1, strings.length).length <= 0){
                        commandSender.sendMessage(ChatColor.of("RED").toString() + "[ERROR] Please type in lore for the item!");
                        return false;
                    }
                    buffer = item.getItemMeta();
                    bufferDataContainer = buffer.getPersistentDataContainer();
                    if(bufferDataContainer.isEmpty() || bufferDataContainer.get(TextParser.deletedKey, PersistentDataType.STRING) == null){
                        newItemLore = TextParser.parseText(item.getItemMeta().getLore());
                        newItemLore = TextParser.manipulateSection(newItemLore, Arrays.copyOfRange(strings, 1, strings.length), 2, false);
                        newItemLore = TextParser.translateToLore(newItemLore);
                    } else{
                        String bufferString = bufferDataContainer.get(TextParser.deletedKey, PersistentDataType.STRING);
                        newItemLore = TextParser.parseText(item.getItemMeta().getLore(), bufferString);
                        newItemLore = TextParser.manipulateSection(newItemLore, Arrays.copyOfRange(strings, 1, strings.length), 2, false);
                        newItemLore = TextParser.translateToLore(newItemLore, bufferString);
                    }

                    buffer.setLore(newItemLore);
                    item.setItemMeta(buffer);
                    player.updateInventory();
                    break;
                default:
                    buffer = item.getItemMeta();
                    bufferDataContainer = buffer.getPersistentDataContainer();
                    if(bufferDataContainer.isEmpty() || bufferDataContainer.get(TextParser.deletedKey, PersistentDataType.STRING) == null){
                        newItemLore = TextParser.parseText(item.getItemMeta().getLore());
                        newItemLore = TextParser.manipulateSectionWithConcat(newItemLore, strings, 2);
                        newItemLore = TextParser.translateToLore(newItemLore);
                    } else{
                        String bufferString = bufferDataContainer.get(TextParser.deletedKey, PersistentDataType.STRING);
                        newItemLore = TextParser.parseText(item.getItemMeta().getLore(), bufferString);
                        newItemLore = TextParser.manipulateSectionWithConcat(newItemLore, strings, 2);
                        newItemLore = TextParser.translateToLore(newItemLore, bufferString);
                    }
                    buffer.setLore(newItemLore);
                    item.setItemMeta(buffer);
                    player.updateInventory();
                    break;
            }
        } else{
            if(strings.length < 1){
                commandSender.sendMessage(ChatColor.of("RED").toString() + "[ERROR] Please type in lore for the item!");
            }
            return false;
        }
        return true;
    }
}
