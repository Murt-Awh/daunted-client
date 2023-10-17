/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.lwjgl;

import org.lwjgl.system.FunctionProvider;
import org.spongepowered.asm.mixin.*;

import io.github.dauntedclient.client.util.Lwjgl2FunctionProvider;

@Mixin(targets = "org.lwjgl.nanovg.NanoVGGLConfig")
public class NanoVGGLConfigMixin {

	@Overwrite(remap = false)
	private static FunctionProvider getFunctionProvider(String className) {
		return new Lwjgl2FunctionProvider();
	}

}
