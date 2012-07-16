package to.joe.j2mc.fun.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.blocks.ItemType;

import to.joe.j2mc.core.command.MasterCommand;
import to.joe.j2mc.fun.J2MC_Fun;

public class RemoveItemCommand extends MasterCommand {
    
    J2MC_Fun plugin;

    public RemoveItemCommand(J2MC_Fun plugin) {
        super(plugin);
        this.plugin = plugin;
    }
    
    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        if (isPlayer) {
            ItemStack heldItem = player.getInventory().getItemInHand();
            ItemType type = ItemType.fromID(heldItem.getTypeId());
            player.getInventory().clear(player.getInventory().getHeldItemSlot());
            player.sendMessage(ChatColor.AQUA + "Removed the " + ChatColor.GOLD + ChatColor.BOLD + type.getName() + ChatColor.AQUA + " you were holding");
        }
    }

}
