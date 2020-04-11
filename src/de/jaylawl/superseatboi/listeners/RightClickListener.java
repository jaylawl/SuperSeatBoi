package de.jaylawl.superseatboi.listeners;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.entity.SeatEntity;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class RightClickListener implements Listener {

    public RightClickListener() {
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRightClick(PlayerInteractEvent event) {

        if (
                event.getAction() != Action.RIGHT_CLICK_BLOCK ||
                        event.getPlayer().getGameMode() == GameMode.SPECTATOR
        ) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }

        Bisected bisected = ((Bisected) clickedBlock.getBlockData());
        if (bisected.getHalf() == Bisected.Half.TOP) {
            return; //mountable stairs can't be upside-down
        }

        Block controlBlock = clickedBlock.getLocation().add(new Vector(0, -1, 0)).getBlock();
        if (controlBlock.getType() != SuperSeatBoi.getControlMaterial()) {
            return; //block below seat must be of this material
        }

        Player player = event.getPlayer();
        if (player.isSneaking()) {
            return;
        }

        event.setCancelled(true);

        if (player.getVelocity().getY() > -0.08) {
            return; //prevent using seats to dodge fall damage
        }

        SeatEntity.getNew(clickedBlock.getLocation()).addPassenger(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendActionBar("§f[SNEAK] to get up§r");
            }
        }.runTaskLater(SuperSeatBoi.inst(), 1L);

    }

}
