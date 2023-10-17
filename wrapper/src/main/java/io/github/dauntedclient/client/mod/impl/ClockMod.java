/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import org.lwjgl.nanovg.NanoVG;

import com.google.gson.annotations.Expose;

import io.github.dauntedclient.client.mod.option.annotation.Option;
import io.github.dauntedclient.client.util.*;
import io.github.dauntedclient.client.util.data.*;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.math.MathHelper;

public final class ClockMod extends DauntedClientSimpleHudMod {

	private static final int RADIUS = 30;

	private static final LocalTime PLACEHOLDER = LocalTime.of(1, 30, 50);

	// 01:30 pm
	private static final DateTimeFormatter TWELVE_HOUR = DateTimeFormatter.ofPattern("hh:mm a");
	// 13:30
	private static final DateTimeFormatter TWENTY_FOUR_HOUR = DateTimeFormatter.ofPattern("HH:mm");

	// well, Java has no way to determine this based on locale
	@Option
	@Expose
	private boolean twentyFourHour = true;
	@Option
	@Expose
	private boolean analogue;
	@Option
	@Expose
	private Colour hourHandColour = Colour.WHITE;
	@Option
	@Expose
	private boolean hourMarks = true;
	@Option
	@Expose
	private Colour hourMarksColour = Colour.WHITE;
	@Option
	@Expose
	private Colour minuteHandColour = Colour.WHITE;
	@Option
	@Expose
	private boolean secondsHand = true;
	@Option
	@Expose
	private Colour secondsHandColour = Colour.PURE_RED;

	private static LocalTime getTime(boolean editMode) {
		LocalTime date = PLACEHOLDER;
		if (!editMode)
			date = LocalTime.now();
		return date;
	}

	@Override
	public String getText(boolean editMode) {
		return (twentyFourHour ? TWENTY_FOUR_HOUR : TWELVE_HOUR).format(getTime(editMode));
	}

	@Override
	public void render(Position position, boolean editMode) {
		if (!analogue) {
			super.render(position, editMode);
			return;
		}

		// render analogue clock
		MinecraftUtils.withNvg(() -> renderAnalogue(position, editMode), true);
	}

	@Override
	public String getDetail() {
		return I18n.translate("daunted_client.mod.screen.inspired_by", "danterus");
	}

	private void renderAnalogue(Position position, boolean editMode) {
		long nvg = NanoVGManager.getNvg();

		int cx = position.getX() + RADIUS;
		int cy = position.getY() + RADIUS;

		LocalTime time = getTime(editMode);

		NanoVG.nvgScale(nvg, getScale(), getScale());

		NanoVG.nvgStrokeWidth(nvg, 1);

		// face
		if (background) {
			NanoVG.nvgBeginPath(nvg);
			NanoVG.nvgCircle(nvg, cx, cy, RADIUS);
			NanoVG.nvgFillColor(nvg, backgroundColour.nvg());
			NanoVG.nvgFill(nvg);
		}

		if (border) {
			NanoVG.nvgBeginPath(nvg);
			NanoVG.nvgCircle(nvg, cx, cy, RADIUS - 0.5F);
			NanoVG.nvgStrokeColor(nvg, borderColour.nvg());
			NanoVG.nvgStroke(nvg);
		}

		NanoVG.nvgStrokeWidth(nvg, 0.5F);

		if (hourMarks) {
			float angle = 0;
			for (int i = 0; i < 12; i++) {
				NanoVG.nvgBeginPath(nvg);
				NanoVG.nvgMoveTo(nvg, cx + MathHelper.cos(angle) * (RADIUS - 2),
						cy + MathHelper.sin(angle) * (RADIUS - 2));
				NanoVG.nvgLineTo(nvg, cx + MathHelper.cos(angle) * (RADIUS - 6),
						cy + MathHelper.sin(angle) * (RADIUS - 6));
				NanoVG.nvgStrokeColor(nvg, hourMarksColour.nvg());
				NanoVG.nvgStroke(nvg);

				angle += Math.PI / 6F;
			}
		}

		NanoVG.nvgStrokeWidth(nvg, 1);

		float hour = time.get(ChronoField.HOUR_OF_AMPM) + time.get(ChronoField.MINUTE_OF_HOUR) / 60F;
		hour /= 12;

		float minutes = time.get(ChronoField.MINUTE_OF_DAY) + time.get(ChronoField.SECOND_OF_MINUTE) / 60F;
		minutes /= 60;

		float seconds = time.get(ChronoField.SECOND_OF_MINUTE) + time.get(ChronoField.MILLI_OF_SECOND) / 1000F;
		seconds /= 60;

		float hourAngle = getRadiansFromPercent(hour);
		float minuteAngle = getRadiansFromPercent(minutes);
		float secondAngle = getRadiansFromPercent(seconds);

		NanoVG.nvgBeginPath(nvg);
		NanoVG.nvgMoveTo(nvg, cx, cy);
		NanoVG.nvgLineTo(nvg, cx + MathHelper.cos(minuteAngle) * 26, cy + MathHelper.sin(minuteAngle) * 26);
		NanoVG.nvgStrokeColor(nvg, minuteHandColour.nvg());
		NanoVG.nvgStroke(nvg);

		NanoVG.nvgBeginPath(nvg);
		NanoVG.nvgMoveTo(nvg, cx, cy);
		NanoVG.nvgLineTo(nvg, cx + MathHelper.cos(hourAngle) * 16, cy + MathHelper.sin(hourAngle) * 16);
		NanoVG.nvgStrokeColor(nvg, hourHandColour.nvg());
		NanoVG.nvgStroke(nvg);

		if (secondsHand) {
			NanoVG.nvgStrokeWidth(nvg, 0.5F);

			NanoVG.nvgBeginPath(nvg);
			NanoVG.nvgMoveTo(nvg, cx, cy);
			NanoVG.nvgLineTo(nvg, cx + MathHelper.cos(secondAngle) * 28, cy + MathHelper.sin(secondAngle) * 28);
			NanoVG.nvgStrokeColor(nvg, secondsHandColour.nvg());
			NanoVG.nvgStroke(nvg);
		}

		// dot in centre
		NanoVG.nvgBeginPath(nvg);
		NanoVG.nvgFillColor(nvg, textColour.nvg());
		NanoVG.nvgCircle(nvg, cx, cy, 2);
		NanoVG.nvgFill(nvg);
	}

	private float getRadiansFromPercent(float percent) {
		return (float) ((percent * Math.PI * 2) - Math.PI / 2);
	}

	@Override
	public Rectangle getBounds(Position position) {
		if (analogue)
			return position.rectangle(RADIUS * 2, RADIUS * 2);

		return super.getBounds(position);
	}

	@Override
	public String getId() {
		return "clock";
	}

}
