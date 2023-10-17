/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.extension;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

public interface MinecraftClientExtension {

	static MinecraftClientExtension getInstance() {
		return (MinecraftClientExtension) MinecraftClient.getInstance();
	}

	ServerInfo getPreviousServer();

}
