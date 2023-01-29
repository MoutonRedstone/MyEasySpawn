package fr.mouton_redstone.myeasyspawnback;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player p){
            if (!MyEasySpawnBack.backs.containsKey(p.getUniqueId())){
                return true;
            }
            Location location = MyEasySpawnBack.backs.get(p.getUniqueId());
            if (!MyEasySpawn.cooldown.containsKey(p.getUniqueId())) { // PLayer has not been cooldown-ed yet
                    MyEasySpawn.cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                    p.teleport(location);
            } else { // PLayer has an existing cooldown
                long timeElapsed = System.currentTimeMillis() - MyEasySpawn.cooldown.get(p.getUniqueId());
                int spawnCooldown = MyEasySpawn.getPlugin().getConfig().getInt("spawnCooldown")*1000;
                if (timeElapsed >= spawnCooldown) { // Player waited long enough
                    p.teleport(location);
                    MyEasySpawn.cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                } else { // Player still have to wait
                    timeElapsed = (spawnCooldown - timeElapsed) / 1000;
                    p.sendMessage(ChatColor.RED + "Stop ! Please wait " + timeElapsed + " seconds before.");
                }
            }
        }else{
            System.out.println("[MyEasySpawn-Back] /back can only be used by player");
        }
        return true;
    }
}
