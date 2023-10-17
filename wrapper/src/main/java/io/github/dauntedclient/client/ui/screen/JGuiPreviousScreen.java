/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.screen;

import com.replaymod.lib.de.johni0702.minecraft.gui.container.GuiScreen;

import net.minecraft.client.gui.screen.Screen;

public class JGuiPreviousScreen extends Screen {

	private GuiScreen previous;

	public JGuiPreviousScreen(GuiScreen previous) {
		this.previous = previous;
	}

	@Override
	public void init() {
		super.init();
		previous.display();
	}

}
