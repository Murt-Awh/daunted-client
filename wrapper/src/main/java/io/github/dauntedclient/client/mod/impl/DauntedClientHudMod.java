/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl;

import java.util.*;

import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.mod.*;
import io.github.dauntedclient.client.mod.hud.HudElement;
import io.github.dauntedclient.client.mod.option.*;
import io.github.dauntedclient.client.mod.option.annotation.AbstractTranslationKey;
import io.github.dauntedclient.client.mod.option.impl.SliderOption;
import io.github.dauntedclient.client.util.data.*;
import net.minecraft.client.font.TextRenderer;

/**
 * Represents a mod with only a single HUD.
 */
@AbstractTranslationKey(DauntedClientHudMod.TRANSLATION_KEY)
public abstract class DauntedClientHudMod extends DauntedClientMod implements PrimaryIntegerSettingMod {

	public static final String TRANSLATION_KEY = "daunted_client.mod.hud";

	/**
	 * Represents the single element that this mod contains.
	 */
	protected final HudElement element = new HudModElement();

	@Expose
	private Position position;
	@Expose
	public float scale = 100;
	protected TextRenderer font;

	@Override
	public ModCategory getCategory() {
		return ModCategory.HUD;
	}

	@Override
	protected List<ModOption<?>> createOptions() {
		List<ModOption<?>> options = super.createOptions();
		options.add(1,
				new SliderOption(TRANSLATION_KEY + ".option.scale",
						ModOptionStorage.of(Number.class, () -> scale, (value) -> scale = value.floatValue()),
						Optional.of("daunted_client.slider.percent"), 50, 150, 1));
		return options;
	}

	@Override
	public void lateInit() {
		super.lateInit();
		this.font = mc.textRenderer;
	}

	protected float getScale() {
		return scale / 100;
	}

	@Override
	public List<HudElement> getHudElements() {
		return Arrays.asList(element);
	}

	public void setPosition(Position position) {
		element.setPosition(position);
	}

	public boolean isVisible() {
		return true;
	}

	public Rectangle getBounds(Position position) {
		return null;
	}

	@Override
	public void render(boolean editMode) {
		element.render(editMode);
	}

	public void render(Position position, boolean editMode) {
	}

	public boolean isShownInReplay() {
		return false;
	}

	public Position determineDefaultPosition(int width, int height) {
		return new Position(0, 0);
	}

	@Override
	public void decrement() {
		scale = Math.max(50, scale - 10);
	}

	@Override
	public void increment() {
		scale = Math.min(150, scale + 10);
	}

	class HudModElement implements HudElement {

		@Override
		public Mod getMod() {
			return DauntedClientHudMod.this;
		}

		@Override
		public Position getConfiguredPosition() {
			return position;
		}

		@Override
		public void setPosition(Position position) {
			DauntedClientHudMod.this.position = position;
		}

		@Override
		public Position determineDefaultPosition(int width, int height) {
			return DauntedClientHudMod.this.determineDefaultPosition(width, height);
		}

		@Override
		public float getScale() {
			return scale / 100F;
		}

		@Override
		public boolean isVisible() {
			return isEnabled() && DauntedClientHudMod.this.isVisible();
		}

		@Override
		public void render(Position position, boolean editMode) {
			DauntedClientHudMod.this.render(position, editMode);
		}

		@Override
		public boolean isShownInReplay() {
			return DauntedClientHudMod.this.isShownInReplay();
		}

		@Override
		public Rectangle getBounds(Position position) {
			return DauntedClientHudMod.this.getBounds(position);
		}

	}

}
