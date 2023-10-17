/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.chatextensions.channel;

import net.minecraft.entity.player.ClientPlayerEntity;

public interface ChatChannel {

	public String getName();

	public void sendMessage(ClientPlayerEntity player, String message);

}
