/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.replay;

import java.util.*;

import com.google.gson.annotations.Expose;
import com.replaymod.core.ReplayMod;
import com.replaymod.lib.de.johni0702.minecraft.gui.versions.callbacks.OpenGuiScreenCallback;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.event.EventHandler;
import io.github.dauntedclient.client.event.impl.*;
import io.github.dauntedclient.client.mod.*;
import io.github.dauntedclient.client.mod.Mod;
import io.github.dauntedclient.client.mod.hud.*;
import io.github.dauntedclient.client.mod.impl.DauntedClientMod;
import io.github.dauntedclient.client.mod.impl.replay.fix.SCReplayModBackend;
import io.github.dauntedclient.client.mod.option.annotation.*;
import io.github.dauntedclient.client.ui.screen.mods.MoveHudsScreen;
import io.github.dauntedclient.client.util.data.*;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.world.ClientWorld;

/**
 * Daunted Client representation of Replay Mod. This allows it to appear in the
 * mod
 * list.
 *
 * Originally by CrushedPixel and johni0702.
 */
public class SCReplayMod extends DauntedClientMod {

	public static boolean enabled;
	public static Boolean deferedState;
	public static SCReplayMod instance;

	public boolean enableNotifications = true;

	public boolean recordSingleplayer = true;
	public boolean recordServer = true;

	public boolean recordingIndicator = true;
	public Colour recordingIndicatorColour = Colour.BLACK;
	protected float recordingIndicatorScale = 100;
	protected Colour recordingIndicatorTextColour = Colour.WHITE;
	protected boolean recordingIndicatorTextShadow = true;
	protected Position recordingIndicatorPosition;
	private RecordingIndicator recordingIndicatorHud = new RecordingIndicator(this);

	public boolean automaticRecording = true;

	public boolean renameDialog = true;
	public boolean showChat = true;

	public SCCameraType camera = SCCameraType.CLASSIC;
	public boolean showPathPreview = true;
	public SCInterpolatorType defaultInterpolator = SCInterpolatorType.CATMULL;
	public boolean showServerIPs = true;

	public boolean automaticPostProcessing = true;
	public boolean autoSync = true;
	public int timelineLength = 1800;
	public boolean frameTimeFromWorldTime;

	public boolean skipPostRenderGui;
	public boolean skipPostScreenshotGui;

	private final List<Object> unregisterOnDisable = new ArrayList<>();
	private final List<Object> registerOnEnable = new ArrayList<>();

	private SCReplayModBackend backend;

	@Override
	public String getId() {
		return "replay";
	}

	@Override
	public String getDetail() {
		return I18n.translate("daunted_client.mod.screen.modified_from", "iflyyt, notlolo");
	}

	@Override
	public ModCategory getCategory() {
		return ModCategory.UTILITY;
	}

	@Override
	public void init() {
		instance = this;

		backend = new SCReplayModBackend();
		backend.init();

		Client.INSTANCE.getEvents().register(new ConstantListener());

		super.init();
	}

	@Override
	public List<HudElement> getHudElements() {
		return Arrays.asList(recordingIndicatorHud);
	}

	private void updateSettings() {
		ReplayMod.instance.getSettingsRegistry().update();
	}

	@Override
	public boolean onOptionChange(String key, Object value) {
		return super.onOptionChange(key, value);
	}

	@Override
	public void postOptionChange(String key, Object value) {
		updateSettings();
	}

	@Override
	protected void onEnable() {
		super.onEnable();
		deferedState = Boolean.TRUE;

		updateState(mc.world);
	}

	@Override
	protected void onDisable() {
		super.onDisable();
		deferedState = Boolean.FALSE;

		updateState(mc.world);
	}

	public class ConstantListener {

		@EventHandler
		public void onWorldLoad(WorldLoadEvent event) {
			updateState(event.world);
		}

		@EventHandler
		public void onRender(PostGameOverlayRenderEvent event) {
			if (event.type == GameOverlayElement.ALL && enabled && !isEnabled()) {
				render(mc.currentScreen instanceof MoveHudsScreen);
			}
		}

	}

	private void updateState(ClientWorld world) {
		updateSettings();

		if (world == null && deferedState != null && deferedState != enabled) {
			enabled = deferedState;
			if (deferedState) {
				for (Object event : registerOnEnable) {
					Client.INSTANCE.getEvents().register(event);
					unregisterOnDisable.add(event);
				}
				registerOnEnable.clear();

				OpenGuiScreenCallback.EVENT.invoker().openGuiScreen(mc.currentScreen);
			} else {
				for (Object event : unregisterOnDisable) {
					Client.INSTANCE.getEvents().unregister(event);
					registerOnEnable.add(event);
				}
				unregisterOnDisable.clear();
			}
			deferedState = null;
		}
	}

	public void addEvent(Object event) {
		if (isEnabled()) {
			unregisterOnDisable.add(event);
		} else {
			registerOnEnable.add(event);
			Client.INSTANCE.getEvents().unregister(event);
		}
	}

	public void removeEvent(Object event) {
		registerOnEnable.remove(event);
		unregisterOnDisable.remove(event);
	}

}
