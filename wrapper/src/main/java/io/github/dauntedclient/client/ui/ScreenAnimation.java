/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui;

import java.io.Closeable;

import org.lwjgl.nanovg.*;
import org.lwjgl.opengl.*;

import com.mojang.blaze3d.platform.GLX;

import io.github.dauntedclient.client.lib.penner.easing.Sine;
import io.github.dauntedclient.client.mod.impl.DauntedClientConfig;
import io.github.dauntedclient.client.util.*;
import net.minecraft.client.MinecraftClient;

public class ScreenAnimation extends NanoVGManager implements Closeable {

	private static final int DURATION = 150;

	private int fbWidth, fbHeight;
	private NVGLUFramebuffer fb;
	private final long openTime = System.currentTimeMillis();

	private boolean isActive() {
		return DauntedClientConfig.instance.openAnimation && System.currentTimeMillis() - openTime < DURATION
				&& GLX.supportsFbo();
	}

	@Override
	public void close() {
		if (fb != null) {
			NanoVGGL2.nvgluDeleteFramebuffer(nvg, fb);
			fb = null;
		}
	}

	public boolean wrap(Runnable task) {
		if (!isActive()) {
			close();
			MinecraftUtils.withNvg(task, true);
			return false;
		}

		MinecraftClient mc = MinecraftClient.getInstance();

		if (fbWidth != mc.width || fbHeight != mc.height)
			close();

		if (fb == null) {
			fbWidth = mc.width;
			fbHeight = mc.height;
			fb = NanoVGGL2.nvgluCreateFramebuffer(nvg, mc.width, mc.height, 0);
		}

		NanoVGGL2.nvgluBindFramebuffer(nvg, fb);
		GL11.glViewport(0, 0, mc.width, mc.height);
		GL11.glClearColor(0, 0, 0, 0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		MinecraftUtils.withNvg(task, true);
		mc.getFramebuffer().bind(true);

		// render the frame with transformations
		MinecraftUtils.withNvg(() -> {
			float progress = Sine.easeOut(Math.min(System.currentTimeMillis() - openTime, DURATION), 0, 1, DURATION);
			NanoVG.nvgGlobalAlpha(nvg, progress);
			progress = 0.85F + (progress * 0.15F);
			NanoVG.nvgTranslate(nvg, (mc.width / 2F) * (1 - progress), (mc.height / 2F) * (1 - progress));
			NanoVG.nvgScale(nvg, progress, progress);

			NanoVG.nvgBeginPath(nvg);
			NanoVG.nvgRect(nvg, 0, 0, mc.width, mc.height);
			NanoVG.nvgFillPaint(nvg, MinecraftUtils.nvgTexturePaint(nvg, fb.image(), 0, 0, mc.width, mc.height, 0));
			NanoVG.nvgFill(nvg);
		}, false);
		return true;
	}

}
