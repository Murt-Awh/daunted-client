/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;


@Mixin(MinecraftClient.class)
public class DelayMixin {

    @Shadow private int attackCooldown ;

    @Inject(method = "doAttack", at = @At("HEAD"))
    private void doAttackAfter(final CallbackInfo ci) {
        attackCooldown = 0;
    }
}