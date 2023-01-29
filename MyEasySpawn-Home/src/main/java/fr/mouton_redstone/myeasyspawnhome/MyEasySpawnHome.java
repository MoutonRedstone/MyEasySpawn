package fr.mouton_redstone.myeasyspawnhome;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import fr.mouton_redstone.myeasyspawn.models.SQLInterface;
import fr.mouton_redstone.myeasyspawnhome.commands.HomeCommand;
import fr.mouton_redstone.myeasyspawnhome.commands.SetHomeCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class MyEasySpawnHome extends JavaPlugin {

    private static MyEasySpawnHome plugin;
    // SQL related
    public static SQLInterface sql;
    private final String[] homesColumns = new String[]{"id","world", "x", "y", "z", "yaw", "pitch"};
    private final String[] homesDims = new String[]{"TEXT NOT NULL PRIMARY KEY","TEXT NOT NULL DEFAULT 0", "DOUBLE NOT NULL DEFAULT 0", "DOUBLE NOT NULL DEFAULT 0", "DOUBLE NOT NULL DEFAULT 0", "FLOAT NOT NULL DEFAULT 0", "FLOAT NOT NULL DEFAULT 0"};


    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // Load SQL and make sure there is a home table
        sql = MyEasySpawn.sql;
        if (!sql.checkTable("Homes")) {
            sql.createTable("Homes", this.homesColumns, this.homesDims);
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
