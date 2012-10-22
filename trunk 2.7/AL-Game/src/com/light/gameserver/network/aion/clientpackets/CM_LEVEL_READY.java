/**
 * This file is part of aion-emu <aion-emu.com>.
 *
 * aion-emu is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * aion-emu is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * aion-emu. If not, see <http://www.gnu.org/licenses/>.
 */
package com.light.gameserver.network.aion.clientpackets;

import com.light.gameserver.dataholders.DataManager;
import com.light.gameserver.model.gameobjects.Pet;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.items.storage.StorageType;
import com.light.gameserver.model.templates.windstreams.Location2D;
import com.light.gameserver.model.templates.windstreams.WindstreamTemplate;
import com.light.gameserver.network.aion.AionClientPacket;
import com.light.gameserver.network.aion.AionConnection.State;
import com.light.gameserver.network.aion.serverpackets.*;
import com.light.gameserver.questEngine.QuestEngine;
import com.light.gameserver.questEngine.model.QuestEnv;
import com.light.gameserver.services.WeatherService;
import com.light.gameserver.spawnengine.InstanceRiftSpawnManager;
import com.light.gameserver.spawnengine.RiftSpawnManager;
import com.light.gameserver.world.World;

/**
 * Client is saying that level[map] is ready.
 *
 * @author -Nemesiss-
 * @author Kwazar
 */
public class CM_LEVEL_READY extends AionClientPacket {

	public CM_LEVEL_READY(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	@Override
	protected void readImpl() {
	}

	@Override
	protected void runImpl() {
		Player activePlayer = getConnection().getActivePlayer();

		sendPacket(new SM_PLAYER_INFO(activePlayer, false));
		sendPacket(new SM_MOTION(activePlayer.getObjectId(), activePlayer.getMotions().getActiveMotions()));
		activePlayer.getController().startProtectionActiveTask();

		WindstreamTemplate template = DataManager.WINDSTREAM_DATA.getStreamTemplate(activePlayer.getPosition().getMapId());
		Location2D location;
		if (template != null)
			for (int i = 0; i < template.getLocations().getLocation().size(); i++) {
				location = template.getLocations().getLocation().get(i);
				sendPacket(new SM_WINDSTREAM_ANNOUNCE(location.getBidirectional(), template.getMapid(), location.getId(),
						location.getBoost()));
			}
		location = null;
		template = null;

		/**
		 * Spawn player into the world.
		 */
		// If already spawned, despawn before spawning into the world
		if (activePlayer.isSpawned())
			World.getInstance().despawn(activePlayer);
		World.getInstance().spawn(activePlayer);

		activePlayer.getController().refreshZoneImpl();

		activePlayer.getController().updateNearbyQuests();

		/**
		 * Loading weather for the player's region
		 */
		WeatherService.getInstance().loadWeather(activePlayer);

		QuestEngine.getInstance().onEnterWorld(new QuestEnv(null, activePlayer, 0, 0));

		activePlayer.getController().onEnterWorld();
		// zone channel message
		sendPacket(new SM_SYSTEM_MESSAGE(1390122, activePlayer.getPosition().getInstanceId()));

		RiftSpawnManager.sendRiftStatus(activePlayer);
		InstanceRiftSpawnManager.sendInstanceRiftStatus(activePlayer);

		activePlayer.getEffectController().updatePlayerEffectIcons();
		sendPacket(SM_CUBE_UPDATE.cubeSize(StorageType.CUBE, activePlayer));

		if (activePlayer.isTeleporting())
			activePlayer.setIsTeleporting(false);

		Pet pet = activePlayer.getPet();
		if (pet != null)
			World.getInstance().spawn(pet);
	}

}