/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import java.io.File;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import io.github.dauntedclient.client.util.MinecraftUtils;
import net.minecraft.client.resource.ResourcePackLoader;

@Mixin(ResourcePackLoader.Entry.class)
public class ResourcePackLoaderEntryMixin {

	@Redirect(method = "toString", at = @At(value = "INVOKE", target = "Ljava/io/File;getName()Ljava/lang/String;"))
	public String getPackName(File instance) {
		return MinecraftUtils.getRelativeToPackFolder(instance);
	}

}
