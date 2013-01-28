package to.joe.j2mc.fun;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventListener implements Listener {

    J2MC_Fun plugin;

    public EventListener(J2MC_Fun fun) {
        this.plugin = fun;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        if (!this.plugin.blockDamage) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();
            if (!this.plugin.pvpEnabled.contains(player.getName())) {
                if (this.plugin.imitateAlpha) {
                    event.setDamage(0);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        final String check = this.check(event.getPlayer(), event.getBlock().getTypeId());
        if (check != null) {
            event.getPlayer().sendMessage(ChatColor.RED + check);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            final String check = this.check(event.getPlayer(), event.getPlayer().getItemInHand().getTypeId());
            if (check != null) {
                event.getPlayer().sendMessage(ChatColor.RED + check);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent event) {
        if (event.getItem().getTypeId() == 383) {
            final BlockState state = event.getBlock().getState();
            if (state instanceof Dispenser) {
                final Dispenser dispenser = (Dispenser) state;
                dispenser.getInventory().remove(383);
            }
            event.setCancelled(true);
        }
    }

    private String check(Player player, int blockid) {
        final boolean isAdmin = player.hasPermission("j2mc.fun.admin");
        final boolean isTrusted = player.hasPermission("j2mc.fun.trusted");

        if (this.plugin.blockedForNormals.contains(blockid)) {
            if (!isAdmin && !isTrusted) {
                if (this.plugin.blockedForTrusted.contains(blockid)) {
                    return "You can't use that item";
                } else {
                    return "You need to be trusted to use that item";
                }
            }
        }
        if (this.plugin.blockedForTrusted.contains(blockid)) {
            if (isTrusted && !isAdmin) {
                return "Even trusted have their limits, you can't use that item";
            }
            if (!isAdmin && !isTrusted) {
                return "You can't use that item";
            }
        }
        return null;
    }

}
