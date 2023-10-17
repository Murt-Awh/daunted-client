/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.option;

import java.util.function.Consumer;

import com.google.common.base.Supplier;

public interface ModOptionStorage<T> {

	static <T> ModOptionStorage<T> of(Class<T> type, Supplier<T> getter) {
		return of(type, getter, (ignored) -> {
			throw new UnsupportedOperationException("Option is not settable");
		});
	}

	static <T> ModOptionStorage<T> of(Class<T> type, Supplier<T> getter, Consumer<T> setter) {
		return new ModOptionStorage<T>() {

			@Override
			public T get() {
				return getter.get();
			}

			@Override
			public void set(T value) {
				setter.accept(value);
			}

			@Override
			public Class<T> getType() {
				return type;
			}

		};
	}

	Class<T> getType();

	T get();

	void set(T value);

	default void setFrom(ModOption<T> option) {
		set(option.getValue());
	}

	default <N> ModOptionStorage<N> cast(Class<N> type) {
		if (!getType().equals(type))
			throw new ClassCastException();

		return (ModOptionStorage<N>) this;
	}

	default <N> ModOptionStorage<N> unsafeCast() {
		return (ModOptionStorage<N>) this;
	}

}
