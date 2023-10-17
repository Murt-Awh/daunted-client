/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl;

import java.io.*;

import org.lwjgl.input.Keyboard;

import com.google.gson.*;
import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.mod.*;
import io.github.dauntedclient.client.mod.option.annotation.Option;
import io.github.dauntedclient.util.*;
import net.minecraft.client.option.KeyBinding;

public class DauntedClientConfig extends ConfigOnlyMod {

	public static DauntedClientConfig instance;

	@Expose
	@Option
	public boolean broadcastOnline = false;

	@Expose
	@Option
	public boolean onlineIndicator = true;

	@Expose
	@Option
	public boolean remindMeToUpdate = true;

	@Expose
	@Option
	public boolean fancyMainMenu = true;

	@Expose
	@Option
	public boolean logoInInventory;

	@Option
	public KeyBinding modsKey = new KeyBinding(getTranslationKey("mods"), Keyboard.KEY_RSHIFT,
			GlobalConstants.KEY_CATEGORY);

	@Option
	public KeyBinding editHudKey = new KeyBinding(getTranslationKey("edit_hud"), 0, GlobalConstants.KEY_CATEGORY);

	@Expose
	@Option
	public boolean openAnimation;

	@Expose
	@Option
	public boolean buttonClicks = true;

	@Expose
	@Option
	public boolean smoothScrolling = true;

	public SemVer latestRelease;

	@Override
	public String getId() {
		return "daunted_client";
	}

	@Override
	public ModCategory getCategory() {
		return ModCategory.GENERAL;
	}

	@Override
	public void init() {
		super.init();

		instance = this;

		// yuck...
		if (GlobalConstants.AUTOUPDATE)
			getOptions().remove(0);
		else if (remindMeToUpdate) {
			Thread thread = new Thread(() -> {
				try (InputStream in = GlobalConstants.RELEASE_API.openStream()) {
					JsonObject object = JsonParser.parseReader(new InputStreamReader(in)).getAsJsonObject();
					latestRelease = SemVer.parseOrNull(object.get("name").getAsString());
				} catch (Throwable error) {
					logger.warn("Could not check for updates", error);
				}
			});
			thread.setDaemon(true);
			thread.start();
		}
	}

	@Override
	public boolean onOptionChange(String key, Object value) {
		if (key.equals("broadcastOnline")) {
			if ((Boolean) value) {
				try {
					Client.INSTANCE.getOnlinePlayers().logIn();
				} catch (IOException error) {
					logger.error("Could not log in", error);
				}
			} else {
				try {
					Client.INSTANCE.getOnlinePlayers().logOut();
				} catch (IOException error) {
					logger.error("Could not log out", error);
				}
			}
		}

		return true;
	}

}
