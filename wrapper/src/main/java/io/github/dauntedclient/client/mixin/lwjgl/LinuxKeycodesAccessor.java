/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.lwjgl;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "org.lwjgl.opengl.LinuxKeycodes")
public interface LinuxKeycodesAccessor {

	@Invoker(value = "mapKeySymToLWJGLKeyCode", remap = false)
	static int mapKeySymToLWJGLKeyCode(long keysym) {
		throw new UnsupportedOperationException();
	}

}
