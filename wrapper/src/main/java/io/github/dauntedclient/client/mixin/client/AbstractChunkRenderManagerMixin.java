/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.event.impl.PreRenderChunkEvent;
import net.minecraft.client.render.world.AbstractChunkRenderManager;
import net.minecraft.client.world.BuiltChunk;

@Mixin(AbstractChunkRenderManager.class)
public class AbstractChunkRenderManagerMixin {

	@Inject(method = "method_9770", at = @At("RETURN"))
	public void preRenderChunk(BuiltChunk helper, CallbackInfo ci) {
		Client.INSTANCE.getEvents().post(new PreRenderChunkEvent(helper));
	}

}
