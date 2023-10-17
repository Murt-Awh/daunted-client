/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.togglesprint;

import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.mod.ModCategory;
import io.github.dauntedclient.client.mod.impl.DauntedClientSimpleHudMod;
import io.github.dauntedclient.client.mod.keybinding.ToggleState;
import io.github.dauntedclient.client.mod.option.annotation.Option;
import io.github.dauntedclient.client.util.MinecraftUtils;

public class ToggleSprintMod extends DauntedClientSimpleHudMod {

	private ToggleState sprint;
	@Expose
	@Option
	private boolean hud;

	private ToggleSprintKeyBinding keybinding;

	@Override
	public void init() {
		super.init();

		MinecraftUtils.unregisterKeyBinding(mc.options.sprintKey);
		keybinding = new ToggleSprintKeyBinding(this, mc.options.sprintKey.getTranslationKey(), 29,
				mc.options.sprintKey.getCategory());
		mc.options.sprintKey = keybinding;
		MinecraftUtils.registerKeyBinding(keybinding);
	}

	@Override
	public String getId() {
		return "toggle_sprint";
	}

	@Override
	public ModCategory getCategory() {
		return ModCategory.UTILITY;
	}

	@Override
	public boolean isVisible() {
		return hud;
	}

	@Override
	public String getText(boolean editMode) {
		if (!hud) {
			return null;
		}
		if (editMode) {
			return keybinding.getText(true);
		}
		return getSprint() == null ? null : keybinding.getText(false);
	}

	public ToggleState getSprint() {
		return sprint;
	}

	public void setSprint(ToggleState sprint) {
		this.sprint = sprint;
	}

}
