/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.network.action;

import com.google.gson.*;

import io.github.dauntedclient.client.network.*;

public interface ApiAction {

	public static ApiAction createAction(String id, JsonObject inputs) {
		ApiAction action = createAction(id);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(action.getClass(), (InstanceCreator<?>) (ignored) -> action).create();
		return gson.fromJson(inputs, action.getClass());
	}

	public static ApiAction createAction(String id) {
		switch (id) {
			case "block_mods":
				return new BlockModsAction();
			case "show_popup":
				return new ShowPopupAction();
			case "hide_popup":
				return new HidePopupAction();
			case "enable_dev_mode":
				return new EnableDevModeAction();
		}

		throw new ApiUsageError("Invalid action: " + id);
	}

	void exec(PacketApi api);

}
