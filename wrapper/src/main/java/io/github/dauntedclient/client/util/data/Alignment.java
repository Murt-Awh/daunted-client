/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.util.data;

import net.minecraft.util.math.MathHelper;

/**
 * Alignment not aware of its axis.
 */
public enum Alignment {
	/**
	 * Aligned to left or top.
	 */
	START,
	/**
	 * Centred.
	 */
	CENTRE,
	/**
	 * Aligned to right or bottom.
	 */
	END;

	public static Alignment fromNullable(Alignment alignment) {
		if (alignment == null)
			return START;

		return alignment;
	}

	public int getPosition(int areaSize, int objectSize) {
		switch (this) {
			case CENTRE:
				return (areaSize / 2) - (objectSize / 2);
			case END:
				return areaSize - objectSize;
			default:
				return 0;
		}
	}
}
