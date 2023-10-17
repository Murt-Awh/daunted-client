/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.util;

import com.google.common.base.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DirtyMapper<I, O> {

	private final Supplier<I> controlValueSupplier;
	private final Function<I, O> mapper;
	private I lastControlValue;
	private O lastOutput;

	public O get() {
		I controlValue = controlValueSupplier.get();

		if (!controlValue.equals(lastControlValue)) {
			lastControlValue = controlValue;
			return lastOutput = mapper.apply(lastControlValue);
		}

		return lastOutput;
	}
}
