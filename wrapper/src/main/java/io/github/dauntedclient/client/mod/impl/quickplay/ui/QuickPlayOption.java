/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.quickplay.ui;

import io.github.dauntedclient.client.mod.impl.quickplay.QuickPlayMod;
import io.github.dauntedclient.client.mod.impl.quickplay.ui.QuickPlayPalette.QuickPlayPaletteComponent;
import io.github.dauntedclient.client.ui.component.Component;
import net.minecraft.item.ItemStack;

public abstract class QuickPlayOption {

	public abstract String getText();

	public abstract void onClick(QuickPlayPaletteComponent palette, QuickPlayMod mod);

	public abstract ItemStack getIcon();

	public Component component(QuickPlayPaletteComponent screen) {
		return new QuickPlayOptionComponent(screen, this);
	}

}
