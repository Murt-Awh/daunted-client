/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.replay.fix;

import io.github.dauntedclient.client.util.ForgeCompat;
import net.minecraft.client.render.WorldRenderer;

@Deprecated
@ForgeCompat
public class SCForgeHooksClient {

	public static boolean renderFirstPersonHand(WorldRenderer context, float partialTicks, int renderPass) {
		return false; // Always render hand
	}

}
