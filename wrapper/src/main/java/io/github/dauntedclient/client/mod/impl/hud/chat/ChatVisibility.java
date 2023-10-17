/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.hud.chat;

import net.minecraft.client.resource.language.I18n;

public enum ChatVisibility {
	SHOWN, COMMANDS, HIDDEN;

	@Override
	public String toString() {
		return I18n.translate("daunted_client.mod.chat.option.visibility." + name().toLowerCase());
	}

}
