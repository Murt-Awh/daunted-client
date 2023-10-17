/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl;

import java.io.*;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.event.EventHandler;
import io.github.dauntedclient.client.event.impl.PostProcessingEvent;
import io.github.dauntedclient.client.mixin.client.ShaderEffectAccessor;
import io.github.dauntedclient.client.mod.*;
import io.github.dauntedclient.client.mod.option.annotation.*;
import net.minecraft.client.gl.*;
import net.minecraft.client.resource.ResourceMetadataProvider;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

public class MotionBlurMod extends DauntedClientMod implements PrimaryIntegerSettingMod {

	public static final Identifier ID = new Identifier("minecraft:shaders/post/motion_blur.json");

	@Expose
	@Option
	@Slider(min = 0, max = 0.99F, step = 0.01F)
	private float blur = 0.5f;
	private ShaderEffect effect;
	private float groupBlur;

	@Override
	public String getId() {
		return "motion_blur";
	}

	@Override
	public ModCategory getCategory() {
		return ModCategory.VISUAL;
	}

	@Override
	public void init() {
		super.init();
		Client.INSTANCE.getPseudoResources().register(ID, new MotionBlurShader());
	}

	public void update() {
		if (effect == null) {
			groupBlur = blur;
			try {
				effect = new ShaderEffect(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), ID);
				effect.setupDimensions(mc.width, mc.height);
			} catch (JsonSyntaxException | IOException error) {
				logger.error("Could not load motion blur", error);
			}
		}
		if (groupBlur != blur) {
			((ShaderEffectAccessor) effect).getPasses().forEach((pass) -> {
				GlUniform blendFactor = pass.getProgram().getUniformByName("BlendFactor");
				if (blendFactor != null) {
					blendFactor.set(blur);
				}
			});
			groupBlur = blur;
		}
	}

	@EventHandler
	public void onPostProcessing(PostProcessingEvent event) {
		update();
		event.effects.add(effect);
	}

	@Override
	protected void onEnable() {
		super.onEnable();
		effect = null;
	}

	@Override
	public void decrement() {
		blur = Math.max(0, blur - 0.1F);
	}

	@Override
	public void increment() {
		blur = Math.min(1, blur + 0.1F);
	}

	public class MotionBlurShader implements Resource {

		@Override
		public Identifier getId() {
			return null;
		}

		@Override
		public InputStream getInputStream() {
			return IOUtils.toInputStream(String.format("{" + "    \"targets\": [" + "        \"swap\","
					+ "        \"previous\"" + "    ]," + "    \"passes\": [" + "        {"
					+ "            \"name\": \"motion_blur\"," + "            \"intarget\": \"minecraft:main\","
					+ "            \"outtarget\": \"swap\"," + "            \"auxtargets\": [" + "                {"
					+ "                    \"name\": \"PrevSampler\"," + "                    \"id\": \"previous\""
					+ "                }" + "            ]," + "            \"uniforms\": [" + "                {"
					+ "                    \"name\": \"BlendFactor\"," + "                    \"values\": [ %s ]"
					+ "                }" + "            ]" + "        }," + "        {"
					+ "            \"name\": \"blit\"," + "            \"intarget\": \"swap\","
					+ "            \"outtarget\": \"previous\"" + "        }," + "        {"
					+ "            \"name\": \"blit\"," + "            \"intarget\": \"swap\","
					+ "            \"outtarget\": \"minecraft:main\"" + "        }" + "    ]" + "}", blur, blur, blur));
		}

		@Override
		public boolean hasMetadata() {
			return false;
		}

		@Override
		public <T extends ResourceMetadataProvider> T getMetadata(String paramString) {
			return null;
		}

		@Override
		public String getResourcePackName() {
			return null;
		}

	}

}
