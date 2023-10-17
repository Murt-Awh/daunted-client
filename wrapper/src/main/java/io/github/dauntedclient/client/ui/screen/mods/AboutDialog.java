/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.screen.mods;

import org.lwjgl.nanovg.*;

import io.github.dauntedclient.client.ui.component.ComponentRenderInfo;
import io.github.dauntedclient.client.ui.component.controller.AlignedBoundsController;
import io.github.dauntedclient.client.ui.component.impl.*;
import io.github.dauntedclient.client.util.MinecraftUtils;
import io.github.dauntedclient.client.util.data.*;
import io.github.dauntedclient.util.GlobalConstants;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;

public class AboutDialog extends BlockComponent {

	public AboutDialog() {
		super(theme.bg, 12, 0);

		add(new LabelComponent("daunted_client.mod.screen.about"),
				new AlignedBoundsController(Alignment.CENTRE, Alignment.START,
						(component, defaultBounds) -> new Rectangle(defaultBounds.getX(), defaultBounds.getY() + 9,
								defaultBounds.getWidth(), defaultBounds.getHeight())));

		add(ButtonComponent.done(() -> parent.setDialog(null)),
				new AlignedBoundsController(Alignment.CENTRE, Alignment.END,
						(component, defaultBounds) -> new Rectangle(defaultBounds.getX(), defaultBounds.getY() - 8,
								defaultBounds.getWidth(), defaultBounds.getHeight())));
	}

	@Override
	public void render(ComponentRenderInfo info) {
		super.render(info);

		int logoX = getBounds().getWidth() / 2 - 32;
		int logoY = getBounds().getHeight() / 2 - 60;

		NVGPaint paint = MinecraftUtils.nvgMinecraftTexturePaint(nvg,
				new Identifier("textures/gui/daunted_client_icon.png"), logoX, logoY, 64, 64, 0);

		NanoVG.nvgBeginPath(nvg);
		NanoVG.nvgFillPaint(nvg, paint);
		NanoVG.nvgRect(nvg, logoX, logoY, 64, 64);
		NanoVG.nvgFill(nvg);

		NanoVG.nvgFillColor(nvg, Colour.WHITE.nvg());

		String versionString = "Daunted Client version " + GlobalConstants.VERSION_STRING;
		regularFont.renderString(nvg, versionString,
				getBounds().getWidth() / 2 - regularFont.getWidth(nvg, versionString) / 2, 110);
		regularFont.renderString(nvg, GlobalConstants.COPYRIGHT,
				getBounds().getWidth() / 2 - regularFont.getWidth(nvg, GlobalConstants.COPYRIGHT) / 2, 125);
		String licenseString = I18n.translate("daunted_client.mod.screen.license");
		regularFont.renderString(nvg, licenseString,
				getBounds().getWidth() / 2 - regularFont.getWidth(nvg, licenseString) / 2, 140);
	}

	@Override
	protected Rectangle getDefaultBounds() {
		return Rectangle.ofDimensions(200, 200);
	}

}
