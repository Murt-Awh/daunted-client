/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.*;

import net.minecraft.client.gui.screen.resourcepack.ResourcePackEntryWidget;
import net.minecraft.client.resource.ResourcePackLoader;

@Mixin(ResourcePackEntryWidget.class)
public class ResourcePackEntryWidgetMixin {

	@Shadow
	private @Final ResourcePackLoader.Entry entry;

	@Overwrite
	public String getName() {
		String name = entry.getName();
		if (name.indexOf('/') != -1)
			name = name.substring(entry.getName().lastIndexOf('/') + 1);

		return name;
	}

}
