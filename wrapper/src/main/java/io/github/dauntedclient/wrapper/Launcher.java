/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.wrapper;

import java.lang.invoke.*;

import org.apache.logging.log4j.LogManager;

import io.github.dauntedclient.util.GlobalConstants;

/**
 * Loads and executes the main method of MAIN_CLASS, using ClassWrapper.
 */
public final class Launcher {

	private static final String MAIN_CLASS = "io.github.dauntedclient.client.Premain";

	public static void main(String[] args) throws Throwable {
		if (System.getProperty("mixin.service") == null)
			System.setProperty("mixin.service", "io.github.dauntedclient.wrapper.WrapperMixinService");

		try (ClassWrapper wrapper = new ClassWrapper(Prelaunch.prepareClasspath())) {
			// @formatter:off
			MethodHandle mainMethod = MethodHandles.lookup().findStatic(
					wrapper.loadClass(MAIN_CLASS),
					"main",
					GlobalConstants.MAIN_METHOD
			);
			// @formatter:on
			mainMethod.invokeExact(args);
		} catch (Throwable error) {
			LogManager.getLogger().error("Launch error", error);
		}
	}

}
