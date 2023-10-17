/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod;

import io.github.dauntedclient.client.mod.impl.DauntedClientMod;

/**
 * Represents a mod that cannot be disabled.
 */
public abstract class ConfigOnlyMod extends DauntedClientMod {

	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

}
