package com.factions.redstoneclock;

import com.factions.redstoneclock.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RedstoneClockConstants {
    public static ItemStack getRedstoneClock(int amount) {
        return new ItemBuilder(Material.STAINED_GLASS, 1, (short) 14)
                .name("§aRelógio de Delay")
                .lore(
                        "§7Com este bloco o seu canhão",
                        "§7atirará automaticamente"
                )
                .amount(amount).make();
    }
}
