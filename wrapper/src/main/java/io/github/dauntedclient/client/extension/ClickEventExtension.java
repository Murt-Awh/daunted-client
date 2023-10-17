/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.extension;

import net.minecraft.text.ClickEvent;

public interface ClickEventExtension {

	static ClickEvent createStyleWithReceiver(Runnable receiver) {
		return from(new ClickEvent(null, null)).setReceiver(receiver).vanilla();
	}

	static ClickEventExtension from(ClickEvent event) {
		return (ClickEventExtension) event;
	}

	Runnable getReceiver();

	ClickEventExtension setReceiver(Runnable receiver);

	default ClickEvent vanilla() {
		return (ClickEvent) this;
	}

}
