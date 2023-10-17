/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.event.impl.GammaEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

@Pseudo
@Mixin(targets = "net.optifine.LightMap")
public class LightMapMixin {

	@Inject(method = "updateLightmap", at = @At("HEAD"), cancellable = true)
	public void overrideGamma(World world, float torchFlickerX, int[] lmColors, boolean nightVision,
			CallbackInfoReturnable<Boolean> callback) {
		GammaEvent event = new GammaEvent(MinecraftClient.getInstance().options.gamma);
		Client.INSTANCE.getEvents().post(event);
		if (event.gamma > 1)
			callback.setReturnValue(false);
	}

}
