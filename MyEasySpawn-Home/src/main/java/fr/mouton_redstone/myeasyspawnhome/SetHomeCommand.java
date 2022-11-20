package fr.mouton_redstone.myeasyspawnhome;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SetHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if(args.length > 0 && (p.hasPermission("myeasyspawn.homeAdmin") || p.hasPermission("myeasyspawn.*"))){
                p = Bukkit.getPlayerExact(args[0]);

                PersistentDataContainer data = p.getPersistentDataContainer();

                Location home = p.getLocation();
                String world = p.getWorld().getName();

                data.set(new NamespacedKey (MyEasySpawnHome.getPlugin(), "homeCoordsX"), PersistentDataType.DOUBLE, home.getX());
                data.set(new NamespacedKey (MyEasySpawnHome.getPlugin(), "homeCoordsY"), PersistentDataType.DOUBLE, home.getY());
                data.set(new NamespacedKey (MyEasySpawnHome.getPlugin(), "homeCoordsZ"), PersistentDataType.DOUBLE, home.getZ());
                data.set(new NamespacedKey (MyEasySpawnHome.getPlugin(), "homeWorld"),   PersistentDataType.STRING, world);

            }else if(p.hasPermission("myeasyspawn.home") || p.hasPermission("myeasyspawn.*") || p.hasPermission("myeasyspawn.homeAdmin")){
                PersistentDataContainer data = p.getPersistentDataContainer();

                Location home = p.getLocation();
                String world = p.getWorld().getName();

                data.set(new NamespacedKey (MyEasySpawnHome.getPlugin(), "homeCoordsX"), PersistentDataType.DOUBLE, home.getX());
                data.set(new NamespacedKey (MyEasySpawnHome.getPlugin(), "homeCoordsY"), PersistentDataType.DOUBLE, home.getY());
                data.set(new NamespacedKey (MyEasySpawnHome.getPlugin(), "homeCoordsZ"), PersistentDataType.DOUBLE, home.getZ());
                data.set(new NamespacedKey (MyEasySpawnHome.getPlugin(), "homeWorld"),   PersistentDataType.STRING, world);
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
