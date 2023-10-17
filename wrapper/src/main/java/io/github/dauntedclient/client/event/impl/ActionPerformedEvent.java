/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

@RequiredArgsConstructor
public class ActionPerformedEvent {

	public final Screen gui;
	public final ButtonWidget button;
	public boolean cancelled;

}
