/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.option.impl;

import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.util.Objects;

import org.objectweb.asm.Opcodes;

import io.github.dauntedclient.client.mod.Mod;
import io.github.dauntedclient.client.mod.option.*;
import lombok.Getter;

/**
 * ModOption implementation using fields and method handles.
 *
 * @param <T> the option type.
 */
public class FieldStorage<T> implements ModOptionStorage<T> {

	private static final Lookup LOOKUP = MethodHandles.lookup();

	@Getter
	private final Mod owner;
	@Getter
	private final String name;
	private final MethodHandle get;
	private final MethodHandle set;

	public FieldStorage(Mod owner, Field field) throws IllegalAccessException {
		this.owner = owner;
		name = field.getName();
		get = LOOKUP.unreflectGetter(field);
		if ((Opcodes.ACC_FINAL & field.getModifiers()) != 0)
			set = null;
		else
			set = LOOKUP.unreflectSetter(field);
	}

	@Override
	public Class<T> getType() {
		return (Class<T>) get.type().returnType();
	}

	@Override
	public T get() {
		try {
			return (T) get.invoke(owner);
		} catch (Throwable error) {
			throw new AssertionError("retrieving mod option failed", error);
		}
	}

	@Override
	public void set(T value) {
		if (set == null)
			throw new UnsupportedOperationException(this + " is marked as final");

		try {
			if (!Objects.equals(value, get()) && owner.onOptionChange(name, value)) {
				set.invoke(owner, value);
				owner.postOptionChange(name, value);
			}
		} catch (Throwable error) {
			throw new AssertionError("setting mod option failed", error);
		}
	}

	@Override
	public String toString() {
		return getType().getName() + ' ' + owner.getClass().getName() + "::" + name;
	}

}
