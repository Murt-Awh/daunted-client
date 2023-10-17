/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.component.impl;

import java.util.function.*;

import org.lwjgl.nanovg.NanoVG;

import io.github.dauntedclient.client.ui.component.*;
import io.github.dauntedclient.client.ui.component.controller.*;
import io.github.dauntedclient.client.util.MinecraftUtils;
import io.github.dauntedclient.client.util.data.*;

public final class ToggleComponent extends BlockComponent {

	private BooleanSupplier getter;
	private final Consumer<Boolean> setter;
	private final AnimatedFloatController handleProgress;
	private final Controller<Colour> handleColour;

	public ToggleComponent(BooleanSupplier getter, Consumer<Boolean> setter) {
		super(new AnimatedColourController((component, defaultColour) -> {
			if (getter.getAsBoolean())
				return component.isHovered() ? theme.accentHover : theme.accent;

			return component.isHovered() ? theme.buttonHover : theme.button;
		}), (component, defaultRadius) -> component.getBounds().getHeight() / 2F,
				(component, defaultStrokeWidth) -> 0F);

		this.getter = getter;
		this.setter = setter;

		handleProgress = new AnimatedFloatController((component, ignored) -> getter.getAsBoolean() ? 1F : 0F, 300);
		handleColour = (component, defaultValue) -> {
			Colour start = theme.accentFg;
			Colour end = theme.fg;
			float progress = handleProgress.get(component);
			if (getter.getAsBoolean()) {
				start = theme.fg;
				end = theme.accentFg;
			} else
				progress = 1 - progress;

			return start.lerp(end, progress);
		};
	}

	@Override
	public void render(ComponentRenderInfo info) {
		super.render(info);

		float x = handleProgress.get(this);
		float startX = getBounds().getHeight() / 2F;
		float endX = getBounds().getWidth() - startX;
		x *= endX - startX;
		x += startX;

		NanoVG.nvgBeginPath(nvg);
		NanoVG.nvgCircle(nvg, x, getBounds().getHeight() / 2F, 4);
		NanoVG.nvgFillColor(nvg, handleColour.get(this).nvg());
		NanoVG.nvgFill(nvg);
	}

	@Override
	public boolean mouseClicked(ComponentRenderInfo info, int button) {
		if (button != 0)
			return false;

		MinecraftUtils.playClickSound(true);
		setter.accept(!getter.getAsBoolean());

		return true;
	}

	@Override
	protected Rectangle getDefaultBounds() {
		return Rectangle.ofDimensions(24, 11);
	}

}
