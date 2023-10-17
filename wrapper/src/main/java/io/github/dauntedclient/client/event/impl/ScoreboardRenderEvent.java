/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.util.Window;
import net.minecraft.scoreboard.ScoreboardObjective;

@RequiredArgsConstructor
public class ScoreboardRenderEvent {

	public final ScoreboardObjective objective;
	public final Window window;
	public boolean cancelled;

}
