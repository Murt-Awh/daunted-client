/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.RequiredArgsConstructor;
import net.minecraft.entity.Entity;

@RequiredArgsConstructor
public class HitboxRenderEvent {

	public final Entity entity;
	public final double x;
	public final double y;
	public final double z;
	public final float entityYaw;
	public final float partialTicks;
	public boolean cancelled;

}
