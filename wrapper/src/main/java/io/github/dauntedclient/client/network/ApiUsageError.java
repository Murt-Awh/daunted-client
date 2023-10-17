/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.network;

public final class ApiUsageError extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public ApiUsageError(String message) {
		super(message);
	}

}
