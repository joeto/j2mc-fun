package to.joe.j2mc.fun.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import to.joe.j2mc.core.J2MC_Manager;
import to.joe.j2mc.core.command.MasterCommand;
import to.joe.j2mc.core.exceptions.BadPlayerMatchException;
import to.joe.j2mc.fun.J2MC_Fun;

public class SpawnCommand extends MasterCommand{

    public SpawnCommand(J2MC_Fun fun){
        super(fun);
    }
    
    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        if (!isPlayer || player.hasPermission("j2mc.fun")) {
            if (isPlayer && (!J2MC_Manager.getPermissions().hasFlag(player.getName(), 'a') || (args.length < 1))) {
                player.sendMessage(ChatColor.RED + "WHEEEEEEEEEEEEEEE");
                player.teleport(player.getWorld().getSpawnLocation());
            } else if (args.length == 1) {
                Player target;
                try{
                    target = J2MC_Manager.getVisibility().getPlayer(args[0], sender);
                }catch(BadPlayerMatchException e){
                    player.sendMessage(ChatColor.RED + e.getMessage());
                    return;
                }
                target.teleport(target.getWorld().getSpawnLocation());
                target.sendMessage(ChatColor.RED + "OH GOD I'M BEING PULLED TO SPAWN OH GOD");
                J2MC_Manager.getCore().adminAndLog(ChatColor.RED + player.getName() + " pulled " + target.getName() + " to spawn");
                } else {
                    sender.sendMessage(ChatColor.RED + "No such player, or matches multiple");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /spawn playername");
            }
    }
}
