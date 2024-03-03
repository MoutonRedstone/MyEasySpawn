package fr.mouton_redstone.myeasyspawn.commands;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SetCooldownCommand implements CommandExecutor {

    Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0){
            try{
                int cooldown = Integer.parseInt(args[0]);
                plugin.getConfig().set("spawnCooldown", cooldown); // Set the cooldown in the
                try {
                    sender.sendMessage(ChatColor.GREEN + "The cooldown has been set to " + cooldown + " seconds.");
                } catch (Exception f) {
                    System.out.println("[MyEasySpawn] --> The cooldown has been set to " + cooldown + " seconds.");
                }
            }catch(Exception e) { // The user did not provide a valid number of seconds
                try {
                    sender.sendMessage(ChatColor.RED + "The cooldown must be a round number");
                } catch (Exception f) {
                    System.out.println("[MyEasySpawn] --> The cooldown must be a round number");
                }
            }
        }else{ // User did not say the amount of seconds
            try{
                sender.sendMessage(ChatColor.RED + "Please specify the amount of seconds");
            }catch(Exception e){
                System.out.println("[MyEasySpawn] --> Please specify the amount of seconds");
            }
        }

        return true;
    }
}
