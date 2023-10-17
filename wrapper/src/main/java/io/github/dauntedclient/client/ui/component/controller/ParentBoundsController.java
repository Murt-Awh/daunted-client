/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.component.controller;

import io.github.dauntedclient.client.ui.component.Component;
import io.github.dauntedclient.client.util.data.Rectangle;

public class ParentBoundsController implements Controller<Rectangle> {

	@Override
	public Rectangle get(Component component, Rectangle defaultBounds) {
		return component.getParent().getBounds();
	}

}
