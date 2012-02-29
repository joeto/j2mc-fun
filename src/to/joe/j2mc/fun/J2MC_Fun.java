package to.joe.j2mc.fun;

import org.bukkit.plugin.java.JavaPlugin;

import to.joe.j2mc.fun.command.ClearInventoryCommand;
import to.joe.j2mc.fun.command.GameModeToggleCommand;
import to.joe.j2mc.fun.command.SpawnCommand;

public class J2MC_Fun extends JavaPlugin{

    public boolean AnyoneCanChangeGameMode;
    @Override
    public void onEnable(){
        this.getConfig().options().copyDefaults(true);
        this.AnyoneCanChangeGameMode = this.getConfig().getBoolean("letanyonechangegamemode");
        
        this.getCommand("spawn").setExecutor(new SpawnCommand(this));
        this.getCommand("gm").setExecutor(new GameModeToggleCommand(this));
        this.getCommand("ci").setExecutor(new ClearInventoryCommand(this));
        
        this.getLogger().info("Fun module enabled!");
    }
    
    @Override
    public void onDisable(){
        this.getLogger().info("Fun module disabled.");
    }
    
    
}
