/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.togglesprint;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.mod.keybinding.*;

public class ToggleSprintKeyBinding extends ToggledKeyBinding<ToggleSprintMod> {
	public ToggleSprintKeyBinding(ToggleSprintMod mod, String description, int keyCode, String category) {
		super(mod, description, keyCode, category);
		Client.INSTANCE.getEvents().register(this);
	}

	@Override
	public void postStateUpdate(ToggleState newState) {
		this.mod.setSprint(newState);
	}

	@Override
	public ToggleState getState() {
		return this.mod.getSprint();
	}
}
