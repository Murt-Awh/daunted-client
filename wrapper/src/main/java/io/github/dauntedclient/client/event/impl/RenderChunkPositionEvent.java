/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.AllArgsConstructor;
import net.minecraft.client.world.BuiltChunk;
import net.minecraft.util.math.BlockPos;

@AllArgsConstructor
public class RenderChunkPositionEvent {

	public final BuiltChunk chunk;
	public final BlockPos position;

}
