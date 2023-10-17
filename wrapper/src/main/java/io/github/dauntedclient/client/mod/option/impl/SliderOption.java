/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.option.impl;

import java.text.DecimalFormat;
import java.util.Optional;

import io.github.dauntedclient.client.mod.option.*;
import io.github.dauntedclient.client.ui.component.Component;
import io.github.dauntedclient.client.ui.component.controller.AlignedBoundsController;
import io.github.dauntedclient.client.ui.component.impl.*;
import io.github.dauntedclient.client.util.NanoVGManager;
import io.github.dauntedclient.client.util.data.*;
import lombok.Getter;
import net.minecraft.client.resource.language.I18n;

public class SliderOption<N extends Number> extends ModOption<N> {

	public SliderOption(String name, ModOptionStorage<N> storage, Optional<String> valueFormat, float min, float max,
			float step) {
		super(name, storage);
		this.valueFormat = valueFormat;
		this.min = min;
		this.max = max;
		this.step = step;
	}

	@Getter
	private final float min, max, step;
	@Getter
	private Optional<String> valueFormat;

	@Override
	public Component createComponent() {
		Component container = createDefaultComponent();
		container.add(new SliderComponent(min, max, step, getValue().floatValue(), (value) -> {
			if (getType() == int.class)
				setValue((N) (Integer) value.intValue());
			else
				setValue((N) value);
		}), new AlignedBoundsController(Alignment.END, Alignment.CENTRE));
		valueFormat.ifPresent((format) -> {
			container.add(
					new LabelComponent((component, defaultText) -> I18n.translate(format,
							new DecimalFormat("0.##").format(getValue()))).scaled(0.8F),
					new AlignedBoundsController(Alignment.END, Alignment.CENTRE, (component, defaultBounds) -> {
						return new Rectangle(
								(int) (container.getBounds().getWidth() - NanoVGManager.getRegularFont()
										.getWidth(NanoVGManager.getNvg(), ((LabelComponent) component).getText()) - 97),
								defaultBounds.getY(), defaultBounds.getWidth(), defaultBounds.getHeight());
					}));
		});
		return container;
	}

}
