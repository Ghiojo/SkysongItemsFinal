package com.skysongdev.skysongitems.command.funcionaldesc;

import com.skysongdev.skysongitems.TextParser;
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

public class DelFunctional implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        ItemStack item = player.getInventory().getItemInMainHand();
        List<String> itemLore = new ArrayList<String>();
        String preChange;
        try {
            ItemMeta buffer = item.getItemMeta();
        } catch (Exception e) {
            return false;
        }
        ItemMeta buffer = item.getItemMeta();
        assert buffer != null;
        PersistentDataContainer bufferDataContainer = buffer.getPersistentDataContainer();
        if (bufferDataContainer.has(TextParser.deletedKey, PersistentDataType.STRING)) {
            String bufferString = bufferDataContainer.get(TextParser.deletedKey, PersistentDataType.STRING);
            preChange = bufferString;
            StringBuilder builder = new StringBuilder(bufferString);
            if (builder.charAt(1) == '0') {
                builder.setCharAt(1, '1');
            } else {
                builder.setCharAt(1, '0');
            }
            bufferDataContainer.set(TextParser.deletedKey, PersistentDataType.STRING, builder.toString());

        } else {
            preChange = "000";
            bufferDataContainer.set(TextParser.deletedKey, PersistentDataType.STRING, "010");
        }
        itemLore = TextParser.parseText(item.getItemMeta().getLore(), preChange);
        itemLore = TextParser.translateToLore(itemLore, bufferDataContainer.get(TextParser.deletedKey, PersistentDataType.STRING));
        buffer.setLore(itemLore);
        item.setItemMeta(buffer);
        return true;
    }
}
