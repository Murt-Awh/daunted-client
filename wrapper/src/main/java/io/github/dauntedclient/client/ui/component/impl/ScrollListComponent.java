/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.ui.component.impl;

import org.lwjgl.input.Keyboard;
import org.lwjgl.nanovg.NanoVG;

import io.github.dauntedclient.client.mod.impl.DauntedClientConfig;
import io.github.dauntedclient.client.ui.component.*;
import io.github.dauntedclient.client.util.data.*;
import net.minecraft.util.math.MathHelper;

public class ScrollListComponent extends ListComponent {

	private double targetY;
	private double animatedY;
	private double lastAnimatedY;
	private double calculatedY;
	private int maxScrolling;
	private double scrollPercent;
	private Component scrollbar;
	private int scrolling = -1;
	private int lastMouseY;
	private double grabStartY = -1;
	private int grabMouseY;

	@Override
	public void setParent(Component parent) {
		if (parent == null && this.parent != null) {
			this.parent.remove(scrollbar);
			super.setParent(parent);
			return;
		}

		parent.add(scrollbar = new BlockComponent(theme.buttonSecondary, 1.5F, 0) {

			@Override
			public void render(ComponentRenderInfo info) {
				scrollPercent = (ScrollListComponent.this.getBounds().getHeight() - 6) / (double) getContentHeight();

				NanoVG.nvgTranslate(nvg, 0, (float) (calculatedY * scrollPercent));

				if (maxScrolling != 0) {
					super.render(info);
				}
			}

		}, (component, defaultBounds) -> {
			return new Rectangle(getBounds().getX() + getBounds().getWidth() - 5, getBounds().getY(), 3,
					(int) (getBounds().getHeight() * scrollPercent));
		});

		super.setParent(parent);
	}

	private ComponentRenderInfo translate(ComponentRenderInfo info) {
		return new ComponentRenderInfo(info.relativeMouseX(), (int) (info.relativeMouseY() + calculatedY),
				info.tickDelta());
	}

	public ComponentRenderInfo reverseTranslation(ComponentRenderInfo info) {
		return new ComponentRenderInfo(info.relativeMouseX(), (int) (info.relativeMouseY() - calculatedY),
				info.tickDelta());
	}

	@Override
	public void render(ComponentRenderInfo info) {
		if (!DauntedClientConfig.instance.smoothScrolling) {
			calculatedY = targetY;
		} else {
			calculatedY = (lastAnimatedY + (animatedY - lastAnimatedY) * info.tickDelta());
		}

		NanoVG.nvgTranslate(nvg, 0, (float) -calculatedY);
		maxScrolling = getContentHeight() - getBounds().getHeight();

		if (maxScrolling < 0) {
			maxScrolling = 0;
		}

		if (lastMouseY != info.relativeMouseY()) {
			if (scrolling > 0) {
				int targetCompY = (int) (info.relativeMouseY() - scrolling);
				jumpTo((int) (targetCompY / (getBounds().getHeight() / (double) getContentHeight())));
				clamp();
			} else if (grabStartY != -1) {
				jumpTo(grabStartY - (info.relativeMouseY() - grabMouseY));
				clamp();
			}
		}

		lastMouseY = (int) info.relativeMouseY();

		super.render(translate(info));
	}

	private int mouseInScrollbar(ComponentRenderInfo info) {
		Rectangle scrollBounds = scrollbar.getBounds().offset(-getBounds().getX(),
				-getBounds().getY() + (int) (calculatedY * scrollPercent));

		if (new Rectangle(scrollBounds.getX(), 0, scrollBounds.getWidth(), getBounds().getHeight())
				.contains((int) info.relativeMouseX(), (int) info.relativeMouseY())) {
			if (!scrollBounds.contains((int) info.relativeMouseX(), (int) info.relativeMouseY())) {
				return scrollbar.getBounds().getHeight() / 2;
			}

			return (int) (info.relativeMouseY() - scrollBounds.getY());
		}

		return -1;
	}

	@Override
	public boolean mouseClicked(ComponentRenderInfo info, int button) {
		if (button == 0) {
			scrolling = mouseInScrollbar(reverseTranslation(info));
			lastMouseY = -1;
			if (scrolling != -1) {
				return true;
			}
		}

		boolean superResult = super.mouseClicked(info, button);

		if (button == 0 && !superResult && grabStartY == -1) {
			grabStartY = targetY;
			grabMouseY = (int) reverseTranslation(info).relativeMouseY();
		}

		return true;
	}

	@Override
	public boolean mouseReleasedAnywhere(ComponentRenderInfo info, int button, boolean inside) {
		if (button == 0) {
			if (scrolling != -1) {
				scrolling = -1;
				return true;
			}
			if (grabStartY != -1) {
				grabStartY = -1;
				return true;
			}
		}

		return super.mouseReleasedAnywhere(info, button, inside);
	}

	@Override
	protected boolean shouldCull(Component component) {
		return (component.getBounds().getEndY() - calculatedY) < 0 || component.getBounds().getY() - calculatedY > getBounds().getHeight();
	}

	public void snapTo(int scroll) {
		targetY = animatedY = lastAnimatedY = calculatedY = maxScrolling = scroll;
	}

	public void jumpTo(double scroll) {
		targetY = scroll;
	}

	public int getScroll() {
		return (int) targetY;
	}

	@Override
	protected int getContentHeight() {
		return super.getContentHeight() + getSpacing() * 2;
	}

	@Override
	public boolean mouseClickedAnywhere(ComponentRenderInfo info, int button, boolean inside, boolean processed) {
		return super.mouseClickedAnywhere(translate(info), button, inside, processed);
	}

	protected int getScrollStep() {
		if (subComponents.isEmpty()) {
			return 0;
		}

		return (subComponents.get(0).getBounds().getHeight() + getSpacing());
	}

	@Override
	public boolean mouseScroll(ComponentRenderInfo info, int delta) {
		if (super.mouseScroll(info, delta)) {
			return true;
		}

		delta = delta / Math.abs(delta);

		if (subComponents.size() != 0) {
			targetY -= delta * getScrollStep();
		}

		return true;
	}

	@Override
	public boolean keyPressed(ComponentRenderInfo info, int keyCode, char character) {
		if (keyCode == Keyboard.KEY_HOME) {
			targetY = 0;
			return true;
		} else if (keyCode == Keyboard.KEY_END) {
			targetY = Double.POSITIVE_INFINITY;
			return true;
		} else if (keyCode == Keyboard.KEY_DOWN) {
			targetY += getScrollStep();
			return true;
		} else if (keyCode == Keyboard.KEY_UP) {
			targetY -= getScrollStep();
			return true;
		}

		return super.keyPressed(info, keyCode, character);
	}

	@Override
	public void tick() {
		super.tick();
		clamp();

		lastAnimatedY = animatedY;
		double multiplier = 0.6F;
		animatedY += (targetY - animatedY) * multiplier;
	}

	private void clamp() {
		targetY = MathHelper.clamp(targetY, 0, maxScrolling);
	}

	@Override
	protected boolean shouldScissor() {
		return true;
	}

}
