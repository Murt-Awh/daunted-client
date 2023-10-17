/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client;

import java.io.IOException;

import org.spongepowered.asm.mixin.Mixins;

import io.github.dauntedclient.client.addon.AddonManager;
import io.github.dauntedclient.util.GlobalConstants;
import io.github.dauntedclient.wrapper.transformer.AccessWidenerTransformer;
import me.djtheredstoner.devauth.common.DevAuth;
import net.minecraft.client.main.Main;

/**
 * Used to add some mixin and access wideners.
 */
public final class Premain {

	public static void main(String[] args) throws IOException {
		// TODO this doesn't really work very well
		System.setProperty("http.agent", GlobalConstants.USER_AGENT);

		Mixins.addConfiguration("daunted-client.mixins.json");

		Mixins.addConfiguration("mixins.core.replaymod.json");
		Mixins.addConfiguration("mixins.recording.replaymod.json");
		Mixins.addConfiguration("mixins.render.replaymod.json");
		Mixins.addConfiguration("mixins.render.blend.replaymod.json");
		Mixins.addConfiguration("mixins.replay.replaymod.json");
		if (GlobalConstants.optifine)
			Mixins.addConfiguration("mixins.compat.shaders.replaymod.json");
		Mixins.addConfiguration("mixins.extras.playeroverview.replaymod.json");

		AccessWidenerTransformer.addWideners("replay-mod.accesswidener");

		// load addons
		AddonManager.premain(args);

		if (GlobalConstants.DEV) {
			DevAuth auth = new DevAuth();
			args = auth.processArguments(args);
		}

		// run Minecraft main
		Main.main(args);
	}

}
