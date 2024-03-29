package fr.mouton_redstone.myeasyspawnhome;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import fr.mouton_redstone.myeasyspawn.models.SQLInterface;
import fr.mouton_redstone.myeasyspawnhome.commands.HomeCommand;
import fr.mouton_redstone.myeasyspawnhome.commands.ListHomesCommand;
import fr.mouton_redstone.myeasyspawnhome.commands.SetHomeCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

public final class MyEasySpawnHome extends JavaPlugin {

    private static MyEasySpawnHome plugin;
    // SQL related
    public static SQLInterface sql;
    private final String[] homesColumns = new String[]{"player", "id", "world", "x", "y", "z", "yaw", "pitch"};
    private final String[] homesDims = new String[]{"TEXT NOT NULL","INTEGER NOT NULL", "TEXT NOT NULL", "DOUBLE NOT NULL", "DOUBLE NOT NULL", "DOUBLE NOT NULL", "FLOAT NOT NULL", "FLOAT NOT NULL"};


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
        getCommand("listhomes").setExecutor(new ListHomesCommand());
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
