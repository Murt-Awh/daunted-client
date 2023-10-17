/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.component.controller;

import io.github.dauntedclient.client.ui.component.Component;
import io.github.dauntedclient.client.util.data.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AlignedBoundsController implements Controller<Rectangle> {

	private final Alignment xAlignment;
	private final Alignment yAlignment;
	private final Controller<Rectangle> baseController;

	public AlignedBoundsController(Alignment xAlignment, Alignment yAlignment) {
		this(Alignment.fromNullable(xAlignment), Alignment.fromNullable(yAlignment), (component, defaultBounds) -> defaultBounds);
	}

	@Override
	public Rectangle get(Component component, Rectangle defaultBounds) {
		return baseController.get(component, new Rectangle(
				xAlignment.getPosition(component.getParent().getBounds().getWidth(), defaultBounds.getWidth()),
				yAlignment.getPosition(component.getParent().getBounds().getHeight(), defaultBounds.getHeight()),
				defaultBounds.getWidth(), defaultBounds.getHeight()));
	}

}
