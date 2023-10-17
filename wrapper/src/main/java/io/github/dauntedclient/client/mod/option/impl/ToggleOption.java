/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.option.impl;

import io.github.dauntedclient.client.mod.option.*;
import io.github.dauntedclient.client.ui.component.Component;
import io.github.dauntedclient.client.ui.component.controller.AlignedBoundsController;
import io.github.dauntedclient.client.ui.component.impl.ToggleComponent;
import io.github.dauntedclient.client.util.data.Alignment;

public class ToggleOption extends ModOption<Boolean> {

	public ToggleOption(String name, ModOptionStorage<Boolean> storage) {
		super(name, storage);
	}

	@Override
	public Component createComponent() {
		Component container = createDefaultComponent();
		container.add(new ToggleComponent(this::getValue, this::setValue), new AlignedBoundsController(Alignment.END, Alignment.CENTRE));
		return container;
	}

}
