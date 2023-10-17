/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.cosmetica;

import com.mojang.blaze3d.platform.GlStateManager;

import cc.cosmetica.api.Model;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.model.BakedModel;

public final class ShoulderBuddies extends CosmeticLayer {

	public ShoulderBuddies(PlayerEntityRenderer parent) {
		super(parent);
	}

	@Override
	public void render(AbstractClientPlayerEntity player, float p_177141_2_, float p_177141_3_, float partialTicks,
			float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
		if (player.isInvisible())
			return;

		CosmeticaMod.instance.getShoulderBuddies(player).ifPresent((buddies) -> {
			if (buddies.getLeft().isPresent()) {
				render(buddies.getLeft().get(), false, player, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_,
						p_177141_6_, p_177141_7_, scale);
			}

			if (buddies.getRight().isPresent()) {
				render(buddies.getRight().get(), true, player, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_,
						p_177141_6_, p_177141_7_, scale);
			}
		});
	}

	private void render(Model buddy, boolean right, AbstractClientPlayerEntity player, float p_177141_2_,
			float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_,
			float scale) {
		if (!buddy.getId().equals("-sheep")) {
			// work this out later
			return;
		}

		GlStateManager.pushMatrix();

		MinecraftClient.getInstance().getTextureManager().bindTexture(Texture.load(0, 0, buddy.getTexture()));

		boolean staticPosition = (buddy.flags() & Model.LOCK_SHOULDER_BUDDY_ORIENTATION) > 0;
		boolean flip = right && (buddy.flags() & Model.DONT_MIRROR_SHOULDER_BUDDY) == 0;
		BakedModel model = CosmeticaMod.instance.bakeIfAbsent(buddy);

		if (staticPosition) {
			GlStateManager.translate(right ? -0.375 : 0.375, player.isSneaking() ? 0.1 : -0.15,
					player.isSneaking() ? -0.16 : 0);
			Util.render(parent.getModel().body, model, 0, 0.044f, 0, flip);
		} else {
			Util.render(right ? parent.getModel().rightArm : parent.getModel().leftArm, model, 0, 0.37f, 0, flip);
		}

		GlStateManager.popMatrix();
	}

}
