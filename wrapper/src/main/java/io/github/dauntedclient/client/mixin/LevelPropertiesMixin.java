/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.event.impl.TimeEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.level.LevelProperties;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin {

	private final MinecraftClient mc = MinecraftClient.getInstance();

	@Inject(method = "getTimeOfDay", at = @At("HEAD"), cancellable = true)
	public void overrideWorldTime(CallbackInfoReturnable<Long> callback) {
		if (mc.world != null && (Object) this == mc.world.getLevelProperties()) {
			callback.setReturnValue(Client.INSTANCE.getEvents().post(new TimeEvent(timeOfDay)).time);
		}
	}

	@Shadow
	private long timeOfDay;

}
