package fr.mouton_redstone.myeasyspawnhome;

import org.bukkit.plugin.java.JavaPlugin;

public final class MyEasySpawnHome extends JavaPlugin {

    private static MyEasySpawnHome plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        System.out.println("[MyEasySpawn - Home] --> Successfully loaded the home extension !");
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("home").setExecutor(new HomeCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MyEasySpawnHome getPlugin() {
        return plugin;
    }
}
