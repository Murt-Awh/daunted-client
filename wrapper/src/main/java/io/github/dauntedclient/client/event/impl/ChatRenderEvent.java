/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.hud.ChatHud;

@RequiredArgsConstructor
public class ChatRenderEvent {

	public final ChatHud chat;
	public final int ticks;
	public final float partialTicks;
	public boolean cancelled;

}
