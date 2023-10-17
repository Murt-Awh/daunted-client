/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl;

import java.text.DecimalFormat;

import io.github.dauntedclient.client.DetectedServer;
import io.github.dauntedclient.client.mod.*;
import io.github.dauntedclient.client.util.MinecraftUtils;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.Formatting;

public class TNTTimerMod extends DauntedClientMod {

	private static final DecimalFormat FORMAT = new DecimalFormat("0.00");
	public static boolean enabled;

	@Override
	public String getId() {
		return "tnt_timer";
	}

	@Override
	public ModCategory getCategory() {
		return ModCategory.UTILITY;
	}

	// Unfortunately doesn't work with TNT chains due to their random nature.
	public static String getText(TntEntity tnt) {
		float fuse = tnt.fuseTimer;

		// Based on Sk1er's mod
		if (DetectedServer.current() == DetectedServer.HYPIXEL && "BED WARS".equals(MinecraftUtils.getScoreboardTitle())) {
			fuse -= 28;
		}

		Formatting colour = Formatting.GREEN;

		if (fuse < 20) {
			colour = Formatting.DARK_RED;
		} else if (fuse < 40) {
			colour = Formatting.RED;
		} else if (fuse < 60) {
			colour = Formatting.GOLD;
		}

		return colour + FORMAT.format(fuse / 20);
	}

	@Override
	protected void onEnable() {
		super.onEnable();
		enabled = true;
	}

	@Override
	protected void onDisable() {
		super.onDisable();
		enabled = false;
	}

}
