/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.util;

import java.lang.invoke.MethodType;
import java.net.URL;

import io.github.dauntedclient.client.addon.AddonManager;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GlobalConstants {

	// properties
	public final boolean DEV = Boolean.getBoolean("loader.development");
	// shhh... fake constant
	public static boolean optifine = Boolean.getBoolean("io.github.dauntedclient.wrapper.optifine");
	public final String VERSION_STRING = System.getProperty("io.github.dauntedclient.client.version", "unknown");
	public final String USER_AGENT = "Daunted Client/" + GlobalConstants.VERSION;
	public final SemVer VERSION = SemVer.parseOrNull(VERSION_STRING);
	public final String LAUNCHER = System.getProperty("io.github.dauntedclient.client.launcher", "unknown");
	public final boolean AUTOUPDATE = Boolean.getBoolean("io.github.dauntedclient.client.autoupdate");
	public final String NAME = "Daunted Client " + VERSION_STRING;

	// constants
	public final String KEY_TRANSLATION_KEY = "daunted_client.key";
	public final String KEY_CATEGORY = KEY_TRANSLATION_KEY + ".category";
	public final URL RELEASE_API = Utils.sneakyParse(System.getProperty("io.github.dauntedclient.client.release_api",
			"https://api.github.com/repos/Sol-Client/Client/releases/latest"));
	public final long DISCORD_APPLICATION = 1163697032171630662L;
	// please change
	public final String IMGUR_APPLICATION = "4efd63137720136";
	// please don't remove :(
	public final String COPYRIGHT = "© 2023 fwanchan and drifter16";
	public final String OPTIFINE_JAR = "OptiFine_1.8.9_HD_U_M5";
	public final String API = "https://thekodetoad.duckdns.org:8080/v1";

	// utils
	public final MethodType MAIN_METHOD = MethodType.methodType(void.class, String[].class);

}
