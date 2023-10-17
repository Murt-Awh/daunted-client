/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.addon;

import java.nio.file.Path;

import io.github.dauntedclient.client.Client;
import io.github.dauntedclient.client.mod.*;
import lombok.*;

public class Addon extends Mod {

	@Getter
	@Setter(AccessLevel.PACKAGE)
	private AddonInfo info;

	@Override
	public String getTranslationKey(String key) {
		return getId() + '.' + key;
	}

	@Override
	public String getName() {
		return info.getName().orElseGet(super::getName);
	}

	@Override
	public String getDescription() {
		return info.getDescription().orElseGet(super::getDescription);
	}

	public String getVersion() {
		return info.getVersion();
	}

	@Override
	public String getDetail() {
		return ' ' + getVersion();
	}

	@Override
	public String getId() {
		return info.getId();
	}

	@Override
	public Path getConfigFolder() {
		return Client.INSTANCE.getConfigFolder().resolve("addon/" + getId());
	}

	public Path getConfigFile() {
		return getConfigFolder().resolve("config.json");
	}

	@Override
	public final ModCategory getCategory() {
		return ModCategory.ADDONS;
	}

	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

}
