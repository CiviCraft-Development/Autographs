package net.civicraft.autographs;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AutographCommand implements CommandExecutor {
    private static final String AUTOGRAPH_LORE = "Autographed by {player} on {date}";
    private final Autographs instance = Autographs.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (commandSender.hasPermission("autographs.reload")) {
                instance.reloadConfig();
                commandSender.sendMessage(Messages.AUTOGRAPH_RELOAD);
            } else {
                commandSender.sendMessage(Messages.NO_PERMISSION);
            }
            return true;
        }

        if (commandSender instanceof Player player && player.hasPermission("autographs.sign")) {
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            String itemName = itemInHand.getType().toString();
            List<String> allowedItems = instance.getConfig().getStringList("autograph.signable-items");
            if (!allowedItems.isEmpty() && !allowedItems.contains(itemName)) {
                player.sendMessage(Messages.AUTOGRAPH_ITEM_RESTRICTED);
                return true;
            }

            ItemMeta meta = itemInHand.getItemMeta();
            if (meta != null) {
                List<Component> lore = meta.hasLore() ? new ArrayList<>(meta.lore()) : new ArrayList<>();

                String signature = "Autographed by " + player.getName();
                boolean alreadySigned = lore.stream().anyMatch(line -> line.toString().contains(signature));

                if (alreadySigned && !instance.getConfig().getBoolean("autograph.re-sign")) {
                    player.sendMessage(Messages.AUTOGRAPH_EXISTING);
                    return true;
                }

                ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy 'at' HH:mm z");
                String formattedDate = now.format(formatter);

                String formattedLore = AUTOGRAPH_LORE.replace("{player}", player.getName()).replace("{date}", formattedDate);
                lore.add(Component.text(formattedLore));
                meta.addEnchant(Enchantment.UNBREAKING, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                meta.lore(lore);
                itemInHand.setItemMeta(meta);

                player.sendMessage(Messages.AUTOGRAPH_SUCCESS);
            } else {
                player.sendMessage(Messages.AUTOGRAPH_FAILURE);
            }
        } else {
            commandSender.sendMessage(Messages.NO_PERMISSION);
        }
        return true;
    }
}


