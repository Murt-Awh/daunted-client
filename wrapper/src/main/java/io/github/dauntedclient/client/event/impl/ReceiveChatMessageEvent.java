/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReceiveChatMessageEvent {

	public final boolean actionBar;
	public final String message;
	/**
	 * Whether the event is fired from the replay mod.
	 */
	public final boolean replay;
	public boolean cancelled;

}
