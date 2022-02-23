package fr.mouton_redstone.myeasyspawn.commands;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SetSpawnCooldown implements CommandExecutor {

    Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ( (sender.hasPermission("myeasyspawn.setspawncooldown") || sender.hasPermission("myeasyspawn.*")) || !(sender instanceof Player) ) {
            if (args.length != 0){
                try{
                    int cooldown = Integer.parseInt(args[0]);
                    plugin.getConfig().set("spawnCooldown", cooldown);
                    try {
                        sender.sendMessage(ChatColor.GREEN + "The cooldown has been set to " + cooldown + " seconds.");
                    } catch (Exception f) {
                        System.out.println("[MyEasySawn] --> The cooldown has been set to " + cooldown + " seconds.");
                    }
                }catch(Exception e) {
                    try {
                        sender.sendMessage(ChatColor.RED + "The cooldown must be a round value");
                    } catch (Exception f) {
                        System.out.println("[MyEasySawn] --> The cooldown must be a round value");
                    }
                }
            }else{
                try{
                    sender.sendMessage(ChatColor.RED + "Please specify the amount of seconds");
                }catch(Exception e){
                    System.out.println("[MyEasySawn] --> Please specify the amount of seconds");
                }
            }
        }else{
            sender.sendMessage(ChatColor.RED + "You do not have permission for that");
        }

        return true;
    }
}
