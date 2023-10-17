/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.lib.penner.easing;

public class Linear {

	public static float easeNone(float t, float b, float c, float d) {
		return c * t / d + b;
	}

	public static float easeIn(float t, float b, float c, float d) {
		return c * t / d + b;
	}

	public static float easeOut(float t, float b, float c, float d) {
		return c * t / d + b;
	}

	public static float easeInOut(float t, float b, float c, float d) {
		return c * t / d + b;
	}

}
