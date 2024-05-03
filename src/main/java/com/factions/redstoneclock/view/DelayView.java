package com.factions.redstoneclock.view;

import com.factions.redstoneclock.data.RedstoneClock;
import com.massivecraft.factions.util.ItemBuilder;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class DelayView extends View {
    DecimalFormat decimalFormat = new DecimalFormat("#.#");
    public DelayView() {
        super(3, "Alterar Delay");

        this.setCancelOnClick(true);
    }

    @Override
    protected void onRender(@NotNull ViewContext context) {
        RedstoneClock redstoneClock = context.get("redstoneclock");

        context.slot(13).onRender(render -> render.setItem(new ItemBuilder(Material.REDSTONE_COMPARATOR)
                .name("§aDelay do relógio de delay")
                .lore(
                        "§7Atualmente o seu relógio",
                        "§7de delay está com §c" + decimalFormat.format(redstoneClock.getDelay()) + "§7 segundos de delay",
                        "",
                        "§7Clique com botao esquerdo para aumentar",
                        "§7Clique com botao direito para diminuir"
                ).make()))
                .onClick(event -> {
                    if (event.isLeftClick()) {
                        double actualDelay = redstoneClock.getDelay();
                        double newDelay = actualDelay + 0.1;

                        if (newDelay > 3.1) {
                            context.getPlayer().sendMessage("§cO delay já está no máximo!");
                            return;
                        }

                        redstoneClock.setDelay(newDelay);
                        context.update();
                        return;
                    }


                    double actualDelay = redstoneClock.getDelay();
                    double newDelay = actualDelay - 0.1;


                    if (newDelay < 1.0) {
                        context.getPlayer().sendMessage("§cO delay já está no minimo!");
                        return;
                    }

                    redstoneClock.setDelay(newDelay);
                    context.update();
                });
    }
}
