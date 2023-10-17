/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl;

import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.event.EventHandler;
import io.github.dauntedclient.client.event.impl.PreTickEvent;
import io.github.dauntedclient.client.mod.*;
import io.github.dauntedclient.client.mod.option.annotation.Option;
import io.github.dauntedclient.client.util.Perspective;
import io.github.dauntedclient.util.GlobalConstants;
import net.minecraft.client.option.KeyBinding;

public class TaplookMod extends DauntedClientMod {

	@Option
	private final KeyBinding key = new KeyBinding(getTranslationKey("key"), 0, GlobalConstants.KEY_CATEGORY);
	private int previousPerspective;
	private boolean active;
	@Expose
	@Option
	private Perspective perspective = Perspective.THIRD_PERSON_BACK;

	@Override
	public String getId() {
		return "taplook";
	}

	@Override
	public ModCategory getCategory() {
		return ModCategory.UTILITY;
	}

	@EventHandler
	public void onTick(PreTickEvent event) {
		if (key.isPressed()) {
			if (!active) {
				start();
			}
		} else if (active) {
			stop();
		}
	}

	public void start() {
		active = true;
		previousPerspective = mc.options.perspective;
		mc.options.perspective = perspective.ordinal();
		mc.worldRenderer.scheduleTerrainUpdate();
	}

	public void stop() {
		active = false;
		mc.options.perspective = previousPerspective;
		mc.worldRenderer.scheduleTerrainUpdate();
	}

	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

}
