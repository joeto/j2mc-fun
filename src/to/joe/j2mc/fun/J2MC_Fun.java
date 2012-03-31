package to.joe.j2mc.fun;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import to.joe.j2mc.fun.command.ClearInventoryCommand;
import to.joe.j2mc.fun.command.ItemCommand;

public class J2MC_Fun extends JavaPlugin {

    public List<Integer> BlockedForTrusted;
    public List<Integer> BlockedForNormals;
    public List<Integer> SummonBlackList;
    public List<Integer> SummonWatchList;

    @Override
    public void onDisable() {
        this.getLogger().info("Fun module disabled.");
    }

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.BlockedForTrusted = this.getConfig().getIntegerList("blockfortrusted");
        this.BlockedForNormals = this.getConfig().getIntegerList("blockfornormals");
        this.SummonBlackList = this.getConfig().getIntegerList("summonblacklist");
        this.SummonWatchList = this.getConfig().getIntegerList("summonwatchlist");

        this.getCommand("ci").setExecutor(new ClearInventoryCommand(this));
        this.getCommand("item").setExecutor(new ItemCommand(this));

        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        this.getLogger().info("Fun module enabled!");
    }

}
