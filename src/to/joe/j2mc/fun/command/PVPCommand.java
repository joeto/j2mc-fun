package to.joe.j2mc.fun.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import to.joe.j2mc.core.J2MC_Manager;
import to.joe.j2mc.core.command.MasterCommand;
import to.joe.j2mc.core.event.MessageEvent;
import to.joe.j2mc.core.exceptions.BadPlayerMatchException;
import to.joe.j2mc.fun.J2MC_Fun;

public class PVPCommand extends MasterCommand<J2MC_Fun> {
    private final String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "PvP" + ChatColor.DARK_AQUA + "] " + ChatColor.RESET;
    private final String listMsg = this.prefix + "There are currently " + ChatColor.BOLD + "%s" + ChatColor.RESET + " users with PvP enabled.";

    public PVPCommand(J2MC_Fun plugin) {
        super(plugin);
    }

    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Nope! /pvp <playername/list>");
            return;
        }
        if (args[0].equalsIgnoreCase("list")) {
            final StringBuilder sb = new StringBuilder();
            for (final String user : this.plugin.pvpEnabled) {
                sb.append(user).append(", ");
            }
            if (sb.length() == 0) {
                sender.sendMessage(String.format(this.listMsg, "0"));
            } else {
                sb.setLength(sb.length() - 2);
                sender.sendMessage(String.format(this.listMsg, this.plugin.pvpEnabled.size()));
                sender.sendMessage(this.prefix + sb.toString());
            }
        } else {
            try {
                final Player target = J2MC_Manager.getVisibility().getPlayer(args[0], sender);
                final String name = target.getName();
                String message = this.prefix + sender.getName() + " ";
                if (this.plugin.pvpEnabled.contains(name)) {
                    this.plugin.pvpEnabled.remove(name);
                    message += "disables PvP for " + name;
                } else {
                    this.plugin.pvpEnabled.add(name);
                    message += "enables PvP for " + name;
                }
                J2MC_Manager.getCore().adminAndLog(message);
                this.plugin.getServer().getPluginManager().callEvent(new MessageEvent(MessageEvent.compile("ADMININFO"), message));
            } catch (final BadPlayerMatchException e) {
                sender.sendMessage(this.prefix + e.getMessage());
            }
        }
    }

}
