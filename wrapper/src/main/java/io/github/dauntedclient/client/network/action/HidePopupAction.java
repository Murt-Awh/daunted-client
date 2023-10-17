/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.network.action;

import java.util.UUID;

import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.network.PacketApi;

public final class HidePopupAction implements ApiAction {

	@Expose
	private UUID uuid;

	@Override
	public void exec(PacketApi api) {
		if (!Client.INSTANCE.getPopups().remove(uuid) && api.isDevMode())
			PacketApi.LOGGER.warn("Tried to remove popup which wasn't present: {}", uuid);
	}

}
