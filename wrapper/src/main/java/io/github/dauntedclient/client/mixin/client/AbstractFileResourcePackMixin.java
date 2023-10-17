/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import java.io.File;

import org.spongepowered.asm.mixin.*;

import io.github.dauntedclient.client.util.MinecraftUtils;
import net.minecraft.resource.AbstractFileResourcePack;

@Mixin(AbstractFileResourcePack.class)
public class AbstractFileResourcePackMixin {

	@Overwrite
	public String getName() {
		return MinecraftUtils.getRelativeToPackFolder(base);
	}

	@Final
	protected @Shadow File base;

}
