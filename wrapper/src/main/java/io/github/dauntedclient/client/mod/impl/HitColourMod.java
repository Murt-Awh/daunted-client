/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl;

import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.event.EventHandler;
import io.github.dauntedclient.client.event.impl.HitOverlayEvent;
import io.github.dauntedclient.client.mod.*;
import io.github.dauntedclient.client.mod.option.annotation.Option;
import io.github.dauntedclient.client.util.data.Colour;

public class HitColourMod extends DauntedClientMod {

	@Expose
	@Option
	private Colour colour = new Colour(255, 0, 0, 76);

	@Override
	public String getId() {
		return "hit_colour";
	}

	@Override
	public ModCategory getCategory() {
		return ModCategory.VISUAL;
	}

	@EventHandler
	public void onHitOverlay(HitOverlayEvent event) {
		event.r = colour.getRedFloat();
		event.g = colour.getGreenFloat();
		event.b = colour.getBlueFloat();
		event.a = colour.getAlphaFloat();
	}

}
