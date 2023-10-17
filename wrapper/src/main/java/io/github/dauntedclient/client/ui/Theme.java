/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui;

import io.github.dauntedclient.client.ui.component.controller.*;
import io.github.dauntedclient.client.util.data.Colour;

public class Theme implements Cloneable {

	public static final Theme DARK, LIGHT;

	static {
		DARK = new Theme();
		DARK.bg = new Colour(0xFF0F0F0F);
		DARK.button = new Colour(0xFF161616);
		DARK.buttonHover = new Colour(0xFF1E1E1E);
		DARK.buttonSecondary = new Colour(0xFF252525);
		DARK.buttonSecondaryHover = new Colour(0xFF2C2C2C);
		DARK.fg = new Colour(0xFFFFFFFF);
		DARK.accent = new Colour(0xFFFFFFFF);
		DARK.accentHover = new Colour(0xFF64A2FF);
		DARK.accentFg = new Colour(0xFF1C1C1C);
		DARK.fgButton = new Colour(0xFFFFFFFF);
		DARK.fgButtonHover = new Colour(0xFFD4D4D4);
		DARK.transparent1 = new Colour(0xFF282828);
		DARK.transparent2 = new Colour(0xFF3C3C3C);
		DARK.danger = new Colour(0xFFFF2929);
		DARK.dangerHover = new Colour(0xFFFF4B4B);

		LIGHT = DARK.clone();
		LIGHT.bg = new Colour(0xFFEBEBEB);
		LIGHT.button = new Colour(0xFFDFDFDF);
		LIGHT.buttonHover = new Colour(0xFFD9D9D9);
		LIGHT.buttonSecondary = new Colour(0xFFCCCCCC);
		LIGHT.buttonSecondaryHover = new Colour(0xFFC7C7C7);
		LIGHT.fg = new Colour(0xFF2A2A2A);
		LIGHT.accentHover = new Colour(0xFFFAA000);
		LIGHT.fgButton = new Colour(0xFF2A2A2A);
		LIGHT.fgButtonHover = new Colour(0xFF454545);
	}

	public Colour bg;
	public Colour button;
	public Colour buttonHover;
	public Colour buttonSecondary;
	public Colour buttonSecondaryHover;
	public Colour fgButton;
	public Colour fgButtonHover;
	public Colour fg;
	public Colour accent;
	public Colour accentHover;
	public Colour accentFg;
	public Colour transparent1;
	public Colour transparent2;
	public Colour danger;
	public Colour dangerHover;

	// @formatter:off
	public final Controller<Colour> bg() { return Controller.of(bg); }
	public final Controller<Colour> fg() { return Controller.of(fg); }
	public final Controller<Colour> accentFg() { return Controller.of(accentFg); }
	// @formatter:on

	private Controller<Colour> hoverPair(Colour base, Colour hover) {
		return new AnimatedColourController((component, defaultColour) -> component.isHovered() ? hover : base);
	}

	public final Controller<Colour> button() {
		return hoverPair(button, buttonHover);
	}

	public final Controller<Colour> buttonSecondary() {
		return hoverPair(buttonSecondary, buttonSecondaryHover);
	}

	public final Controller<Colour> fgButton() {
		return new AnimatedColourController(
				(component, defaultColour) -> component.isHovered() ? fgButtonHover : fgButton);
	}

	public final Controller<Colour> accent() {
		return hoverPair(accent, accentHover);
	}

	public final Controller<Colour> danger() {
		return hoverPair(danger, dangerHover);
	}

	@Override
	public Theme clone() {
		try {
			return (Theme) super.clone();
		} catch (CloneNotSupportedException error) {
			throw new AssertionError("Theme should implement Clonable");
		}
	}

}
