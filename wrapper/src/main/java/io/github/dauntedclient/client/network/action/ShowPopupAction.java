/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.network.action;

import java.util.UUID;

import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.network.*;

public final class ShowPopupAction implements ApiAction {

	@Expose
	private UUID handle;
	@Expose
	private String text, command;
	@Expose
	private int time = 10000;

	@Override
	public void exec(PacketApi api) {
		if (text == null)
			throw new ApiUsageError("No text provided");
		if (command == null)
			throw new ApiUsageError("No command provided");

		Client.INSTANCE.getPopups().add(new Popup(text, command, time), handle);
	}

}
