/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.component.impl;

import io.github.dauntedclient.client.ui.component.Component;
import io.github.dauntedclient.client.ui.component.controller.Controller;
import io.github.dauntedclient.client.util.data.Colour;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ColouredComponent extends Component {

	protected final Controller<Colour> colour;

	public Colour getColour() {
		return colour.get(this, theme.fg);
	}

	public int getColourValue() {
		return getColour().getValue();
	}

}
