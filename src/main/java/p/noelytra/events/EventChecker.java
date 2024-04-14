package p.noelytra.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import p.noelytra.Handler.ElytraHandler;

public class EventChecker implements Listener {
    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        if (event.getFrom().getName().equals("world_the_end")) {
            ElytraHandler.CloseElytra(event.getPlayer());
        } else if (event.getPlayer().getWorld().getName().equals("world_the_end")) {
            ElytraHandler.OpenElytra(event.getPlayer());
        }
    }
    @EventHandler
    public void onInventory(InventoryClickEvent event) {
        if (!event.getWhoClicked().getWorld().getName().equals("world_the_end")) {
            ElytraHandler.CloseElytra((Player) event.getWhoClicked());
        } else {
            ElytraHandler.OpenElytra((Player) event.getWhoClicked());
        }
    }
    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {
        if (!event.getPlayer().getWorld().getName().equals("world_the_end")) {
            ElytraHandler.CloseElytra(event.getPlayer());
        } else {
            ElytraHandler.OpenElytra(event.getPlayer());
        }
    }
}
