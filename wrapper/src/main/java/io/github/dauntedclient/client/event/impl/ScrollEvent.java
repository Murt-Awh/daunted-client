/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScrollEvent {

	public final int amount;
	public boolean cancelled;

}
