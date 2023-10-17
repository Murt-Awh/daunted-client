/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.quickplay.ui;

import io.github.dauntedclient.client.mod.impl.quickplay.QuickPlayMod;
import io.github.dauntedclient.client.mod.impl.quickplay.ui.QuickPlayPalette.QuickPlayPaletteComponent;
import net.minecraft.item.*;

public class BackOption extends QuickPlayOption {

	@Override
	public String getText() {
		return "← Back";
	}

	@Override
	public void onClick(QuickPlayPaletteComponent palette, QuickPlayMod mod) {
		palette.back();
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Items.ARROW);
	}

}
