/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.network.action;

import java.util.*;

import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.mod.Mod;
import io.github.dauntedclient.client.network.*;

public final class BlockModsAction implements ApiAction {

	@Expose
	private Map<String, Boolean> mods;

	@Override
	public void exec(PacketApi api) {
		if (mods == null)
			throw new ApiUsageError("No mods provided to block");

		mods.forEach((key, value) -> {
			Optional<Mod> modOpt = Client.INSTANCE.getMods().getById(key);
			if (!modOpt.isPresent()) {
				if (api.isDevMode())
					PacketApi.LOGGER.warn("Server tried to block mod with id " + key);

				return;
			}

			Mod mod = modOpt.get();

			if (value)
				mod.block();
			else
				mod.unblock();
		});
	}

}
