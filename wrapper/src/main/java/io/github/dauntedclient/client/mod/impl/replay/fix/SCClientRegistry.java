/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.replay.fix;

import io.github.dauntedclient.client.util.*;
import net.minecraft.client.option.KeyBinding;

@Deprecated
@ForgeCompat
public class SCClientRegistry {

	public static void registerKeyBinding(KeyBinding keyBinding) {
		MinecraftUtils.registerKeyBinding(keyBinding);
	}

}
