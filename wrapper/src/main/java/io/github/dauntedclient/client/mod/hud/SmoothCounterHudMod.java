/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.hud;

import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.event.EventHandler;
import io.github.dauntedclient.client.event.impl.PostTickEvent;
import io.github.dauntedclient.client.mod.impl.DauntedClientSimpleHudMod;
import io.github.dauntedclient.client.mod.option.annotation.*;

@AbstractTranslationKey("daunted_client.mod.smooth_counter_hud")
public abstract class SmoothCounterHudMod extends DauntedClientSimpleHudMod {

	@Expose
	@Option
	private boolean smoothNumbers = true;

	public abstract int getIntValue();

	public abstract String getSuffix();

	private int counter;

	@EventHandler
	public void onTick(PostTickEvent event) {
		if (mc.world == null)
			return;

		int actualValue = getIntValue();

		if (!smoothNumbers) {
			counter = actualValue;
			return;
		}

		if (actualValue > counter) {
			counter += Math.max(((actualValue - counter) / 2), 1);
		} else if (actualValue < counter) {
			counter -= Math.max(((counter - actualValue) / 2), 1);
		}
	}

	@Override
	public String getText(boolean editMode) {
		if (editMode) {
			return "0 " + getSuffix();
		} else {
			return counter + " " + getSuffix();
		}
	}

}
