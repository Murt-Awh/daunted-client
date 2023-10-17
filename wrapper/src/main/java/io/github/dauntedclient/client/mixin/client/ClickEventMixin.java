/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;

import io.github.dauntedclient.client.extension.ClickEventExtension;
import lombok.Getter;
import net.minecraft.text.ClickEvent;

@Mixin(ClickEvent.class)
public class ClickEventMixin implements ClickEventExtension {

	@Getter
	private Runnable receiver;

	@Override
	public ClickEventExtension setReceiver(Runnable receiver) {
		this.receiver = receiver;
		return this;
	}

}
