/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.replay;

import net.minecraft.client.resource.language.I18n;

public enum SCCameraType {
	CLASSIC, VANILLA_ISH;

	@Override
	public String toString() {
		return I18n.translate("daunted_client.mod.replay.option.camera." + name().toLowerCase());
	}

}
