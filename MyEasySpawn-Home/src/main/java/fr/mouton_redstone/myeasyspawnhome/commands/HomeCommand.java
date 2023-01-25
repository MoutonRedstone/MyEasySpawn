package fr.mouton_redstone.myeasyspawnhome.commands;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import fr.mouton_redstone.myeasyspawnhome.MyEasySpawnHome;
import fr.mouton_redstone.myeasyspawnhome.models.HomesStorageUtil;
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
    Plugin plugin = MyEasySpawnHome.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p){
            int number;
            if (args.length>0){
                try{
                    number = Integer.parseInt(args[0]);
                }catch (Exception e){
                    number = 1;
                }
            }else{
                number = 1;
            }
            Location home = HomesStorageUtil.getHome(p.getDisplayName(), number);

            if (home == null){
                Bukkit.dispatchCommand(p, "setHome "+Integer.toString(number));
            }else{
                if (!MyEasySpawn.cooldown.containsKey(p.getUniqueId())) { // PLayer has not been cooldown-ed yet
                    MyEasySpawn.cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                    p.teleport(home);
                } else { // PLayer has an existing cooldown
                    long timeElapsed = System.currentTimeMillis() - MyEasySpawn.cooldown.get(p.getUniqueId());
                    int spawnCooldown = plugin.getConfig().getInt("spawnCooldown") * 1000;
                    if (timeElapsed >= spawnCooldown) { // Player waited long enough
                        p.teleport(home);
                        MyEasySpawn.cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                    } else { // Player still have to wait
                        timeElapsed = (spawnCooldown - timeElapsed) / 1000;
                        p.sendMessage(ChatColor.RED + "Stop ! Please wait " + timeElapsed + " seconds before.");
                    }
                }
            }

        }else{
            System.out.println("[MyEasySpawn - Home] Only players can use this command.");
        }

        return true;
    }
}
