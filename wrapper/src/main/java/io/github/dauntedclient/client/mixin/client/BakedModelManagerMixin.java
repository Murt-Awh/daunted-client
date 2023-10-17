/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import io.github.dauntedclient.client.util.MinecraftUtils;
import net.minecraft.client.render.block.BlockModelShapes;
import net.minecraft.client.render.model.*;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.resource.ResourceManager;

@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin {

	@Redirect(method = "reload", at = @At(value = "NEW", target = "net/minecraft/client/render/model/ModelLoader"))
	public ModelLoader captureModelBakery(ResourceManager resourceManager, SpriteAtlasTexture atlas,
			BlockModelShapes blockModelShapes) {
		return MinecraftUtils.modelLoader = new ModelLoader(resourceManager, atlas, blockModelShapes);
	}

}
