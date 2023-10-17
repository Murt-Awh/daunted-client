/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.dauntedclient.client.culling.Cullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {

	@Inject(method = "renderEntity(Lnet/minecraft/block/entity/BlockEntity;FI)V", at = @At("HEAD"), cancellable = true)
	public void cullTileEntity(BlockEntity blockEntity, float tickDelta, int destroyProgress, CallbackInfo callback) {
		if (((Cullable) blockEntity).isCulled()) {
			callback.cancel();
		}
	}

}
