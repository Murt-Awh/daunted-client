/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.hud.crosshair;

import net.minecraft.client.resource.language.I18n;

public enum CrosshairStyle {
	DEFAULT, NONE, DOT, PLUS, PLUS_DOT, SQUARE, SQUARE_DOT, CIRCLE, CIRCLE_DOT, FOUR_ANGLED, FOUR_ANGLED_DOT, TRIANGLE;

	@Override
	public String toString() {
		return I18n.translate("daunted_client.mod.crosshair.option.style." + name().toLowerCase());
	}

}
