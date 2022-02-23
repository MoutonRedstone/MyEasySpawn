package fr.mouton_redstone.myeasyspawn.commands;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class SpawnCommand implements CommandExecutor {

    Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);
    private final HashMap<UUID, Long> cooldown;

    public SpawnCommand(){
        this.cooldown = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Location spawn = (sender.getServer().getWorld(plugin.getConfig().getString("spawnWorldName")).getSpawnLocation());
        String tpMessage = plugin.getConfig().getString("teleportMessage");
        String tpMessageColor = plugin.getConfig().getString("teleportMessageColor");

        //Command executed by player
        if (sender instanceof Player p) {
            if (p.hasPermission("myeasyspawn.spawn") || p.hasPermission("myeasyspawn.*")) {
                if (!this.cooldown.containsKey(p.getUniqueId())) {
                    this.cooldown.put(p.getUniqueId(), System.currentTimeMillis());

                    p.teleport(spawn);
                    if (tpMessage.contains("{username}")) {
                        tpMessage = tpMessage.replace("{username}", p.getDisplayName());
                    }
                    if (!tpMessage.equals("None")) {
                        p.sendMessage(ChatColor.valueOf(tpMessageColor) + tpMessage);
                    }
                } else {
                    long timeElapsed = System.currentTimeMillis() - cooldown.get(p.getUniqueId());
                    int spawnCooldown = plugin.getConfig().getInt("spawnCooldown");
                    spawnCooldown = spawnCooldown * 1000;
                    if (timeElapsed >= spawnCooldown) {
                        p.teleport(spawn);
                        if (tpMessage.contains("{username}")) {
                            tpMessage = tpMessage.replace("{username}", p.getDisplayName());
                        }
                        if (!tpMessage.equals("None")) {
                            p.sendMessage(ChatColor.valueOf(tpMessageColor) + tpMessage);
                        }
                    } else {
                        timeElapsed = (spawnCooldown - timeElapsed) / 1000;
                        p.sendMessage(ChatColor.RED + "Stop ! Please wait " + timeElapsed + " seconds before.");
                    }
                }

            } else {
                p.sendMessage(ChatColor.RED + "You do not have permission");
            }
        }else{
            System.out.println("[My Easy Spawn] --> The spawn command can only be executed by a player, not by" + sender);
        }

        return true;
    }
}
