/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.replay.fix;

import io.github.dauntedclient.client.util.ForgeCompat;

@Deprecated
@ForgeCompat
public class SCFMLCommonHandler {

	private static final SCFMLCommonHandler INSTANCE = new SCFMLCommonHandler();

	public static SCFMLCommonHandler instance() {
		return INSTANCE;
	}

	public void onRenderTickStart(float partialTicks) {
		// Stub
	}

	public void onRenderTickEnd(float partialTicks) {
		// Stub
	}

}
