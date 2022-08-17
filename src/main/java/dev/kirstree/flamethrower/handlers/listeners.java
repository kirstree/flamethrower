package dev.kirstree.flamethrower.handlers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class listeners implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        ItemStack flamethrower = new ItemStack(Material.DIAMOND_HOE, 1);
        Inventory inv = p.getInventory();

        ItemMeta meta = flamethrower.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "FLAMETHROWER");
        meta.addEnchant(Enchantment.DAMAGE_ALL, 50, false);

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED + "Shoot fire!");
        lore.add(ChatColor.RED + "A new weapon");

        meta.setLore(lore);

        flamethrower.setItemMeta(meta);

        inv.setItem(0, flamethrower);
    }

    @EventHandler
    public void interactEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if(p.getInventory().getItemInMainHand().getType() == Material.DIAMOND_HOE &&
                e.getAction().equals(Action.LEFT_CLICK_BLOCK) ||
                p.getInventory().getItemInMainHand().getType() == Material.DIAMOND_HOE &&
                        e.getAction().equals(Action.LEFT_CLICK_AIR)){

            Particle part = Particle.FLAME;
            Location loc = p.getLocation();
            Vector direction = loc.getDirection();
            double distance = 1d;
            double speed = 1d;
            int count = 30; // count is half the number of particles that will be spawned
            double offsetAngle = 0.1d; //offsetAngle is the angle between particles. idk what kind of units it's using to rotate
            direction.rotateAroundY(-1 * count * offsetAngle);
                    for(int i = -count; i < count; i++){
                        direction.rotateAroundY(offsetAngle);
                        p.getWorld().spawnParticle(part, loc, 0, direction.getX(), direction.getY(),
                                direction.getZ(), speed);
                    }
        }
    }
}