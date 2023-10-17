/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.util;

import net.minecraft.client.resource.language.I18n;

public enum Perspective {
	FIRST_PERSON, THIRD_PERSON_BACK, THIRD_PERSON_FRONT;

	@Override
	public String toString() {
		return I18n.translate("daunted_client.perspective." + name().toLowerCase());
	}

}
