package fr.mouton.redstone.myeasyspawntpto;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class MyEasySpawnTpTo extends JavaPlugin {

    private static Plugin plugin;
    public static Map<Player, Player> tpRequests = new HashMap();

    @Override
    public void onEnable() {
        plugin = this;
        this.getCommand("tpto").setExecutor(new TpToCommand());
        this.getCommand("tpaccept").setExecutor(new TpAcceptCommand());
        System.out.println("[MyEasySpawn-TpTo] TpTo extension successfully loaded");
    }

    @Override
    public void onDisable() {
        System.out.println("[MyEasySpawn-TpTo] TpTo extension successfully stopped");
    }
}
