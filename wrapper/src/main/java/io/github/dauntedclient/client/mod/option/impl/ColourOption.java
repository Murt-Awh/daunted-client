/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.option.impl;

import java.util.Optional;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.mod.Mod;
import io.github.dauntedclient.client.mod.option.*;
import io.github.dauntedclient.client.ui.component.Component;
import io.github.dauntedclient.client.ui.component.controller.*;
import io.github.dauntedclient.client.ui.component.impl.*;
import io.github.dauntedclient.client.ui.screen.mods.ColourPickerDialog;
import io.github.dauntedclient.client.util.*;
import io.github.dauntedclient.client.util.data.*;

public class ColourOption extends ModOption<Colour> {

	private final Optional<String> applyToAllKey;

	public ColourOption(String name, ModOptionStorage<Colour> storage, Optional<String> applyToAllKey) {
		super(name, storage);
		this.applyToAllKey = applyToAllKey;
	}

	@Override
	public Component createComponent() {
		Component container = createDefaultComponent();

		ColourBoxComponent colour = new ColourBoxComponent(Controller.of(() -> getValue()));
		container.add(colour, new AlignedBoundsController(Alignment.END, Alignment.CENTRE));

		container.add(new LabelComponent(Controller.of(() -> getValue().toHexString())).scaled(0.8F),
				new AlignedBoundsController(Alignment.END, Alignment.CENTRE, (component, defaultBounds) -> {
					return new Rectangle(
							(int) (container.getBounds().getWidth() - NanoVGManager.getRegularFont()
									.getWidth(NanoVGManager.getNvg(), ((LabelComponent) component).getText()) - 12),
							defaultBounds.getY(), defaultBounds.getWidth(), defaultBounds.getHeight());
				}));

		colour.onClick((info, button) -> {
			if (button != 0)
				return false;

			MinecraftUtils.playClickSound(true);
			container.getScreen().getRoot().setDialog(new ColourPickerDialog(this, getValue(), this::setValue));
			return true;
		});

		return container;
	}

	public boolean canApplyToAll() {
		return applyToAllKey.isPresent();
	}

	public void applyToAll() {
		for (Mod mod : Client.INSTANCE.getMods()) {
			for (ColourOption option : mod.getFlatOptions(ColourOption.class)) {
				if (!option.applyToAllKey.equals(applyToAllKey))
					continue;
				option.setFrom(this);
			}
		}
	}

}
