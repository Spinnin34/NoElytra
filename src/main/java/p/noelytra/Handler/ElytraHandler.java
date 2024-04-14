package p.noelytra.Handler;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import p.noelytra.NoElytra;

import java.util.Arrays;

public class ElytraHandler implements Listener {

    static String prefix = "§x§F§B§0§0§4§4N§x§F§C§1§5§4§6o§x§F§C§2§B§4§8E§x§F§D§4§0§4§Al§x§F§D§5§5§4§By§x§F§E§6§A§4§Dt§x§F§E§8§0§4§Fr§x§F§F§9§5§5§1a";
    public static void CloseElytra(Player player) {

        Inventory inventario = player.getInventory();
        boolean tieneElytra = false;
        for (int i = 0; i < inventario.getSize(); i++) {
            ItemStack objeto = inventario.getItem(i);
            if (objeto != null && objeto.getType() == Material.ELYTRA) {
                tieneElytra = true;
                ItemMeta metaElytra = objeto.getItemMeta();
                // Crear un peto de cuero y transferir propiedades
                ItemStack petoCuero = new ItemStack(Material.LEATHER_CHESTPLATE, objeto.getAmount());
                inventario.setItem(i, petoCuero);
                petoCuero = inventario.getItem(i);
                ItemMeta meta = petoCuero.getItemMeta();

                // Transferir encantamientos
                for (Enchantment encantamiento : objeto.getEnchantments().keySet()) {
                    int nivel = objeto.getEnchantmentLevel(encantamiento);
                    meta.addEnchant(encantamiento, nivel, true);
                }

                if (meta instanceof Damageable) {
                    ((Damageable) meta).setDamage(((Damageable) metaElytra).getDamage());
                    meta.setUnbreakable(true);
                }

                ((LeatherArmorMeta) meta).setColor(org.bukkit.Color.GRAY);
                if(metaElytra.hasDisplayName()){
                    meta.setDisplayName(metaElytra.getDisplayName());
                } else {
                    meta.setDisplayName(prefix);
                }
                meta.setLore(Arrays.asList("§x§F§F§9§5§5§1Las Elytra no se pueden usar en esta dimensión,", "§x§F§F§9§5§5§1Ingresa al End para reabrir", String.format("§x§F§B§0§0§4§4Durabilidad: %s/432", 432 - ((Damageable) metaElytra).getDamage())));
                petoCuero.setItemMeta(meta);
            }
        }
        if (tieneElytra) {
            player.sendMessage(prefix + " §fTus Elytra han sido cerradas...");
        }
    }

    public static void OpenElytra(Player player) {
        Inventory inventario = player.getInventory();
        boolean tieneElytra = false;
        for (int i = 0; i < inventario.getSize(); i++) {
            ItemStack objeto = inventario.getItem(i);
            if (objeto != null && objeto.getItemMeta().getLore() != null && objeto.getItemMeta().getLore().get(0).equals("Las Elytra no se pueden usar en esta dimensión,")) {
                tieneElytra = true;
                ItemMeta metaCuero = objeto.getItemMeta();
                ItemStack elytra = new ItemStack(Material.ELYTRA, objeto.getAmount());
                inventario.setItem(i, elytra);
                elytra = inventario.getItem(i);
                ItemMeta meta = elytra.getItemMeta();

                for (Enchantment encantamiento : objeto.getEnchantments().keySet()) {
                    if (encantamiento.equals(Enchantment.MENDING) || encantamiento.equals(Enchantment.DURABILITY)) {
                        int nivel = objeto.getEnchantmentLevel(encantamiento);
                        meta.addEnchant(encantamiento, nivel, true);
                    }
                }
                if(!metaCuero.getDisplayName().equals(prefix)) {
                    meta.setDisplayName(metaCuero.getDisplayName());
                }
                if (meta instanceof Damageable) {
                    ((Damageable) meta).setDamage(((Damageable) metaCuero).getDamage());
                    meta.setUnbreakable(false);
                }
                elytra.setItemMeta(meta);
            }
        }
        if (tieneElytra) {
            player.sendMessage(prefix + " §fTus Elytra han sido abiertas.");
        }
    }
}
