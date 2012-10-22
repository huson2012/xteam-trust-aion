package com.light.gameserver.services.siegeservice;

import com.light.gameserver.controllers.attack.AggroList;
import com.light.gameserver.model.gameobjects.Creature;

@SuppressWarnings("rawtypes")
public class SiegeBossDoAddDamageListener extends AggroList.AddDamageValueCallback {

	private final Siege<?> siege;

	public SiegeBossDoAddDamageListener(Siege siege) {
		this.siege = siege;
	}

	@Override
	public void onDamageAdded(Creature creature, int hate) {
		siege.addBossDamage(creature, hate);
	}
}
