/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.hud.armour;

import net.minecraft.client.resource.language.I18n;

public enum DurabilityDisplay {
	OFF, FRACTION, REMAINING, PERCENTAGE;

	@Override
	public String toString() {
		return I18n.translate("daunted_client.mod.armour.option.durability." + name().toLowerCase());
	}

	public int getWidth() {
		switch (this) {
			case FRACTION:
				return 67;
			case REMAINING:
				return 29;
			case PERCENTAGE:
				return 30;
			default:
				return 0;
		}
	}

}
