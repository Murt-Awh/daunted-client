/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.event.impl.TransformFirstPersonItemEvent;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

	@Shadow
	private ItemStack mainHand;

	@Inject(method = "applyEquipAndSwingOffset", at = @At("HEAD"))
	public void applyEquipAndSwingOffset(float equipProgress, float swingProgress, CallbackInfo callback) {
		Client.INSTANCE.getEvents().post(new TransformFirstPersonItemEvent(mainHand, equipProgress, swingProgress));
	}

}
