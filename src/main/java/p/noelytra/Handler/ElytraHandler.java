package p.noelytra.Handler;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;

public class ElytraHandler {
    public static void CloseElytra(Player player) {
        Inventory inventory = player.getInventory();
        boolean hasElytra = false;
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getType() == Material.ELYTRA) {
                hasElytra = true;
                ItemMeta elytraMeta = item.getItemMeta();
                // Create a leather chestplate and transfer properties
                ItemStack leatherChestplate = new ItemStack(Material.LEATHER_CHESTPLATE, item.getAmount());
                inventory.setItem(i, leatherChestplate);
                leatherChestplate = inventory.getItem(i);
                ItemMeta meta = leatherChestplate.getItemMeta();

                // Transfer enchantments
                for (Enchantment enchantment : item.getEnchantments().keySet()) {
                    int level = item.getEnchantmentLevel(enchantment);
                    meta.addEnchant(enchantment, level, true);
                }

                if (meta instanceof Damageable) {
                    ((Damageable) meta).setDamage(((Damageable) elytraMeta).getDamage());
                    meta.setUnbreakable(true);
                }

                ((LeatherArmorMeta) meta).setColor(org.bukkit.Color.GRAY);
                if(elytraMeta.hasDisplayName()){
                    meta.setDisplayName(elytraMeta.getDisplayName());
                } else {
                    meta.setDisplayName("§8Closed Elytra");
                }
                meta.setLore(Arrays.asList("Elytra are unusable in this dimension,", "Enter The End to reopen", String.format("§fDurability: %s/432", 432 - ((Damageable) elytraMeta).getDamage())));
                leatherChestplate.setItemMeta(meta);
            }
        }
        if (hasElytra) {
            player.sendMessage("Your elytra have been closed.");
        }
    }

    public static void OpenElytra(Player player) {
        Inventory inventory = player.getInventory();
        boolean hasElytra = false;
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().get(0).equals("Elytra are unusable in this dimension,")) {
                hasElytra = true;
                ItemMeta leatherMeta = item.getItemMeta();
                ItemStack elytra = new ItemStack(Material.ELYTRA, item.getAmount());
                inventory.setItem(i, elytra);
                elytra = inventory.getItem(i);
                ItemMeta meta = elytra.getItemMeta();

                for (Enchantment enchantment : item.getEnchantments().keySet()) {
                    if (enchantment.equals(Enchantment.MENDING) || enchantment.equals(Enchantment.DURABILITY)) {
                        int level = item.getEnchantmentLevel(enchantment);
                        meta.addEnchant(enchantment, level, true);
                    }
                }
                if(!leatherMeta.getDisplayName().equals("§8Closed Elytra")) {
                    meta.setDisplayName(leatherMeta.getDisplayName());
                }
                if (meta instanceof Damageable) {
                    ((Damageable) meta).setDamage(((Damageable) leatherMeta).getDamage());
                    meta.setUnbreakable(false);
                }
                elytra.setItemMeta(meta);
            }
        }
        if (hasElytra) {
            player.sendMessage("Your elytra have been opened!");
        }
    }

}
