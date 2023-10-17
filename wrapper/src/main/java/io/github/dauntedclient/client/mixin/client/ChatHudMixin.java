/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import java.util.List;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.event.impl.ChatRenderEvent;
import io.github.dauntedclient.client.extension.*;
import io.github.dauntedclient.client.mod.impl.hud.chat.ChatMod;
import io.github.dauntedclient.client.util.MinecraftUtils;
import net.minecraft.client.gui.hud.*;
import net.minecraft.text.Text;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin implements ChatHudExtension {

	@Inject(at = @At("HEAD"), cancellable = true, method = "render")
	public void drawChat(int updateCounter, CallbackInfo callback) {
		if (Client.INSTANCE.getEvents().post(new ChatRenderEvent((ChatHud) (Object) /* hax */ this, updateCounter,
				MinecraftUtils.getTickDelta())).cancelled) {
			callback.cancel();
		}
	}

	@Inject(at = @At("HEAD"), cancellable = true, method = "addMessage(Lnet/minecraft/text/Text;)V")
	public void allowNullMessage(Text component, CallbackInfo callback) {
		if (component == null)
			callback.cancel();
	}

	@Redirect(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"))
	public int getSize(List instance) {
		if (ChatMod.enabled && ChatMod.instance.infiniteChat)
			return 0;

		return instance.size();
	}

	@Override
	public void clearChat() {
		visibleMessages.clear();
		messages.clear();
	}

	@Shadow
	@Final
	private List<ChatHudLine> visibleMessages;

	@Shadow
	@Final
	private List<ChatHudLine> messages;

}
