/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;
import com.replaymod.render.hooks.EntityRendererHandler;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.event.impl.*;
import io.github.dauntedclient.client.util.MinecraftUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.Window;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.scoreboard.ScoreboardObjective;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform"
			+ "/GlStateManager;enableBlend()V", ordinal = 1, shift = At.Shift.AFTER), cancellable = true)
	public void preRenderGameOverlay(float partialTicks, CallbackInfo callback) {
		GlStateManager.disableLighting();
		if (Client.INSTANCE.getEvents()
				.post(new PreGameOverlayRenderEvent(partialTicks, GameOverlayElement.ALL)).cancelled) {
			callback.cancel();
		}
	}

	@Inject(method = "render", at = @At("RETURN"))
	public void postRenderGameOverlay(float partialTicks, CallbackInfo callback) {
		GlStateManager.disableLighting();
		Client.INSTANCE.getEvents().post(new PostGameOverlayRenderEvent(partialTicks, GameOverlayElement.ALL));
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;showCrosshair()Z"))
	public boolean preRenderCrosshair(InGameHud instance) {
		boolean result = !Client.INSTANCE.getEvents().post(
				new PreGameOverlayRenderEvent(MinecraftUtils.getTickDelta(), GameOverlayElement.CROSSHAIRS)).cancelled
				&& showCrosshair();
		client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
		return result;
	}

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(IIIIII)V"))
	public void overrideCrosshair(float partialTicks, CallbackInfo ci) {
		Client.INSTANCE.getEvents().post(new PostGameOverlayRenderEvent(partialTicks, GameOverlayElement.CROSSHAIRS));
	}

	@Inject(method = "renderScoreboardObjective", at = @At("HEAD"), cancellable = true)
	public void overrideScoreboard(ScoreboardObjective objective, Window window, CallbackInfo callback) {
		if (((EntityRendererHandler.IEntityRenderer) MinecraftClient.getInstance().gameRenderer)
				.replayModRender_getHandler() != null
				|| Client.INSTANCE.getEvents().post(new ScoreboardRenderEvent(objective, window)).cancelled) {
			callback.cancel();
		}
	}

	@Inject(method = "renderHorseHealth", at = @At("HEAD"), cancellable = true)
	public void preJumpBar(Window window, int x, CallbackInfo callback) {
		if (Client.INSTANCE.getEvents().post(
				new PreGameOverlayRenderEvent(MinecraftUtils.getTickDelta(), GameOverlayElement.JUMPBAR)).cancelled)
			callback.cancel();

		client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
	}

	@Inject(method = "renderHorseHealth", at = @At("RETURN"))
	public void postJumpBar(Window window, int x, CallbackInfo callback) {
		Client.INSTANCE.getEvents()
				.post(new PostGameOverlayRenderEvent(MinecraftUtils.getTickDelta(), GameOverlayElement.JUMPBAR));
	}

	@Inject(method = "renderBossBar", at = @At("HEAD"), cancellable = true)
	public void preBossBar(CallbackInfo callback) {
		if (Client.INSTANCE.getEvents().post(
				new PreGameOverlayRenderEvent(MinecraftUtils.getTickDelta(), GameOverlayElement.BOSSHEALTH)).cancelled) {
			BossBar.framesToLive--;
			callback.cancel();
		}

		client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
	}

	@Inject(method = "renderBossBar", at = @At("RETURN"))
	public void postBossBar(CallbackInfo callback) {
		Client.INSTANCE.getEvents()
				.post(new PostGameOverlayRenderEvent(MinecraftUtils.getTickDelta(), GameOverlayElement.BOSSHEALTH));
	}

	@Shadow
	protected abstract boolean showCrosshair();

	@Shadow
	private @Final MinecraftClient client;

}
