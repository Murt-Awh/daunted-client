/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl;

import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.event.EventHandler;
import io.github.dauntedclient.client.event.impl.TimeEvent;
import io.github.dauntedclient.client.mod.*;
import io.github.dauntedclient.client.mod.option.annotation.*;

public class TimeChangerMod extends DauntedClientMod implements PrimaryIntegerSettingMod {

	@Expose
	@Option
	@Slider(min = 0, max = 24000, step = 1, showValue = false)
	private float time = 1000;

	@Override
	public String getId() {
		return "time_changer";
	}

	@Override
	public ModCategory getCategory() {
		return ModCategory.VISUAL;
	}

	@EventHandler
	public void onTime(TimeEvent event) {
		event.time = (long) time;
	}

	@Override
	public void decrement() {
		time = Math.max(0, time - 1000);
	}

	@Override
	public void increment() {
		time = Math.min(24000, time + 1000);
	}

}
