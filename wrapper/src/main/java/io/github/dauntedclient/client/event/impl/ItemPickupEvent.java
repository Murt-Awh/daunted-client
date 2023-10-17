/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.AllArgsConstructor;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;

@AllArgsConstructor
public class ItemPickupEvent {

	public final PlayerEntity player;
	public final ItemEntity pickedUp;

}
