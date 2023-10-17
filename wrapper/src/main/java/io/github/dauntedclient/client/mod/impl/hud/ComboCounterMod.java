/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.mod.impl.hud;

import io.github.dauntedclient.client.event.EventHandler;
import io.github.dauntedclient.client.event.impl.*;
import io.github.dauntedclient.client.mod.impl.DauntedClientSimpleHudMod;
import io.github.dauntedclient.client.util.data.Position;
import net.minecraft.client.resource.language.I18n;

public class ComboCounterMod extends DauntedClientSimpleHudMod {

	private long hitTime = -1;
	private int combo;
	private int possibleTarget;

	@Override
	public String getId() {
		return "combo_counter";
	}

	@Override
	public void render(Position position, boolean editMode) {
		super.render(position, editMode);
		if ((System.currentTimeMillis() - hitTime) > 2000) {
			combo = 0;
		}
	}

	@Override
	public String getText(boolean editMode) {
		if (editMode || combo == 0) {
			return I18n.translate("daunted_client.mod.combo_counter.no_hits");
		} else if (combo == 1) {
			return I18n.translate("daunted_client.mod.combo_counter.one_hit");
		} else {
			return I18n.translate("daunted_client.mod.combo_counter.n_hits", combo);
		}
	}

	public void dealHit() {
		combo++;
		possibleTarget = -1;
		hitTime = System.currentTimeMillis();
	}

	@EventHandler
	public void onEntityAttack(EntityAttackEvent event) {
		possibleTarget = event.victim.getEntityId();
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.entity.getEntityId() == possibleTarget) {
			dealHit();
		} else if (event.entity == mc.player) {
			takeHit();
		}
	}

	public void takeHit() {
		combo = 0;
	}

}
