package fr.mouton_redstone.myeasyspawn;

import fr.mouton_redstone.myeasyspawn.commands.SetCooldownCommand;
import fr.mouton_redstone.myeasyspawn.commands.SpawnCommand;
import fr.mouton_redstone.myeasyspawn.commands.SpawnConfig;
import fr.mouton_redstone.myeasyspawn.events.MenuHandler;
import fr.mouton_redstone.myeasyspawn.events.JoinLeaveListener;
import fr.mouton_redstone.myeasyspawn.events.TeleportationListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyEasySpawn extends JavaPlugin {

    private static MyEasySpawn plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        System.out.println("[My Easy Spawn] --> My Easy Spawn has successfully started");
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("mes setcooldown").setExecutor(new SetCooldownCommand());
        getCommand("mes config").setExecutor(new SpawnConfig());
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new TeleportationListener(), this);
        getServer().getPluginManager().registerEvents(new MenuHandler(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("[My Easy Spawn] --> My Easy Spawn has successfully stopped");
    }

    public static MyEasySpawn getPlugin() {
        return plugin;
    }

}
