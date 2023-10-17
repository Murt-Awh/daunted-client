/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import java.util.Collection;

import lombok.AllArgsConstructor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

@AllArgsConstructor
public class PostGuiInitEvent {

	public final Screen screen;
	public final Collection<ButtonWidget> buttonList;

}
