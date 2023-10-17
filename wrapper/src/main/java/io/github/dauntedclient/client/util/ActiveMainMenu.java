/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.util;

import lombok.*;
import lombok.experimental.UtilityClass;
import net.minecraft.client.gui.screen.TitleScreen;

// just a placeholder holder for the main menu...
@UtilityClass
public class ActiveMainMenu {

	@Getter
	@Setter
	private TitleScreen instance;

}
