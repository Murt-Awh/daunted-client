/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.option.annotation;

public @interface ColourKey {

	String value() default "";

	String BACKGROUND_COLOUR = "background_colour";
	String BORDER_COLOUR = "border_colour";
	String TEXT_COLOUR = "text_colour";

}
