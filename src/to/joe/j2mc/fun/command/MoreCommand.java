package to.joe.j2mc.fun.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import to.joe.j2mc.core.command.MasterCommand;
import to.joe.j2mc.fun.J2MC_Fun;

public class MoreCommand extends MasterCommand<J2MC_Fun> {

    public MoreCommand(J2MC_Fun fun) {
        super(fun);
    }

    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        if (isPlayer) {
            if (player.getItemInHand().getType().equals(Material.AIR)) {
                sender.sendMessage(ChatColor.RED + "Can't give you more air");
                return;
            }
            if (args.length > 0) {
                try {
                    int numStacks = Integer.parseInt(args[0]);
                    if (numStacks > 36) {
                        numStacks = 36;
                    }
                    ItemStack i = player.getItemInHand();
                    i.setAmount(i.getMaxStackSize());
                    numStacks--;
                    for (int x = 0; x < numStacks; x++) {
                        player.getInventory().addItem(i);
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "That's not a number");
                    return;
                }
            }
            player.getItemInHand().setAmount(player.getItemInHand().getMaxStackSize());
            sender.sendMessage(ChatColor.YELLOW + "You've been given " + ChatColor.AQUA + "more " + ChatColor.GOLD + ChatColor.BOLD + player.getItemInHand().getType().toString().toLowerCase().replace("_", " "));
        }
    }
}