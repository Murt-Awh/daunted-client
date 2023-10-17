/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.event.impl.ItemEntityRenderEvent;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.entity.ItemEntity;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {

	@Inject(method = "method_10221", at = @At(value = "HEAD"), cancellable = true)
	public void preItemEntityRender(ItemEntity itemEntity, double x, double y, double z, float tickDelta,
			BakedModel model, CallbackInfoReturnable<Integer> callback) {
		int result;
		if ((result = Client.INSTANCE.getEvents()
				.post(new ItemEntityRenderEvent(itemEntity, x, y, z, tickDelta, model)).result) != -1)
			callback.setReturnValue(result);
	}

}
