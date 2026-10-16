package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;

import java.util.Locale;

public class ExampleModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            if (client.player == null || client.world == null) {
                return;
            }

            float myHealth = client.player.getHealth();

            drawContext.drawText(
                    client.textRenderer,
                    Text.literal("My Health: " + formatHealth(myHealth)),
                    8, 8, 0xFFFFFF, true
            );

            if (client.targetedEntity instanceof LivingEntity target
                    && target.isAlive()) {

                float targetHealth = target.getHealth();
                float difference = myHealth - targetHealth;

                drawContext.drawText(
                        client.textRenderer,
                        Text.literal("Target Health: " + formatHealth(targetHealth)),
                        8, 20, 0xFF5555, true
                );

                drawContext.drawText(
                        client.textRenderer,
                        Text.literal("Can Farki: " + formatHealth(difference)),
                        8, 32,
                        difference >= 0 ? 0x55FF55 : 0xFF5555,
                        true
                );
            } else {
                drawContext.drawText(
                        client.textRenderer,
                        Text.literal("Target Health: --"),
                        8, 20, 0xAAAAAA, true
                );

                drawContext.drawText(
                        client.textRenderer,
                        Text.literal("Can Farki: --"),
                        8, 32, 0xAAAAAA, true
                );
            }
        });
    }

    private static String formatHealth(float health) {
        return String.format(Locale.ROOT, "%.1f", health);
    }
}
