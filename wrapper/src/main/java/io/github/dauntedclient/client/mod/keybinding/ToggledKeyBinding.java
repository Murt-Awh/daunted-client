/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.keybinding;

import io.github.dauntedclient.client.event.EventHandler;
import io.github.dauntedclient.client.event.impl.PostTickEvent;
import io.github.dauntedclient.client.mod.Mod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;

public abstract class ToggledKeyBinding<ModType extends Mod> extends KeyBinding {

	public final ModType mod;
	private final MinecraftClient mc = MinecraftClient.getInstance();
	private boolean wasDown;
	private long startTime;

	public ToggledKeyBinding(ModType mod, String description, int keyCode, String category) {
		super(description, keyCode, category);
		this.mod = mod;
	}

	@EventHandler
	public void tickBinding(PostTickEvent event) {
		boolean down = super.isPressed();
		if (mod.isEnabled()) {
			if (down) {
				if (!wasDown) {
					startTime = System.currentTimeMillis();
					if (getState() == ToggleState.TOGGLED) {
						postStateUpdate(ToggleState.HELD);
					} else {
						postStateUpdate(ToggleState.TOGGLED);
					}
				} else if ((System.currentTimeMillis() - startTime) > 250) {
					postStateUpdate(ToggleState.HELD);
				}
			} else if (getState() == ToggleState.HELD) {
				postStateUpdate(null);
			}

			wasDown = down;
		}
	}

	@Override
	public boolean isPressed() {
		if (mod.isEnabled())
			return mc.currentScreen == null && getState() != null;

		return super.isPressed();
	}

	public String getText(boolean editMode) {
		String translationId;
		if (editMode)
			translationId = String.format("daunted_client.mod.%s.%s", mod.getId(),
					ToggleState.TOGGLED.name().toLowerCase());
		else
			translationId = String.format("daunted_client.mod.%s.%s", mod.getId(), getState().name().toLowerCase());
		return I18n.translate(translationId);
	}

	public abstract void postStateUpdate(ToggleState newState);

	public abstract ToggleState getState();

}
