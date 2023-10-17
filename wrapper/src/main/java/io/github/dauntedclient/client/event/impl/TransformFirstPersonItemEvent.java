/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.AllArgsConstructor;
import net.minecraft.item.ItemStack;

@AllArgsConstructor
public class TransformFirstPersonItemEvent {

	public final ItemStack itemToRender;
	public final float equipProgress;
	public final float swingProgress;

}
