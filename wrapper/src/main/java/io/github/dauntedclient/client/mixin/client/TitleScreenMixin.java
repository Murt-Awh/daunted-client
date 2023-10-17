/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.client;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.replaymod.replay.ReplayModReplay;
import com.replaymod.replay.gui.screen.GuiReplayViewer;

import io.github.dauntedclient.client.mod.impl.replay.SCReplayMod;
import io.github.dauntedclient.client.ui.ReplayButton;
import io.github.dauntedclient.client.ui.screen.mods.ModsScreen;
import io.github.dauntedclient.client.util.ActiveMainMenu;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

	@Inject(method = "<init>", at = @At("RETURN"))
	public void setActiveMainMenu(CallbackInfo callback) {
		ActiveMainMenu.setInstance((TitleScreen) (Object) this);
	}

	@Inject(method = "initWidgetsNormal", at = @At("RETURN"))
	public void getModsButton(int x, int y, CallbackInfo callback) {
		buttons.remove(realmsButton);
		buttons.add(new ButtonWidget(realmsButton.id, realmsButton.x, realmsButton.y,
				I18n.translate("daunted_client.mod.screen.title")));

		if (SCReplayMod.enabled)
			buttons.add(new ReplayButton(15, realmsButton.x + 202, realmsButton.y));
	}

	@Redirect(method = "buttonClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;switchToRealms()V"))
	public void openModsMenu(TitleScreen instance) {
		client.setScreen(new ModsScreen());
	}

	@Inject(method = "buttonClicked", at = @At("RETURN"))
	public void openReplayMenu(ButtonWidget button, CallbackInfo callback) {
		if (button.id == 15)
			new GuiReplayViewer(ReplayModReplay.instance).display();
	}

	@Shadow
	private ButtonWidget realmsButton;

}
