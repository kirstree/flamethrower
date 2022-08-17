package dev.kirstree.flamethrower;

import dev.kirstree.flamethrower.handlers.listeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Flamethrower extends JavaPlugin {

    public static Flamethrower instance;

    public static Flamethrower getInstance(){
        return instance;
    }

    public static void setInstance(Flamethrower instance){
        Flamethrower.instance = instance;
    }

    @Override
    public void onEnable(){
        Bukkit.getLogger().info("Flamethrower turning on...");
        setInstance(this);
        Bukkit.getPluginManager().registerEvents(new listeners(), this);
    }

    @Override
    public void onDisable(){
        Bukkit.getLogger().info("Flamethrower turning off...");
    }
}
