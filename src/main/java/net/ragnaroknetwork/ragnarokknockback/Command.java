package net.ragnaroknetwork.ragnarokknockback;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Command implements CommandExecutor {
    Plugin plugin = RagnarokKnockback.getPlugin(RagnarokKnockback.class);

    @Override
    public boolean onCommand (CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("ragnarokknockback.admin")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l► &cYou do not have permission to execute this command!"));
                return true;
            }
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lRagnarokKnockback Help\n&c&l ► &c/rkb multiplier <value>: &7Change the knockback enchantment's velocity.\n&c&l ► &c/rkb base <value>: &7Change the knockback enchantment's base velocity.\n&c&l ► &c/rbb reload: &7Reload the config file."));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l► &aSuccessfully reloaded &2RagnarokBowBoosting&a!"));
            return true;
        }

        else if (args[0].equalsIgnoreCase("multiplier")) {
            if (args.length == 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l► &7Knockback Enchantment multiplier: &c" + plugin.getConfig().getString("knockbackMultiplier")));
                return true;
            }

            try {
                Double.parseDouble(args[1].trim());
            } catch (NumberFormatException e){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l► &cValue is not a number!"));
                return true;
            }

            Double multiplier = Double.parseDouble(args[1].trim());

            plugin.getConfig().set("knockbackMultiplier", multiplier);
            plugin.saveConfig();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l► &7The knockback enchantment multiplier was set to &c" + args[1]));
            return true;
        }

        else if (args[0].equalsIgnoreCase("base")) {
            if (args.length == 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l► &7Knockback Enchantment base: &c" + plugin.getConfig().getString("base")));
                return true;
            }

            try {
                Double.parseDouble(args[1].trim());
            } catch (NumberFormatException e){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l► &cValue is not a number!"));
                return true;
            }

            Double multiplier = Double.parseDouble(args[1].trim());

            plugin.getConfig().set("base", multiplier);
            plugin.saveConfig();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l► &7The knockback enchantment base was set to &c" + args[1]));
            return true;
        }

        else if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lRagnarokKnockback Help\n&c&l ► &c/rkb multiplier <value>: &7Change the knockback enchantment's velocity.\n&c&l ► &c/rkb base <value>: &7Change the knockback enchantment's base velocity.\n&c&l ► &c/rbb reload: &7Reload the config file."));
            return true;
        }

        return true;
    }
}
