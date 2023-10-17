/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendChatMessageEvent {

	public final String message;
	public boolean cancelled;

}
