/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin;

import org.spongepowered.asm.mixin.Mixin;

import io.github.dauntedclient.client.culling.Cullable;
import lombok.*;
import net.minecraft.block.entity.BlockEntity;

@Mixin(BlockEntity.class)
public class BlockEntityMixin implements Cullable {

	@Getter
	@Setter
	private boolean culled;

}
