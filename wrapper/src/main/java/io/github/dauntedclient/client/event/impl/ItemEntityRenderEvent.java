/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.entity.ItemEntity;

@RequiredArgsConstructor
public class ItemEntityRenderEvent {

	public boolean cancelled;
	public final ItemEntity entity;
	public final double x;
	public final double y;
	public final double z;
	public final float partialTicks;
	public final BakedModel model;
	public int result = -1;

}
