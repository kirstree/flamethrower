package dev.kirstree.flamethrower.handlers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class listeners implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        ItemStack flamethrower = new ItemStack(Material.DIAMOND_HOE, 1);
        Inventory inv = p.getInventory();

        ItemMeta meta = flamethrower.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "FLAMETHROWER");
        meta.addEnchant(Enchantment.DAMAGE_ALL, 10, false);

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED + "Shoot fire!");
        lore.add(ChatColor.RED + "A new weapon");

        meta.setLore(lore);

        flamethrower.setItemMeta(meta);

        inv.setItem(1, flamethrower);


    }
}
