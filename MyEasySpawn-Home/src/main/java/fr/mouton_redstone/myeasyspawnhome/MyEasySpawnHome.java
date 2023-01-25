package fr.mouton_redstone.myeasyspawnhome;

import fr.mouton_redstone.myeasyspawnhome.commands.HomeCommand;
import fr.mouton_redstone.myeasyspawnhome.commands.SetHomeCommand;
import fr.mouton_redstone.myeasyspawnhome.models.HomesStorageUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class MyEasySpawnHome extends JavaPlugin {

    private static MyEasySpawnHome plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        try {
            HomesStorageUtil.loadHomes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("home").setExecutor(new HomeCommand());
        System.out.println("[MyEasySpawn-Home] Successfully loaded the home extension !");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MyEasySpawnHome getPlugin() {
        return plugin;
    }
}
