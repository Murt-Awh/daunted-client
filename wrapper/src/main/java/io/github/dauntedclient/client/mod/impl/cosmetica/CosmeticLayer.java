/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.cosmetica;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;

@RequiredArgsConstructor
abstract class CosmeticLayer implements FeatureRenderer<AbstractClientPlayerEntity> {

	protected final PlayerEntityRenderer parent;

	@Override
	public boolean combineTextures() {
		return false;
	}

}
