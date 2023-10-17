/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.gui.screen.TitleScreen;

@Mixin(TitleScreen.class)
public interface TitleScreenAccessor {

	@Invoker("renderPanorama")
	public abstract void drawPanorama(int mouseX, int mouseY, float partialTicks);

	@Invoker("transformPanorama")
	public abstract void rotateAndBlurPanorama(float partialTicks);

}
