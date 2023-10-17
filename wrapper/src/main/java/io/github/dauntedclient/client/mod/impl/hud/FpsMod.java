/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.hud;

import io.github.dauntedclient.client.mod.hud.SmoothCounterHudMod;
import net.minecraft.client.MinecraftClient;

public class FpsMod extends SmoothCounterHudMod {

	@Override
	public String getId() {
		return "fps";
	}

	@Override
	public int getIntValue() {
		return MinecraftClient.getCurrentFps();
	}

	@Override
	public String getSuffix() {
		return "FPS";
	}

}
