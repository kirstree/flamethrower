package dev.kirstree.flamethrower.handlers;

import dev.kirstree.flamethrower.Flamethrower;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class listeners implements Listener {

    public String flameName = ChatColor.DARK_RED + ChatColor.BOLD.toString() + "FLAMETHROWER";
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        ItemStack flamethrower = new ItemStack(Material.DIAMOND_HOE, 1);
        Inventory inv = p.getInventory();

        ItemMeta meta = flamethrower.getItemMeta();
        assert meta != null;
        meta.setDisplayName(flameName);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 50, false);

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED + "Shoot fire!");
        lore.add(ChatColor.RED + "A new weapon");

        meta.setLore(lore);

        flamethrower.setItemMeta(meta);

        inv.setItem(0, flamethrower);
    }

    @EventHandler
    public void onInteractP(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            ItemMeta itemName = p.getInventory().getItemInMainHand().getItemMeta();
            if(itemName != null){

                if(itemName.getDisplayName().equals(flameName)){
                    burn(p);
                }
            }
        }
    }

    public void shootParticle(Player p, Particle particle, double velocity) {

            Location loc = p.getEyeLocation();
            Vector direction = loc.getDirection();
            double distance = 1d;

            p.getWorld().spawnParticle(particle, loc, 0, direction.getX()*distance, direction.getY()*distance,
                    direction.getZ()*distance, velocity);

    }

    private final long durationMillis = 5000;

    public void burn(Player p) {
        new BukkitRunnable() {
            final long startMilli = System.currentTimeMillis();

            public void run() {
                long difference = ((startMilli + durationMillis) - System.currentTimeMillis());

                shootParticle(p, Particle.FLAME, 1.5);
                if (difference < 0) {
                    cancel();
                }
            }
        }.runTaskTimer(Flamethrower.getInstance(), 0, 3);
    }
}