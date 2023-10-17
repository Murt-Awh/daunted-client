/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.extension;

import io.github.dauntedclient.client.chatextensions.ChatButton;
import io.github.dauntedclient.client.util.MinecraftUtils;

public interface ChatScreenExtension {

	ChatButton getSelectedChatButton();

	void setSelectedChatButton(ChatButton button);

	static ChatScreenExtension active() {
		return (ChatScreenExtension) MinecraftUtils.getChatScreen();
	}

}
