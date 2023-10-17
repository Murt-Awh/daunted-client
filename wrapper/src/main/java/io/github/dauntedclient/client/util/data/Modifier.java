/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.util.data;

import org.lwjgl.input.Keyboard;

public final class Modifier {

	public static final int CTRL = 1;
	public static final int ALT = 2;
	public static final int SHIFT = 4;

	public static boolean isModifier(int key) {
		return key == Keyboard.KEY_LCONTROL || key == Keyboard.KEY_LMENU || key == Keyboard.KEY_LSHIFT;
	}

}
