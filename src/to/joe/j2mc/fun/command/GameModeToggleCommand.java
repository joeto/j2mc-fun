package to.joe.j2mc.fun.command;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import to.joe.j2mc.core.J2MC_Manager;
import to.joe.j2mc.core.command.MasterCommand;
import to.joe.j2mc.fun.J2MC_Fun;

public class GameModeToggleCommand extends MasterCommand{

    J2MC_Fun plugin;
    
    public GameModeToggleCommand(J2MC_Fun fun){
        super(fun);
        this.plugin = fun;
    }
    
    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        if(isPlayer){
            if(plugin.AnyoneCanChangeGameMode){
                if(player.getGameMode().equals(GameMode.SURVIVAL)){
                    player.setGameMode(GameMode.CREATIVE);
                } else{
                    player.setGameMode(GameMode.SURVIVAL);
                }
                plugin.getLogger().info(player.getName() + " changed game mode to " + player.getGameMode().toString());
            }else{
                if(J2MC_Manager.getPermissions().isAdmin(player.getName())){
                    if(player.getGameMode().equals(GameMode.SURVIVAL)){
                        player.setGameMode(GameMode.CREATIVE);
                    } else{
                        player.setGameMode(GameMode.SURVIVAL);
                    }
                    plugin.getLogger().info(player.getName() + " changed game mode to " + player.getGameMode().toString());
                }
            }
        }
    }
}
