package dev.kirstree.flamethrower.handlers;

import dev.kirstree.flamethrower.Flamethrower;
import org.bukkit.*;
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
import java.util.concurrent.ThreadLocalRandom;

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

    public void shootParticle(Player p, Particle particle, double velocity, int count, double maxSpread) {

            Location loc = p.getEyeLocation().add(0d, -0.14d, 0d);
            Vector direction = loc.getDirection();
            //it might technically be more efficient to use getters before the loop as the values are the same
            for(int i = 0; i< count; i++){

                p.getWorld().spawnParticle(particle, loc, 0, direction.getX() + randomOffset(maxSpread),
                        direction.getY() + randomOffset(maxSpread),
                        direction.getZ() + randomOffset(maxSpread), velocity);
            }

    }
    public double randomOffset(double maxOffset){
        return ThreadLocalRandom.current().nextDouble(-maxOffset, maxOffset);
    }

    private final long durationMillis = 420;

    public void burn(Player p) {
        // might be nice to add a check so that only one runnable can be running per player at a time - not sure if needed tho
        // would be cool also to add an invisible projectile to deal damage
        new BukkitRunnable() {
            final long startMilli = System.currentTimeMillis();

            public void run() {
                long difference = ((startMilli + durationMillis) - System.currentTimeMillis());

                shootParticle(p, Particle.FLAME, 0.8d, 6, 0.1d);
                shootParticle(p, Particle.SMALL_FLAME, 0.8d, 4, 0.05d);
                shootParticle(p, Particle.SMOKE_NORMAL, 0.8d, 5, 0.12d);
                if (difference < 0) {
                    cancel();
                }
            }
        }.runTaskTimer(Flamethrower.getInstance(), 0, 1);
    }
}