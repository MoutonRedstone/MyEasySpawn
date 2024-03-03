package fr.mouton_redstone.myeasyspawn;

import fr.mouton_redstone.myeasyspawn.commands.SetCooldownCommand;
import fr.mouton_redstone.myeasyspawn.commands.SetWorldSpawn;
import fr.mouton_redstone.myeasyspawn.commands.SpawnCommand;
import fr.mouton_redstone.myeasyspawn.commands.SpawnConfig;
import fr.mouton_redstone.myeasyspawn.events.MenuHandler;
import fr.mouton_redstone.myeasyspawn.events.JoinLeaveListener;
import fr.mouton_redstone.myeasyspawn.events.TeleportationListener;
import fr.mouton_redstone.myeasyspawn.models.SQLInterface;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

public final class MyEasySpawn extends JavaPlugin {

    private static MyEasySpawn plugin;
    private static Properties serverProperties = new Properties();
    // SQL Interface related
    public static SQLInterface sql;
    private final String[] warpsColumns = new String[]{"name","world", "x", "y", "z", "yaw", "pitch"};
    private final String[] warpsDims = new String[]{"TEXT NOT NULL PRIMARY KEY","TEXT NOT NULL DEFAULT 0", "DOUBLE NOT NULL DEFAULT 0", "DOUBLE NOT NULL DEFAULT 0", "DOUBLE NOT NULL DEFAULT 0", "FLOAT NOT NULL DEFAULT 0", "FLOAT NOT NULL DEFAULT 0"};

    public static HashMap<UUID, Long> cooldown;

    @Override
    public void onEnable() {
        plugin = this;
        cooldown = new HashMap<>();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Start SQL and make sure there is a Warps table
        sql = new SQLInterface(this);
        if (!sql.checkTable("Warps")) {
            sql.createTable("Warps", this.warpsColumns, this.warpsDims);
        }

        // Get the server's properties
        System.out.println("[MyEasySpawn] Loading server's properties");
        try {
            File propertiesFile = new File( "server.properties");
            if (propertiesFile.exists()) {
                Reader reader = new FileReader(propertiesFile);
                serverProperties.load(reader);
            }else{
                System.out.println("[My Easy Spawn] Unable to find server's properties, might result in a plugin crash");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the world's spawn coords and add them to the SQL table
        try {
            if (sql.query("SELECT COUNT(*) FROM Warps WHERE name='Spawn'").getInt(1)<1){
                updateDatabaseSpawn( plugin.getServer().getWorld(serverProperties.getProperty("level-name")) );
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        sql.closeConnection();
        System.out.println("[My Easy Spawn] My Easy Spawn has successfully stopped");
    }

    public static void updateDatabaseSpawn(World world){
        String mainWorldName = world.getName();
        Location spawnLoc = world.getSpawnLocation();
        String x = Double.toString(spawnLoc.getX());
        String y = Double.toString(spawnLoc.getY());
        String z = Double.toString(spawnLoc.getZ());
        sql.query("INSERT OR REPLACE INTO Warps (name, world, x, y, z, yaw, pitch) " +
                "VALUES('Spawn', '"+mainWorldName+"' ,"+x+", "+y+", "+z+", 0, 0)");

        System.out.println("[MyEasySpawn] Updated spawn coordinates");
    }

    public static MyEasySpawn getPlugin() {
        return plugin;
    }

    public static Properties getServerProperties() {
        return serverProperties;
    }

}

// This plugin was made by Mouton_Redstone, all Rights Reserved
