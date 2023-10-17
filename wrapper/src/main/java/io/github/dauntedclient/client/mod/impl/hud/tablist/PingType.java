/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.hud.tablist;

import net.minecraft.client.resource.language.I18n;

public enum PingType {
	NONE, ICON, NUMERAL;

	@Override
	public String toString() {
		return I18n.translate("daunted_client.mod.tab_list.option.pingType." + name().toLowerCase());
	}

}
