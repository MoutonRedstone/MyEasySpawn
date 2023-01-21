package fr.mouton_redstone.myeasyspawn.commands;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import fr.mouton_redstone.myeasyspawn.models.WarpStorageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SpawnCommand implements CommandExecutor {

    Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Location spawn = WarpStorageUtil.getWarp("Spawn");

        if (sender instanceof Player p) { // Sender is a Player
            if (!MyEasySpawn.cooldown.containsKey(p.getUniqueId())) { // PLayer has not been cooldown-ed yet
                MyEasySpawn.cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                p.teleport(spawn);
            } else { // PLayer has an existing cooldown
                long timeElapsed = System.currentTimeMillis() - MyEasySpawn.cooldown.get(p.getUniqueId());
                int spawnCooldown = plugin.getConfig().getInt("spawnCooldown")*1000;
                if (timeElapsed >= spawnCooldown) { // Player waited long enough
                    p.teleport(spawn);
                    MyEasySpawn.cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                } else { // Player still have to wait
                    timeElapsed = (spawnCooldown - timeElapsed) / 1000;
                    p.sendMessage(ChatColor.RED + "Stop ! Please wait " + timeElapsed + " seconds before.");
                }
            }
        }else{ // Sender isn't a player
            System.out.println("[My Easy Spawn] The spawn command can only be executed by a player, not by" + sender);
        }

        return true;
    }
}
