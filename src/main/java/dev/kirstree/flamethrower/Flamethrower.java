package dev.kirstree.flamethrower;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Flamethrower extends JavaPlugin {

    @Override
    public void onEnable(){
        Bukkit.getLogger().info("Flamethrower turning on...");
    }

    @Override
    public void onDisable(){
        Bukkit.getLogger().info("Flamethrower turning off...");
    }
}
