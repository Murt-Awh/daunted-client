/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.AllArgsConstructor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

@AllArgsConstructor
public class PlayerSleepEvent {

	public final PlayerEntity player;
	public final BlockPos pos;

}
