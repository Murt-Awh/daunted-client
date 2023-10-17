/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl;

import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.mod.option.annotation.*;
import io.github.dauntedclient.client.util.DirtyMapper;
import io.github.dauntedclient.client.util.data.*;
import net.minecraft.client.resource.language.I18n;

/**
 * A simple HUD mod that rendered a simple string.
 */
@AbstractTranslationKey(DauntedClientSimpleHudMod.TRANSLATION_KEY)
public abstract class DauntedClientSimpleHudMod extends DauntedClientHudMod {

	public static final String TRANSLATION_KEY = "daunted_client.mod.simple_hud";

	@Expose
	@Option
	protected boolean background = true;
	@Expose
	@ColourKey(ColourKey.BACKGROUND_COLOUR)
	protected Colour backgroundColour = new Colour(0, 0, 0, 100);
	@Expose
	@Option
	protected boolean border = false;
	@Expose
	@ColourKey(ColourKey.BORDER_COLOUR)
	protected Colour borderColour = Colour.BLACK;
	@Expose
	@ColourKey(ColourKey.TEXT_COLOUR)
	protected Colour textColour = Colour.WHITE;
	@Expose
	@Option
	protected boolean shadow = true;
	private DirtyMapper<String, Integer> langWidth = new DirtyMapper<>(
			() -> mc.getLanguageManager().getLanguage().getCode(), (key) -> {
				String translationKey = getTranslationKey("default_width");
				String width = I18n.translate(translationKey);

				if (width.equals(translationKey)) {
					return 53;
				}

				return Integer.parseInt(width);
			});

	@Override
	public Rectangle getBounds(Position position) {
		return new Rectangle(position.getX(), position.getY(), getWidth(), 16);
	}

	private int getWidth() {
		return langWidth.get();
	}

	@Override
	public void render(Position position, boolean editMode) {
		String text = getText(editMode);
		if (text != null) {
			if (background) {
				getBounds(position).fill(backgroundColour);
			} else {
				if (!text.isEmpty()) {
					text = "[" + text + "]";
				}
			}

			if (border) {
				getBounds(position).stroke(borderColour);
			}
			font.draw(text, position.getX() + (getBounds(position).getWidth() / 2F) - (font.getStringWidth(text) / 2F),
					position.getY() + 4, textColour.getValue(), shadow);
		}
	}

	public abstract String getText(boolean editMode);

}
