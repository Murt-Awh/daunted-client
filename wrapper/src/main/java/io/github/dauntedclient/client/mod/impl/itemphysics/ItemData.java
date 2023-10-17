/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.itemphysics;

public interface ItemData {

	long getLastUpdate();

	void setLastUpdate(long lastUpdate);

	float getRotation();

	void setRotation(float rotation);

}
