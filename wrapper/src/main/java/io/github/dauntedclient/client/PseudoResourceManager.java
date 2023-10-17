/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client;

import java.util.*;

import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

public final class PseudoResourceManager {

	private final Map<Identifier, Resource> map = new HashMap<>();

	public void register(Identifier id, Resource resource) {
		map.put(id, resource);
	}

	public Resource get(Identifier id) {
		return map.get(id);
	}

}
