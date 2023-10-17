/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.replay;

import net.minecraft.client.resource.language.I18n;

public enum SCInterpolatorType {
	CATMULL, CUBIC, LINEAR;

	@Override
	public String toString() {
		return I18n.translate("daunted_client.mod.replay.interpolator." + name().toLowerCase());
	}

}
