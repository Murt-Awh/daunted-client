/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.component.impl;

import io.github.dauntedclient.client.ui.component.Component;
import io.github.dauntedclient.client.ui.component.controller.AlignedBoundsController;
import io.github.dauntedclient.client.util.data.*;

public class ListComponent extends Component {

	public void add(Component component) {
		add(getSubComponents().size(), component);
	}

	public void add(int index, Component component) {
		add(index, component,
				new AlignedBoundsController(Alignment.CENTRE, Alignment.START, (sizingComponent, defaultBounds) -> {
					Component previous = null;
					Rectangle lastBounds = null;

					int prevIndex = subComponents.indexOf(component) - 1;
					if (prevIndex > -1) {
						previous = subComponents.get(prevIndex);
						lastBounds = previous.getCachedBounds();
					}

					return new Rectangle(defaultBounds.getX(),
							previous == null ? 0 : lastBounds.getY() + lastBounds.getHeight() + getSpacing(),
							defaultBounds.getWidth(), defaultBounds.getHeight());
				}));
	}

	protected int getContentHeight() {
		if (subComponents.isEmpty())
			return 0;

		return getBounds(subComponents.get(subComponents.size() - 1)).getEndY();
	}

	@Override
	protected boolean shouldCull(Component component) {
		return component.getBounds().getEndY() < 0 || component.getBounds().getY() > getBounds().getHeight();
	}

	public int getSpacing() {
		return 5;
	}

}
