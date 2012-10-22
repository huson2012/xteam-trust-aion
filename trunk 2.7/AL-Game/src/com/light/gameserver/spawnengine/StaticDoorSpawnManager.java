/*
 * This file is part of aion-lightning <aion-lightning.com>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.light.gameserver.spawnengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.light.gameserver.controllers.StaticObjectController;
import com.light.gameserver.dataholders.DataManager;
import com.light.gameserver.model.gameobjects.StaticDoor;
import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.templates.spawns.SpawnGroup2;
import com.light.gameserver.model.templates.spawns.SpawnTemplate;
import com.light.gameserver.model.templates.staticdoor.StaticDoorTemplate;
import com.light.gameserver.model.templates.staticdoor.StaticDoorWorld;
import com.light.gameserver.utils.idfactory.IDFactory;
import com.light.gameserver.world.World;
import com.light.gameserver.world.knownlist.PlayerAwareKnownList;

/**
 * @author MrPoke
 */
public class StaticDoorSpawnManager {

	private static Logger log = LoggerFactory.getLogger(StaticDoorSpawnManager.class);

	/**
	 * @param spawnGroup
	 * @param instanceIndex
	 */
	public static void spawnTemplate(int worldId, int instanceIndex) {
		StaticDoorWorld staticDoorWorld = DataManager.STATICDOOR_DATA.getStaticDoorWorlds(worldId);
		if (staticDoorWorld == null)
			return;
		int counter = 0;
		for (StaticDoorTemplate data : staticDoorWorld.getStaticDoors()) {
			SpawnTemplate spawn = new SpawnTemplate(new SpawnGroup2(worldId, 300001), data.getX(), data.getY(), data.getZ(), (byte) 0, 0, null, 0, 0);
			spawn.setStaticId(data.getDoorId());
			int objectId = IDFactory.getInstance().nextId();
			StaticDoor staticDoor = new StaticDoor(objectId, new StaticObjectController(), spawn, data);
			staticDoor.setKnownlist(new PlayerAwareKnownList(staticDoor));
			bringIntoWorld(staticDoor, spawn, instanceIndex);
			counter++;
		}
		if (counter > 0)
			log.info("Spawned static doors: " + worldId + " [" + instanceIndex + "] : " + counter);
	}

	/**
	 * @param visibleObject
	 * @param spawn
	 * @param instanceIndex
	 */
	private static void bringIntoWorld(VisibleObject visibleObject, SpawnTemplate spawn, int instanceIndex) {
		World world = World.getInstance();
		world.storeObject(visibleObject);
		world.setPosition(visibleObject, spawn.getWorldId(), instanceIndex, spawn.getX(), spawn.getY(), spawn.getZ(),
			spawn.getHeading());
		world.spawn(visibleObject);
	}
}
