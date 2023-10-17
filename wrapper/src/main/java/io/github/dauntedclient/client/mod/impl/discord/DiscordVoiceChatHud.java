/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.discord;

import java.util.*;

import io.github.dauntedclient.client.mod.Mod;
import io.github.dauntedclient.client.mod.hud.*;
import io.github.dauntedclient.client.mod.impl.discord.socket.User;
import io.github.dauntedclient.client.util.MinecraftUtils;
import io.github.dauntedclient.client.util.data.*;
import lombok.Setter;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;

public class DiscordVoiceChatHud implements HudElement {

	private static final int USER_HEIGHT = 20;

	private final DiscordIntegrationMod mod;
	@Setter
	private TextRenderer font;

	public DiscordVoiceChatHud(DiscordIntegrationMod mod) {
		this.mod = mod;
	}

	@Override
	public boolean isVisible() {
		return mod.isEnabled() && mod.voiceChatHud;
	}

	@Override
	public Rectangle getBounds(Position position) {
		int yOffset = 0;

		switch (mod.voiceChatHudAlignment) {
			case MIDDLE:
				yOffset = (USER_HEIGHT * 4) / 2;
				break;
			case BOTTOM:
				yOffset = USER_HEIGHT * 4;
				break;
			default:
				break;
		}

		return position.offset(0, -yOffset).rectangle(20 + font.getStringWidth("TheKodeToad") + 4, 76);
	}

	@Override
	public void render(Position position, boolean editMode) {
		Collection<User> users;

		if (editMode) {
			users = new ArrayList<>();

			User lynith = new User("0");
			lynith.setName("Lynith");
			users.add(lynith);

			User theKodeToad = new User("0");
			theKodeToad.setName("TheKodeToad");
			users.add(theKodeToad);

			User trigg = new User("0");
			trigg.setName("Trigg");
			users.add(trigg);

			User midget = new User("0");
			midget.setName("midget_3111");
			users.add(midget);
		} else if (mod.socket == null) {
			return;
		} else {
			users = mod.socket.getVoiceCallUsers();
		}

		int y = position.getY();

		switch (mod.voiceChatHudAlignment) {
			case MIDDLE:
				y -= (USER_HEIGHT * (users.size())) / 2;
				break;
			case BOTTOM:
				y -= USER_HEIGHT * users.size();
				break;
			default:
				break;
		}

		for (User user : users) {
			user.bindTexture();
			DrawableHelper.drawTexture(position.getX(), y, 0, 0, 16, 16, 16, 16);

			if (user.isSpeaking()) {
				MinecraftUtils.drawOutline(position.getX() - 1, y - 1, position.getX() + 17, y + 17,
						mod.speakingColour.getValue());
			}

			font.draw(user.getName(), position.getX() + 20, y + 4,
					user.isMuted() ? mod.mutedColour.getValue() : mod.usernameColour.getValue(), mod.shadow);
			y += 20;
		}
	}

	@Override
	public Mod getMod() {
		return mod;
	}

	@Override
	public boolean isShownInReplay() {
		return false;
	}

	@Override
	public Position getConfiguredPosition() {
		return mod.voiceChatHudPosition;
	}

	@Override
	public void setPosition(Position position) {
		mod.voiceChatHudPosition = position;
	}

	@Override
	public float getScale() {
		return mod.voiceChatHudScale / 100F;
	}

}
