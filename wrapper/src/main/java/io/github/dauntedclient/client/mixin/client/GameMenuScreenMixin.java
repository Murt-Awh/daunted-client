/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.replaymod.replay.ReplayModReplay;

import io.github.dauntedclient.client.ui.screen.IngameServerList;
import io.github.dauntedclient.client.ui.screen.mods.ModsScreen;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {

	@Inject(method = "init", at = @At("RETURN"))
	public void addButtons(CallbackInfo callback) {
		boolean replay = ReplayModReplay.instance.getReplayHandler() != null;

		buttons.add(new ButtonWidget(5000, replay ? buttons.get(2).x + 102 : width / 2 - 100,
				replay ? buttons.get(2).y : height / 4 + 56, 98, 20, I18n.translate("daunted_client.mod.screen.title")));

		if (!replay)
			buttons.add(new ButtonWidget(5001, width / 2 + 2, height / 4 + 56, 98, 20,
					I18n.translate("daunted_client.servers")));
	}

	@Inject(method = "buttonClicked", at = @At("RETURN"))
	public void actionPerformed(ButtonWidget button, CallbackInfo callback) {
		if (button.id == 5000)
			client.setScreen(new ModsScreen());
		else if (button.id == 5001)
			client.setScreen(new IngameServerList(this));
	}

}
