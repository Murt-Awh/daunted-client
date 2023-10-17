/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;

@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {

	@Invoker("renderBakedItemModel")
	void renderBakedModel(BakedModel model, int colour);

}
