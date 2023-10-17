/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.util.data;

import net.minecraft.client.resource.language.I18n;

public enum VerticalAlignment {
	TOP, MIDDLE, BOTTOM;

	@Override
	public String toString() {
		return I18n.translate("daunted_client.vertical_alignment." + name().toLowerCase());
	}

}
