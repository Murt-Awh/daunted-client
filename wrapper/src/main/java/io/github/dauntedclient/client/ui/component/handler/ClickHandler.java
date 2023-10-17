/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.component.handler;

import io.github.dauntedclient.client.ui.component.ComponentRenderInfo;

@FunctionalInterface
public interface ClickHandler {

	boolean onClick(ComponentRenderInfo info, int button);

}
