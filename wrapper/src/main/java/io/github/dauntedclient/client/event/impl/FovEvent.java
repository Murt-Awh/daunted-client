/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FovEvent {

	public float fov;
	public final float partialTicks;

}
