/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.option.annotation;

import java.lang.annotation.*;

/**
 * Use this annotation if you have options on an abstract mod class.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AbstractTranslationKey {

	String value();

}
