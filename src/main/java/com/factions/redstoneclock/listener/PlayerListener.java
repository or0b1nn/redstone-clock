package com.factions.redstoneclock.listener;

import com.factions.redstoneclock.RedstoneClockConstants;
import com.factions.redstoneclock.RedstoneClockPlugin;
import com.factions.redstoneclock.data.RedstoneClock;
import com.factions.redstoneclock.view.DelayView;
import com.google.common.collect.ImmutableMap;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

public class PlayerListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItemInHand();

        if (itemStack == null) return;
        if (itemStack.getType() == Material.AIR) return;
        if (!itemStack.isSimilar(RedstoneClockConstants.getRedstoneClock(0))) return;

        MPlayer mPlayer = MPlayer.get(player);

        if (!mPlayer.hasFaction()) {
            player.sendMessage("§cPrecisas de uma facção para utilizar este item!");
            event.setCancelled(true);
            return;
        }

        Location location = event.getBlock().getLocation();
        String factionTag = mPlayer.getFactionTag();

        RedstoneClock redstoneClock = new RedstoneClock(location, factionTag, 1, TimeUnit.SECONDS.toMillis((long)1.0));
        RedstoneClockPlugin.getInstance().getRedstoneClockCache().register(redstoneClock);

        player.sendMessage("§aRelógio de Delay colocado com sucesso!");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        MPlayer mPlayer = MPlayer.get(player);

        if (!mPlayer.hasFaction()) return;

        Block block = event.getBlock();
        RedstoneClock redstoneClock = RedstoneClockPlugin.getInstance().getRedstoneClockCache().getByLocation(block.getLocation());

        if (redstoneClock == null) return;
        if (!redstoneClock.getFactionsTag().equalsIgnoreCase(mPlayer.getFactionTag()) && !player.hasPermission("redstoneclock.admin")) {
            player.sendMessage("§cEste relógio de delay não é da tua facção!");
            event.setCancelled(true);
            return;
        }

        RedstoneClockPlugin.getInstance().getRedstoneClockCache().remove(redstoneClock);
        player.sendMessage("§aRelógio de Delay retirado com sucesso!");
     }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Location location = block.getLocation();

        RedstoneClock redstoneClock = RedstoneClockPlugin.getInstance().getRedstoneClockCache().getByLocation(location);

        MPlayer mPlayer = MPlayer.get(player);

        if (!mPlayer.hasFaction()) return;

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (redstoneClock == null) return;
        if (!redstoneClock.getFactionsTag().equalsIgnoreCase(mPlayer.getFactionTag()) && !player.hasPermission("redstoneclock.admin")) {
            player.sendMessage("§cEste relógio de delay não é da tua facção!");
            event.setCancelled(true);
            return;
        }

        RedstoneClockPlugin.getInstance().getViewFrame().open(DelayView.class, player, ImmutableMap.of("redstoneclock", redstoneClock));
     }
}
