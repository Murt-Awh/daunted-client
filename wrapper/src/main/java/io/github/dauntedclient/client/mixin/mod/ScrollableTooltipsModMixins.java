/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.mod;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import io.github.dauntedclient.client.mod.impl.ScrollableTooltipsMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.inventory.slot.Slot;
import net.minecraft.item.itemgroup.ItemGroup;

public class ScrollableTooltipsModMixins {

	@Mixin(Screen.class)
	public static class ScreenMixin {

		@ModifyArgs(method = "renderTooltip(Lnet/minecraft/item/ItemStack;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;renderTooltip(Ljava/util/List;II)V"))
		public void modifyTooltipPosition(Args args) {
			if (!((Object) this instanceof HandledScreen))
				return;

			if (!ScrollableTooltipsMod.enabled)
				return;

			if (((Object) this instanceof CreativeInventoryScreen)) {
				CreativeInventoryScreen creative = (CreativeInventoryScreen) (Object) this;

				if (creative.getSelectedTab() != ItemGroup.INVENTORY.getIndex())
					return;
			}

			ScrollableTooltipsMod instance = ScrollableTooltipsMod.instance;
			instance.onRenderTooltip();

			args.set(1, (int) args.get(1) + instance.offsetX);
			args.set(2, (int) args.get(2) + instance.offsetY);
		}

	}

	@Mixin(HandledScreen.class)
	public static class HandledScreenMixin {

		@Shadow
		private Slot focusedSlot;
		private Slot cachedSlot;

		@Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;popMatrix()V"))
		public void resetScrollOnSlotChange(int mouseX, int mouseY, float tickDelta, CallbackInfo ci) {
			if (ScrollableTooltipsMod.enabled && cachedSlot != focusedSlot) {
				cachedSlot = focusedSlot;
				ScrollableTooltipsMod.instance.resetScroll();
			}
		}
	}
}
