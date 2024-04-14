package p.noelytra;

import org.bukkit.plugin.java.JavaPlugin;
import p.noelytra.events.EventChecker;

public final class NoElytra extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getLogger().info("El plugin a sido inciado correctamente.");
        getServer().getPluginManager().registerEvents(new EventChecker(), this);

    }

    @Override
    public void onDisable() {
    }
}
