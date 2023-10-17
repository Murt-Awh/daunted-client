/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client;

import java.io.*;
import java.nio.file.*;

import org.apache.logging.log4j.*;
import org.lwjgl.input.Keyboard;

import io.github.dauntedclient.client.addon.AddonManager;
import io.github.dauntedclient.client.chatextensions.ChatExtensionManager;
import io.github.dauntedclient.client.command.CommandManager;
import io.github.dauntedclient.client.event.EventBus;
import io.github.dauntedclient.client.mod.*;
import io.github.dauntedclient.client.network.*;
import io.github.dauntedclient.client.ui.screen.mods.ModsScreen;
import io.github.dauntedclient.client.util.*;
import io.github.dauntedclient.util.Utils;
import io.github.dauntedclient.wrapper.ClassWrapper;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;

/**
 * Main class for Daunted Client.
 */
public final class Client {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final Client INSTANCE = new Client();

	private final MinecraftClient mc = MinecraftClient.getInstance();

	// all of the managers etc.
	@Getter
	private final EventBus events = new EventBus();
	@Getter
	private final ModManager mods = new ModManager();
	@Getter
	private final ModUiStateManager modUiState = new ModUiStateManager();
	@Getter
	private final PacketApi packets = new PacketApi();
	@Getter
	private final PopupManager popups = new PopupManager();
	@Getter
	private final ChatExtensionManager chatExtensions = new ChatExtensionManager();
	@Getter
	private final CommandManager commands = new CommandManager();
	@Getter
	private final PseudoResourceManager pseudoResources = new PseudoResourceManager();
	@Getter
	private final AddonManager addons = AddonManager.getInstance();
	@Getter
	private final OnlineApi onlinePlayers = new OnlineApi();

	// for convenience with multimc
	@Getter
	private final Path configFolder = mc.runDirectory.toPath().resolve("config/daunted-client"),
			modsFile = configFolder.resolve("mods.json"), modUiStateFile = configFolder.resolve("mod_ui_state.json");

	// used before 1.9.x
	private final Path legacyModsFile = mc.runDirectory.toPath().resolve("daunted_client_mods.json");

	public void init() {
		// in this function, we try to use fairly heavy exception padding so nothing
		// escapes, but is logged
		// maybe it's not ideal, as suppressing some exceptions may cause others

		// misc stuff
		try {
			NanoVGManager.createContext();
		} catch (IOException error) {
			throw new IllegalStateException("Cannot initialise NanoVG", error);
		}

		MinecraftUtils.resetLineWidth();
		Keyboard.enableRepeatEvents(false);
		new File(mc.runDirectory, "server-resource-packs").mkdirs();

		CpsMonitor.forceInit();

		// load mods and data
		prepareLoad();
		mods.loadStandard(modsFile);
		addons.load(mods);
		try {
			modUiState.load(modUiStateFile);
		} catch (Throwable error) {
			LOGGER.error("Could not load mod ui state", error);
		}

		// register events
		events.register(this);
		events.register(new DefaultEvents());
		events.register(packets);
		events.register(popups);
		events.register(onlinePlayers);

		// save it all!
		save();
	}

	private void prepareLoad() {
		try {
			Utils.ensureDirectory(configFolder);

			if (Files.exists(legacyModsFile) && !Files.exists(modsFile))
				Files.move(legacyModsFile, modsFile);
		} catch (Throwable error) {
			LOGGER.error("Failed to prepare load", error);
		}
	}

	public void save() {
		try {
			modUiState.save(modUiStateFile);
		} catch (IOException error) {
			LOGGER.error("Could not save pins", error);
		}

		try {
			mods.saveStandard(modsFile);
		} catch (IOException error) {
			LOGGER.error("Could not save mods", error);
		}

		addons.save(mods);
	}

	/**
	 * Saves if the mod screen is not opened.
	 */
	public void optionChanged() {
		if (!(mc.currentScreen instanceof ModsScreen))
			save();
	}

	/**
	 * Gets the core class loader.
	 *
	 * @return the loader.
	 */
	public ClassLoader getClassLoader() {
		return ClassWrapper.getInstance();
	}

}
