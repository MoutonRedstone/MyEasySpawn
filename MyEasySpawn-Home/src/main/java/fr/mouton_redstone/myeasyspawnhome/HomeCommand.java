package fr.mouton_redstone.myeasyspawnhome;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class HomeCommand implements CommandExecutor {

    Plugin corePlugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String tpMessage = corePlugin.getConfig().getString("teleportMessage");
        String tpMessageColor = corePlugin.getConfig().getString("teleportMessageColor");

        if (sender instanceof Player p){
            if(args.length > 0 && (p.hasPermission("myeasyspawn.homeAdmin") || p.hasPermission("myeasyspawn.*"))){
                Player p2 = Bukkit.getPlayerExact(args[0]);

                PersistentDataContainer data = p2.getPersistentDataContainer();
                String homeWorldString = data.get(new NamespacedKey(MyEasySpawnHome.getPlugin(), "homeWorld"), PersistentDataType.STRING);
                World homeWorld = p.getServer().getWorld(homeWorldString);
                Double X = data.get(new NamespacedKey(MyEasySpawnHome.getPlugin(), "homeCoordsX"), PersistentDataType.DOUBLE);
                Double Y = data.get(new NamespacedKey(MyEasySpawnHome.getPlugin(), "homeCoordsY"), PersistentDataType.DOUBLE);
                Double Z = data.get(new NamespacedKey(MyEasySpawnHome.getPlugin(), "homeCoordsZ"), PersistentDataType.DOUBLE);
                Location home = new Location(homeWorld, X, Y, Z);

                p.teleport(home);
                if (tpMessage.contains("{username}")) {tpMessage = tpMessage.replace("{username}", p.getDisplayName());}
                if (!tpMessage.equals("None")) {p.sendMessage(ChatColor.valueOf(tpMessageColor) + tpMessage);}
            }else if(p.hasPermission("myeasyspawn.home") || p.hasPermission("myeasyspawn.*") || p.hasPermission("myeasyspawn.homeAdmin")){
                PersistentDataContainer data = p.getPersistentDataContainer();
                String homeWorldString = data.get(new NamespacedKey(MyEasySpawnHome.getPlugin(), "homeWorld"), PersistentDataType.STRING);
                World homeWorld = p.getServer().getWorld(homeWorldString);
                Double X = data.get(new NamespacedKey(MyEasySpawnHome.getPlugin(), "homeCoordsX"), PersistentDataType.DOUBLE);
                Double Y = data.get(new NamespacedKey(MyEasySpawnHome.getPlugin(), "homeCoordsY"), PersistentDataType.DOUBLE);
                Double Z = data.get(new NamespacedKey(MyEasySpawnHome.getPlugin(), "homeCoordsZ"), PersistentDataType.DOUBLE);
                Location home = new Location(homeWorld, X, Y, Z);

                p.teleport(home);
                if (tpMessage.contains("{username}")) {tpMessage = tpMessage.replace("{username}", p.getDisplayName());}
                if (!tpMessage.equals("None")) {p.sendMessage(ChatColor.valueOf(tpMessageColor) + tpMessage);}
            }else{
                p.sendMessage(ChatColor.RED + "You do not have permission for this");
            }

        }else{
            try{
                sender.sendMessage("Only players can use this command.");
            }catch (Exception e){
                System.out.println("[MyEasySpawn - Home] --> Only players can use this command.");
            }
        }

        return true;
    }
}
