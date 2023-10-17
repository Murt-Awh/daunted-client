/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import java.nio.IntBuffer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.*;

import net.minecraft.client.texture.TextureUtil;

@Mixin(TextureUtil.class)
public interface TextureUtilAccessor {

	@Accessor("BUFFER")
	static IntBuffer getBuffer() {
		throw new UnsupportedOperationException();
	}

	@Invoker("method_5866")
	static void copyToBuffer(int[] array, int length) {
		throw new UnsupportedOperationException();
	}

}
