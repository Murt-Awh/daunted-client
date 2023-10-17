/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.event.impl.SendChatMessageEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.world.World;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

	public ClientPlayerEntityMixin(World world, GameProfile gameProfile) {
		super(world, gameProfile);
	}

	@Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
	public void sendChatMessage(String message, CallbackInfo callback) {
		if (Client.INSTANCE.getEvents().post(new SendChatMessageEvent(message)).cancelled) {
			callback.cancel();
		}
	}

	@Override
	public boolean isPartVisible(PlayerModelPart part) {
		return MinecraftClient.getInstance().options.getEnabledPlayerModelParts().contains(part);
	}

}
