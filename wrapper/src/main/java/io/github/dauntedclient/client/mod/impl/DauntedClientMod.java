/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl;

import java.nio.file.Path;

import org.apache.logging.log4j.Logger;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.mod.Mod;
import lombok.*;
import net.minecraft.client.MinecraftClient;

/**
 * Base class for built-in mods. Adds some handy stuff.
 */
public abstract class DauntedClientMod extends Mod {

	protected final Logger logger = getLogger();
	protected final MinecraftClient mc = MinecraftClient.getInstance();
	@Getter
	@Setter
	private int index = -1;

	public String getTranslationKey(String key) {
		return "daunted_client.mod." + getId() + '.' + key;
	}

	@Override
	public boolean isEnabledByDefault() {
		return false;
	}

	@Override
	public Path getConfigFolder() {
		return Client.INSTANCE.getConfigFolder();
	}

}
