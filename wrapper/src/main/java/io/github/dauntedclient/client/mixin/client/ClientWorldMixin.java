/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.event.impl.SoundPlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

	@Shadow
	private @Final MinecraftClient client;

	@Inject(method = "playSound(DDDLjava/lang/String;FFZ)V", at = @At(value = "HEAD"), cancellable = true)
	public void handlePlaySound(double x, double y, double z, String soundName, float volume, float pitch,
			boolean distanceDelay, CallbackInfo callback) {
		SoundPlayEvent event = Client.INSTANCE.getEvents()
				.post(new SoundPlayEvent(soundName, volume, pitch, volume, pitch));
		if (event.pitch != event.originalPitch || event.volume != event.originalVolume) {
			callback.cancel();

			volume = event.volume;
			pitch = event.pitch;
			double distanceSq = this.client.getCameraEntity().squaredDistanceTo(x, y, z);
			PositionedSoundInstance sound = new PositionedSoundInstance(new Identifier(soundName), volume, pitch,
					(float) x, (float) y, (float) z);

			if (distanceDelay && distanceSq > 100.0D)
				client.getSoundManager().play(sound, (int) (Math.sqrt(distanceSq) / 40.0D * 20.0D));
			else
				client.getSoundManager().play(sound);
		}
	}

}
