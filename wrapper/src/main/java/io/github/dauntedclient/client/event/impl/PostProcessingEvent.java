/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import java.util.*;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.gl.ShaderEffect;

@RequiredArgsConstructor
public class PostProcessingEvent {

	public final Type type;
	public final List<ShaderEffect> effects = new LinkedList<>();

	public enum Type {
		RENDER, UPDATE
	}

}
