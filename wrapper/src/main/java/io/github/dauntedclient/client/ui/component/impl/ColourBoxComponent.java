/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.component.impl;

import org.lwjgl.nanovg.NanoVG;

import io.github.dauntedclient.client.ui.component.ComponentRenderInfo;
import io.github.dauntedclient.client.ui.component.controller.*;
import io.github.dauntedclient.client.util.data.*;

public class ColourBoxComponent extends ColouredComponent {

	public ColourBoxComponent(Controller<Colour> colour) {
		super(colour);
	}

	@Override
	protected Rectangle getDefaultBounds() {
		return Rectangle.ofDimensions(16, 16);
	}

	@Override
	public void render(ComponentRenderInfo info) {
		NanoVG.nvgBeginPath(nvg);
		NanoVG.nvgFillColor(nvg, getColour().nvg());
		NanoVG.nvgCircle(nvg, getBounds().getWidth() / 2, getBounds().getHeight() / 2, getBounds().getWidth() / 2);
		NanoVG.nvgFill(nvg);


		if (getColour().needsOutline(theme.bg)) {
			NanoVG.nvgStrokeColor(nvg, theme.fg.nvg());
			NanoVG.nvgStrokeWidth(nvg, 1);
			NanoVG.nvgStroke(nvg);
		}

		super.render(info);
	}

}
