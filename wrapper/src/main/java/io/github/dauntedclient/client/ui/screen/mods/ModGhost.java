/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.screen.mods;

import io.github.dauntedclient.client.ui.component.impl.BlockComponent;
import io.github.dauntedclient.client.util.data.Rectangle;

public class ModGhost extends BlockComponent {

	public ModGhost() {
		super(theme.buttonSecondary.add(10), 4, 0);
	}

	@Override
	protected Rectangle getDefaultBounds() {
		return Rectangle.ofDimensions(230, 30);
	}

}
