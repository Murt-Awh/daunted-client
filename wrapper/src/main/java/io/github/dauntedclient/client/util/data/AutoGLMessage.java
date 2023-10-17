/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.util.data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AutoGLMessage {
	GLHF("glhf"), GL("gl"), GOOD_LUCK("good luck!"), GOOD_LUCK_HAVE_FUN("good luck, have fun!");

	private final String message;

	@Override
	public String toString() {
		return message;
	}

}
