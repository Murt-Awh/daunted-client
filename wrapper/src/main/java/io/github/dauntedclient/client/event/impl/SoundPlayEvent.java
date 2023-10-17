/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.event.impl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SoundPlayEvent {

	public String soundName;
	public float volume;
	public float pitch;
	public float originalVolume;
	public float originalPitch;

	/**
	 * Sets volume to <code>0</code>.
	 */
	public void cancel() {
		volume = 0;
	}

}
