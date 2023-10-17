/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.lwjgl;

import org.lwjgl.opengl.GLContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GLContext.class)
public interface GLContextAccessor {

	@Invoker("getFunctionAddress")
	static long getFunctionAddress(String name) {
		throw new UnsupportedOperationException();
	}

	@Invoker("ngetFunctionAddress")
	static long ngetFunctionAddress(long name) {
		throw new UnsupportedOperationException();
	}

}
