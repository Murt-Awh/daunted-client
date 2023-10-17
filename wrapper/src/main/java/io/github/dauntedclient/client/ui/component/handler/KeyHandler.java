/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.component.handler;

import io.github.dauntedclient.client.ui.component.ComponentRenderInfo;

@FunctionalInterface
public interface KeyHandler {

	boolean onKey(ComponentRenderInfo info, int keyCode, char character);

}
