/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.hud;

import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.CpsMonitor;
import io.github.dauntedclient.client.mod.impl.DauntedClientSimpleHudMod;
import io.github.dauntedclient.client.mod.option.annotation.Option;
import io.github.dauntedclient.client.util.MinecraftUtils;
import io.github.dauntedclient.client.util.data.*;

public class CpsMod extends DauntedClientSimpleHudMod {

	@Expose
	@Option
	private boolean rmb;
	@Expose
	@Option
	private Colour separatorColour = new Colour(64, 64, 64);

	@Override
	public String getId() {
		return "cps";
	}

	@Override
	public void render(Position position, boolean editMode) {
		super.render(position, editMode);
		if (rmb) {
			String prefix = background ? "" : "[";
			String suffix = background ? "" : "]";

			int width = font.getStringWidth(
					prefix + CpsMonitor.LMB.getCps() + " | " + CpsMonitor.RMB.getCps() + " CPS" + suffix) - 2;

			int x = position.getX() + (53 / 2) - (width / 2);
			int y = position.getY() + 4;

			x = font.draw(prefix + Integer.toString(CpsMonitor.LMB.getCps()), x, y, textColour.getValue(), shadow);

			x--;
			if (shadow)
				x--;

			x += font.getCharWidth(' ');

			MinecraftUtils.drawVerticalLine(x, y - 1, y + 7, separatorColour.getValue());

			if (shadow) {
				MinecraftUtils.drawVerticalLine(x + 1, y, y + 8, separatorColour.getShadowValue());
			}

			x += 1;

			x += font.getCharWidth(' ');

			font.draw(CpsMonitor.RMB.getCps() + " CPS" + suffix, x, y, textColour.getValue(), shadow);
		}
	}

	@Override
	public String getText(boolean editMode) {
		return rmb ? "" : CpsMonitor.LMB.getCps() + " CPS";
	}

}
