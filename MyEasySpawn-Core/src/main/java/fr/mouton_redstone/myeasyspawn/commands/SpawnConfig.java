package fr.mouton_redstone.myeasyspawn.commands;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import fr.mouton_redstone.myeasyspawn.models.ConfigItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class SpawnConfig implements CommandExecutor {

    Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            Inventory gui = Bukkit.createInventory(p, 9, "myEasySpawn Configuration :");

            // Getting all the items from the item creation class
            ItemStack cooldown = ConfigItemCreator.CooldownItem();
            ItemStack tpMessage = ConfigItemCreator.TpMessageItem();
            ItemStack joinMessage = ConfigItemCreator.JoinMessageItem();
            ItemStack leaveMessage = ConfigItemCreator.LeaveMessageItem();

            ItemStack[] home_items = {cooldown, tpMessage, joinMessage, leaveMessage};

            gui.setContents(home_items);
            p.openInventory(gui);

        }else{
            System.out.println("[My Easy Spawn] --> The config menu can only be opened by a player, not by" + sender);
        }
        return true;
    }
}
