/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.component.controller;

import io.github.dauntedclient.client.mod.impl.DauntedClientConfig;
import io.github.dauntedclient.client.ui.component.Component;
import io.github.dauntedclient.client.util.data.Colour;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnimatedColourController implements Controller<Colour> {

	private final Controller<Colour> base;
	private final int duration;
	private Colour last;
	private long currentTime;
	private Colour current;

	public AnimatedColourController(Controller<Colour> base) {
		this(base, 200);
	}

	@Override
	public Colour get(Component component, Colour defaultValue) {
		Colour baseValue = base.get(component, defaultValue);
		if (!baseValue.equals(current)) {
			last = current;
			current = baseValue;
			currentTime = System.currentTimeMillis();
		}

		return animate(Math.max(0, (System.currentTimeMillis() - currentTime) / ((float) duration)));
	}

	public Colour animate(float progress) {
		if (last == null || progress == 1)
			return current;

		return last.lerp(current, progress);
	}

}
