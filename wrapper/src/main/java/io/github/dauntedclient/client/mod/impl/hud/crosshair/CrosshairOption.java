/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.hud.crosshair;

import io.github.dauntedclient.client.mod.option.*;
import io.github.dauntedclient.client.ui.component.Component;
import io.github.dauntedclient.client.ui.component.controller.Controller;
import io.github.dauntedclient.client.util.data.PixelMatrix;

public class CrosshairOption extends ModOption<PixelMatrix> {

	CrosshairMod mod;

	public CrosshairOption(CrosshairMod mod) {
		super(mod.getTranslationKey("option.pixels"), ModOptionStorage.of(PixelMatrix.class, () -> mod.pixels));
		this.mod = mod;
	}

	@Override
	public Component createComponent() {
		Component container = createDefaultComponent(190, false);
		container.add(new CrosshairEditorDialog(this), Controller.none());
		return container;
	}

}
