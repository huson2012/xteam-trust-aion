package com.light.gameserver.services.siegeservice;

import com.light.gameserver.ai2.AbstractAI;
import com.light.gameserver.ai2.eventcallback.OnDieEventCallback;
import com.light.gameserver.services.SiegeService;

@SuppressWarnings("rawtypes")
public class SiegeBossDeathListener extends OnDieEventCallback {

	private final Siege<?> siege;

	public SiegeBossDeathListener(Siege siege) {
		this.siege = siege;
	}

	@Override
	public void onDie(AbstractAI obj) {
		siege.setBossKilled(true);
		SiegeService.getInstance().stopSiege(siege.getSiegeLocationId());
	}
}
