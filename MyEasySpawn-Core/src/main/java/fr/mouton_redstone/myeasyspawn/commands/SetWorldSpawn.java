package fr.mouton_redstone.myeasyspawn.commands;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import fr.mouton_redstone.myeasyspawn.models.WarpStorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Properties;

public class SetWorldSpawn implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) { // Sender is a Player
            boolean managed = true;
            try{
                Bukkit.dispatchCommand(p, "minecraft:setworldspawn");
            }catch (CommandException comm){
                managed = false;
            }
            if (managed) {
                Properties serverProperties = MyEasySpawn.getServerProperties();
                Plugin plugin = MyEasySpawn.getPlugin();
                String mainWorldName = serverProperties.getProperty("level-name");
                if (WarpStorageUtil.getWarp("Spawn") == null){
                    WarpStorageUtil.createWarp("Spawn", plugin.getServer().getWorld(mainWorldName).getSpawnLocation());
                } else {
                    WarpStorageUtil.updateWarp("Spawn", plugin.getServer().getWorld(mainWorldName).getSpawnLocation());
                }
            }
        }else{
            System.out.println("[My Easy Spawn] --> The spawn command can only be executed by a player, not by" + sender);
        }

        return true;
    }
}
