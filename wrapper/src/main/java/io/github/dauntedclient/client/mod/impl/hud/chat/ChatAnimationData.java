/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.hud.chat;

public interface ChatAnimationData {

	float getTransparency();

	void setTransparency(float transparency);

	float getLastTransparency();

	void setLastTransparency(float transparency);

}
