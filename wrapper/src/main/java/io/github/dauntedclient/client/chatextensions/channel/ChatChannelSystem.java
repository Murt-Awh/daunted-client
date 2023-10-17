/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.chatextensions.channel;

import java.util.List;

public abstract class ChatChannelSystem {

	public static final ChatChannel ALL = new DefaultChatChannel("All", null);

	private ChatChannel channel = ALL;

	public abstract List<ChatChannel> getChannels();

	public static ChatChannel getPrivateChannel(String player) {
		return new DefaultChatChannel(player, "msg " + player);
	}

	public ChatChannel getChannel() {
		return channel;
	}

	public String getChannelName() {
		return channel.getName();
	}

	public void setChannel(ChatChannel channel) {
		this.channel = channel;
	}

}
