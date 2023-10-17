/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.RequiredArgsConstructor;
import net.minecraft.util.hit.BlockHitResult;

@RequiredArgsConstructor
public class BlockHighlightRenderEvent {

	public final BlockHitResult hit;
	public final float partialTicks;
	public boolean cancelled;

}
