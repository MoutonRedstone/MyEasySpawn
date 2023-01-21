package fr.mouton_redstone.myeasyspawn;

import fr.mouton_redstone.myeasyspawn.commands.SetCooldownCommand;
import fr.mouton_redstone.myeasyspawn.commands.SetWorldSpawn;
import fr.mouton_redstone.myeasyspawn.commands.SpawnCommand;
import fr.mouton_redstone.myeasyspawn.commands.SpawnConfig;
import fr.mouton_redstone.myeasyspawn.events.MenuHandler;
import fr.mouton_redstone.myeasyspawn.events.JoinLeaveListener;
import fr.mouton_redstone.myeasyspawn.events.TeleportationListener;
import fr.mouton_redstone.myeasyspawn.models.WarpStorageUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

public final class MyEasySpawn extends JavaPlugin {

    private static MyEasySpawn plugin;
    private static Properties serverProperties = new Properties();

    public static HashMap<UUID, Long> cooldown;

    @Override
    public void onEnable() {
        plugin = this;
        cooldown = new HashMap<>();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Get the server's properties
        try {
            File propertiesFile = new File( "server.properties");
            if (propertiesFile.exists()) {
                Reader reader = new FileReader(propertiesFile);
                serverProperties.load(reader);
            }else{
                System.out.println("[My Easy Spawn] Unable to find server's properties, my result in the plugin's crash");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load Warps from persistent storage util
        try {
            WarpStorageUtil.loadWarps();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Update the warps spawn location
        String mainWorldName = serverProperties.getProperty("level-name");
        if (WarpStorageUtil.getWarp("Spawn") == null){
            WarpStorageUtil.createWarp("Spawn", plugin.getServer().getWorld(mainWorldName).getSpawnLocation());
        } else {
            WarpStorageUtil.updateWarp("Spawn", plugin.getServer().getWorld(mainWorldName).getSpawnLocation());
        }

        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("mes_setcooldown").setExecutor(new SetCooldownCommand());
        getCommand("mes_config").setExecutor(new SpawnConfig());
        getCommand("setworldspawn").setExecutor(new SetWorldSpawn());
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new TeleportationListener(), this);
        getServer().getPluginManager().registerEvents(new MenuHandler(), this);
        System.out.println("[MyEasySpawn] My Easy Spawn has successfully started");
    }

    @Override
    public void onDisable() {
        try {
            WarpStorageUtil.saveWarps();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[My Easy Spawn] My Easy Spawn has successfully stopped");
    }

    public static MyEasySpawn getPlugin() {
        return plugin;
    }

    public static Properties getServerProperties() {
        return serverProperties;
    }

}

// This plugin was made by Mouton_Redstone, all Rights Reserved
