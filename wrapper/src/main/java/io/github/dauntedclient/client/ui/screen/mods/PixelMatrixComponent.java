/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.screen.mods;

import org.lwjgl.nanovg.NanoVG;

import io.github.dauntedclient.client.ui.component.*;
import io.github.dauntedclient.client.util.data.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class PixelMatrixComponent extends Component {

	private final PixelMatrix pixels;

	@Override
	protected Rectangle getDefaultBounds() {
		return Rectangle.ofDimensions(pixels.getWidth(), pixels.getHeight());
	}

	@Override
	public void render(ComponentRenderInfo info) {
		NanoVG.nvgBeginPath(nvg);
		pixels.nvgBind(nvg, 0, 0, -1, 0);
		NanoVG.nvgRect(nvg, 0, 0, pixels.getWidth(), pixels.getHeight());
		NanoVG.nvgFill(nvg);
		super.render(info);
	}

}
