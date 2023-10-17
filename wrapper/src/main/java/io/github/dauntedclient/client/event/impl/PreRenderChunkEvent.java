/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.AllArgsConstructor;
import net.minecraft.client.world.BuiltChunk;

@AllArgsConstructor
public class PreRenderChunkEvent {

	public final BuiltChunk chunk;

}
