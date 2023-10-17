/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mixin.mod;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import io.github.dauntedclient.client.mod.impl.chunkanimator.*;
import lombok.*;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.BuiltChunk;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class ChunkAnimatorModMixins {

	@Getter
	@Setter
	@Mixin(BuiltChunk.class)
	public static class BuiltChunkMixin implements BuiltChunkData {

		private long animationStart = -1;
		private boolean animationComplete;

		@Override
		public void skipAnimation() {
			animationComplete = true;
		}

	}

	@Mixin(ClientPlayerInteractionManager.class)
	public static class ClientPlayerInteractionManagerMixin {

		@Redirect(method = "onRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/BlockItem;canPlaceItemBlock(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Z"))
		public boolean notifyPlace(BlockItem item, World world, BlockPos pos, Direction direction, PlayerEntity player, ItemStack stack) {
			boolean result = item.canPlaceItemBlock(world, pos, direction, player, stack);

			if (result) {
				ChunkAnimatorMod.instance.notifyPlace(pos.add(direction.getVector()));
			}

			return result;
		}

	}

}
