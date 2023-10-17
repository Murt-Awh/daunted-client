/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.lib.penner.easing;

public class Sine {

	public static float easeIn(float t, float b, float c, float d) {
		return -c * (float) Math.cos(t / d * (Math.PI / 2)) + c + b;
	}

	public static float easeOut(float t, float b, float c, float d) {
		return c * (float) Math.sin(t / d * (Math.PI / 2)) + b;
	}

	public static float easeInOut(float t, float b, float c, float d) {
		return -c / 2 * ((float) Math.cos(Math.PI * t / d) - 1) + b;
	}

}
