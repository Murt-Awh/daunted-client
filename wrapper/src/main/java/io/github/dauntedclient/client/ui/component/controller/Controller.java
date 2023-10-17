/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.component.controller;

import com.google.common.base.Supplier;

import io.github.dauntedclient.client.ui.component.Component;

@FunctionalInterface
public interface Controller<T> {

	static <T> Controller<T> of(Supplier<T> value) {
		return (component, defaultValue) -> value.get();
	}

	static <T> Controller<T> of(T value) {
		return (component, defaultValue) -> value;
	}

	static <T> Controller<T> none() {
		return (component, defaultValue) -> defaultValue;
	}

	default T get(Component component) {
		return get(component, null);
	}

	T get(Component component, T defaultValue);

}
