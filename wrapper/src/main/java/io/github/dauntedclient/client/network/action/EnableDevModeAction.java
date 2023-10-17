/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.network.action;

import io.github.dauntedclient.client.network.PacketApi;

public class EnableDevModeAction implements ApiAction {

	@Override
	public void exec(PacketApi api) {
		api.enableDevMode();
	}

}
