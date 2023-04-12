package net.ragnaroknetwork.ragnarokknockback;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class RagnarokKnockback extends JavaPlugin implements Listener {

    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("ragnarokknockback").setExecutor(new Command());
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        this.getLogger().info("[RagnarokKnockback] Disabling plugin.");
    }

    @EventHandler
    public void onPlayerVelocity(PlayerVelocityEvent event) {
        Entity entity = event.getPlayer();

        EntityDamageEvent damageEvent = entity.getLastDamageCause();
        if (damageEvent == null) return;
        if (damageEvent.isCancelled()) return;
        if (!(damageEvent instanceof EntityDamageByEntityEvent)) return;

        Entity attacker = ((EntityDamageByEntityEvent)damageEvent).getDamager();
        if (!(attacker instanceof Player)) return;

        Player player = (Player) attacker;
        if (!player.getItemInHand().containsEnchantment(Enchantment.KNOCKBACK)) return;

        int knockbackStrength = player.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK);
        Double multiplier = this.getConfig().getDouble("knockbackMultiplier") * knockbackStrength;
        Double base = this.getConfig().getDouble("base");

        Vector oldVelocity = event.getVelocity();
        double oldVelocityLength = Math.sqrt(oldVelocity.getX() * oldVelocity.getX() + oldVelocity.getZ() * oldVelocity.getZ());
        Vector newVelocity = new Vector((oldVelocity.getX() / oldVelocityLength) * (base + multiplier), oldVelocity.getY(), (oldVelocity.getZ() / oldVelocityLength) * (base + multiplier));

        event.setVelocity(newVelocity);
    }
}

