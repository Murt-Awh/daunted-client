/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.component.impl;

import org.lwjgl.nanovg.NanoVG;

import io.github.dauntedclient.client.ui.component.ComponentRenderInfo;
import io.github.dauntedclient.client.ui.component.controller.Controller;
import io.github.dauntedclient.client.util.data.*;
import net.minecraft.client.resource.language.I18n;

public class LabelComponent extends ColouredComponent {

	private final Controller<String> text;
	private float scale = 1;

	public LabelComponent(String text) {
		this((component, defaultText) -> I18n.translate(text), (component, defaultColour) -> defaultColour);
	}

	public LabelComponent(Controller<String> text) {
		this(text, (component, defaultColour) -> defaultColour);
	}

	public LabelComponent(Controller<String> text, Controller<Colour> colour) {
		super(colour);
		this.text = text;
	}

	public LabelComponent scaled(float scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public void render(ComponentRenderInfo info) {
		NanoVG.nvgFillColor(nvg, getColour().nvg());
		NanoVG.nvgSave(nvg);
		NanoVG.nvgScale(nvg, scale, scale);
		regularFont.renderString(nvg, getText(), 0, 0);
		NanoVG.nvgRestore(nvg);

		super.render(info);
	}

	@Override
	protected Rectangle getDefaultBounds() {
		return Rectangle.ofDimensions((int) (regularFont.getWidth(nvg, getText()) * scale),
				(int) ((regularFont.getLineHeight(nvg) + 2) * scale));
	}

	public String getText() {
		return text.get(this);
	}

}
