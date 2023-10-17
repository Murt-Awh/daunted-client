/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.util.data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AutoGGMessage {
	GG("gg"), GF("gf"), GOOD_GAME("good game"), GOOD_FIGHT("good fight");

	private final String message;

	@Override
	public String toString() {
		return message;
	}

}
