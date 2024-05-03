package com.factions.redstoneclock.command.impl;

import com.factions.redstoneclock.RedstoneClockConstants;
import com.factions.redstoneclock.command.CustomCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RedstoneClockCommand extends CustomCommand {
    public RedstoneClockCommand() {
        super("redstoneclock", "redstoneclock.admin", false, "relogioredstone");
    }
    @Override
    protected void onCommand(CommandSender commandSender, String[] arguments) {
        if (arguments.length != 2) {
            commandSender.sendMessage("§cUtilize, /redstoneclock <nome> <quantia>");
            return;
        }

        String playerName = arguments[0];
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            commandSender.sendMessage("§cEsse jogador está offline.");
            return;
        }

        int amount = Integer.parseInt(arguments[1]);

        if (amount == 0)
            return;

        player.getInventory().addItem(RedstoneClockConstants.getRedstoneClock(amount));
        player.sendMessage("§aRecebeste " + amount + " relogios de delay!");
        commandSender.sendMessage("§aForam enviados " + amount + " relogios de delay!");
    }
}
