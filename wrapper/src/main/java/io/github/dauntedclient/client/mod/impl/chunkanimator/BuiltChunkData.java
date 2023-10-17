/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.chunkanimator;

public interface BuiltChunkData {

	long getAnimationStart();

	void setAnimationStart(long animationStart);

	boolean isAnimationComplete();

	void skipAnimation();

}
