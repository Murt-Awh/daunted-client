/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.chatextensions;

import io.github.dauntedclient.client.extension.ChatScreenExtension;
import net.minecraft.client.MinecraftClient;

public interface ChatButton {

	int getPriority();

	default int getWidth() {
		return MinecraftClient.getInstance().textRenderer.getStringWidth(getText()) + 4;
	}

	int getPopupWidth();

	int getPopupHeight();

	String getText();

	void render(int x, int y, boolean mouseDown, boolean wasMouseDown, boolean wasMouseClicked, int mouseX, int mouseY);

	default boolean isOpen() {
		ChatScreenExtension chat = ChatScreenExtension.active();
		return chat != null && chat.getSelectedChatButton() == this;
	}

}
