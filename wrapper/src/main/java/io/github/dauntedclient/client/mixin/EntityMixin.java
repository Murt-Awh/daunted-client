/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import io.github.dauntedclient.client.culling.Cullable;
import lombok.*;
import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public abstract class EntityMixin implements Cullable {

	@Getter
	@Setter
	private boolean culled;

}
