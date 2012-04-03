package to.joe.j2mc.fun.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import to.joe.j2mc.core.J2MC_Manager;
import to.joe.j2mc.core.command.MasterCommand;
import to.joe.j2mc.core.event.MessageEvent;
import to.joe.j2mc.core.exceptions.BadPlayerMatchException;
import to.joe.j2mc.fun.J2MC_Fun;

public class ItemCommand extends MasterCommand {

    J2MC_Fun plugin;

    public ItemCommand(J2MC_Fun fun) {
        super(fun);
        this.plugin = fun;
    }

    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        if (isPlayer) {
            final boolean isAdmin = sender.hasPermission("j2mc.fun.admin");
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Correct usage is: /i [item](:damage) (amount)");
                return;
            }
            final Player targetPlayer = player;
            Material itemMaterial = null;
            int itemCount = 1;
            short itemDamage = 0;
            final String[] idDamageSplit = args[0].split(":");
            if (idDamageSplit[0].equals("0")) {
                idDamageSplit[0] = "1";
            }
            itemMaterial = Material.matchMaterial(idDamageSplit[0]);
            if (idDamageSplit.length == 2) {
                final String damageString = idDamageSplit[1];
                final short value = this.toWoolValue(damageString);
                if (value != 0) {
                    itemDamage = value;
                } else {
                    try {
                        itemDamage = Short.valueOf(damageString);
                    } catch (final NumberFormatException e) {
                        //Nope
                    }
                }
            }
            if ((itemDamage < 0) || (itemDamage > 15)) {
                itemDamage = 0;
            }
            if (args.length > 1) {
                final String countString = args[1];
                try {
                    itemCount = Integer.parseInt(countString);
                } catch (final NumberFormatException ex) {
                    //nope
                }
            }
            if ((args.length == 3) && isAdmin) {
                final String targetName = args[2];
                try {
                    J2MC_Manager.getVisibility().getPlayer(targetName, sender);
                } catch (final BadPlayerMatchException e) {
                    player.sendMessage(ChatColor.RED + e.getMessage());
                    return;
                }
            }
            if (itemMaterial == null) {
                player.sendMessage(ChatColor.RED + "Unknown item");
                return;
            }
            if (!isAdmin && this.plugin.summonBlackList.contains(itemMaterial.getId())) {
                player.sendMessage(ChatColor.RED + "Can't give that to you right now");
                return;
            }
            targetPlayer.getInventory().addItem(new ItemStack(itemMaterial, itemCount, itemDamage));
            player.sendMessage("Given " + targetPlayer.getDisplayName() + " " + itemCount + " " + itemMaterial.toString());
            this.plugin.getLogger().info("Giving " + player.getName() + " " + itemCount + " " + itemMaterial.toString());
            if ((this.plugin.summonWatchList.contains(itemMaterial.getId()) && ((itemCount > 10) || (itemCount < 1)) && !isAdmin) && !player.hasPermission("j2mc.fun.trusted")) {
                this.plugin.getServer().getPluginManager().callEvent(new MessageEvent(MessageEvent.compile("ADMININFO"), "Detecting summon of " + itemCount + " " + itemMaterial.toString() + " by " + player.getName()));
                J2MC_Manager.getCore().adminAndLog(ChatColor.LIGHT_PURPLE + "Detecting summon of " + ChatColor.WHITE + itemCount + " " + ChatColor.LIGHT_PURPLE + itemMaterial.toString() + " by " + ChatColor.WHITE + player.getName());
            }
        }
    }

    public short toWoolValue(String givenColorName) {
        if (givenColorName.equalsIgnoreCase("white")) {
            return 0;
        } else if (givenColorName.equalsIgnoreCase("orange")) {
            return 1;
        } else if (givenColorName.equalsIgnoreCase("magenta")) {
            return 2;
        } else if (givenColorName.equalsIgnoreCase("lightblue")) {
            return 3;
        } else if (givenColorName.equalsIgnoreCase("yellow")) {
            return 4;
        } else if (givenColorName.equalsIgnoreCase("lightgreen")) {
            return 5;
        } else if (givenColorName.equalsIgnoreCase("pink")) {
            return 6;
        } else if (givenColorName.equalsIgnoreCase("gray")) {
            return 7;
        } else if (givenColorName.equalsIgnoreCase("lightgray")) {
            return 8;
        } else if (givenColorName.equalsIgnoreCase("cyan")) {
            return 9;
        } else if (givenColorName.equalsIgnoreCase("purple")) {
            return 10;
        } else if (givenColorName.equalsIgnoreCase("blue")) {
            return 11;
        } else if (givenColorName.equalsIgnoreCase("brown")) {
            return 12;
        } else if (givenColorName.equalsIgnoreCase("darkgreen")) {
            return 13;
        } else if (givenColorName.equalsIgnoreCase("red")) {
            return 14;
        } else if (givenColorName.equalsIgnoreCase("black")) {
            return 15;
        }
        return 0;
    }

}
