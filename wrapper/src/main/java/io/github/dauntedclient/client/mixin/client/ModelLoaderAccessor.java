/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.BlockModel;

@Mixin(ModelLoader.class)
public interface ModelLoaderAccessor {

	@Invoker("method_10386")
	BakedModel bakeBlockModel(BlockModel model, ModelRotation rotation, boolean uvLocked);

}
