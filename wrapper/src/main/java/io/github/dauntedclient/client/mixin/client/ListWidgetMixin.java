/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.minecraft.client.gui.widget.ListWidget;

@Mixin(ListWidget.class)
public class ListWidgetMixin {

	// just a tweak to make scrolling a bit faster
	@ModifyConstant(method = "handleMouse", constant = @Constant(intValue = 2, ordinal = 4))
	public int getScrollDivisor(int original) {
		return 1;
	}

}
