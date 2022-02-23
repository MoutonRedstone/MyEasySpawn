package fr.mouton_redstone.myeasyspawn;

import fr.mouton_redstone.myeasyspawn.commands.SetSpawnCooldown;
import fr.mouton_redstone.myeasyspawn.commands.SpawnCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyEasySpawn extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        System.out.println("[My Easy Spawn] --> My Easy Spawn has successfully started");
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("setSpawnCooldown").setExecutor(new SetSpawnCooldown());
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("[My Easy Spawn] --> My Easy Spawn has successfully stopped");
    }
}