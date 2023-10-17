/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.wrapper.transformer;

import lombok.experimental.UtilityClass;

/**
 * General small class transformations.
 */
@UtilityClass
public class ClassTransformer {

	public byte[] transformClass(String name, byte[] input) {
		input = PackageAccessFixer.fix(name, input);
		input = AccessWidenerTransformer.transform(name, input);
		input = ReplayModMixinPluginCompat.transform(name, input);
		return input;
	}

}
