/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.network;

import lombok.*;

@RequiredArgsConstructor
public class Popup {

	@Getter
	private final String text;
	@Getter
	private final String command;
	@Getter
	private final int time;
	@Getter
	private long startTime;

	public void setTime() {
		this.startTime = System.currentTimeMillis();
	}

}
