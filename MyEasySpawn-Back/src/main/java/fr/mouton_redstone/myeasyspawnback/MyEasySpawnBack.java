package fr.mouton_redstone.myeasyspawnback;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class MyEasySpawnBack extends JavaPlugin {

    public static HashMap<UUID, Location> backs;

    @Override
    public void onEnable() {
        // Plugin startup logic
        backs = new HashMap<>();

        getCommand("back").setExecutor(new BackCommand());
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        System.out.println("[MyEasySpawn-Back] Back extension successfully loaded");
    }

    @Override
    public void onDisable() {
        System.out.println("[MyEasySpawn-Back] Back extension successfully stopped");
    }
}
