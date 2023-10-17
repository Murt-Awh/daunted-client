/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import io.github.dauntedclient.client.DetectedServer;
import lombok.AllArgsConstructor;
import net.minecraft.client.network.ServerInfo;

@AllArgsConstructor
public class ServerConnectEvent {

	public final ServerInfo info;
	public final DetectedServer server;

}
