package com.skysongdev.skysongitems;

import com.skysongdev.skysongitems.command.funcionaldesc.FunctionalDesc;
import com.skysongdev.skysongitems.command.getLoreCommand;
import com.skysongdev.skysongitems.command.physicaldesc.PhysicalDesc;
import com.skysongdev.skysongitems.command.skilldesc.SkillDesc;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main plugin;


    @Override
    public void onEnable() {
        plugin = this;
        super.onEnable();
        getCommand("getlore").setExecutor(new getLoreCommand(this));
        getCommand("physdesc").setExecutor(new PhysicalDesc());
        getCommand("funcdesc").setExecutor(new FunctionalDesc());
        getCommand("skilldesc").setExecutor(new SkillDesc());
    }

    public static Main getPlugin(){
        return plugin;
    }
}
