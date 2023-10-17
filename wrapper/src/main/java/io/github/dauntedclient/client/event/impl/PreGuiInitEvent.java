/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.screen.Screen;

@RequiredArgsConstructor
public class PreGuiInitEvent {

	public final Screen screen;
	public boolean cancelled;

}
