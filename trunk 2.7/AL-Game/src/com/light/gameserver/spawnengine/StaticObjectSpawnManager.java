/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.light.gameserver.spawnengine;

import com.light.gameserver.controllers.StaticObjectController;
import com.light.gameserver.dataholders.DataManager;
import com.light.gameserver.model.gameobjects.StaticObject;
import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.templates.VisibleObjectTemplate;
import com.light.gameserver.model.templates.spawns.SpawnGroup2;
import com.light.gameserver.model.templates.spawns.SpawnTemplate;
import com.light.gameserver.utils.idfactory.IDFactory;
import com.light.gameserver.world.World;
import com.light.gameserver.world.knownlist.PlayerAwareKnownList;

/**
 * @author ATracer
 */
public class StaticObjectSpawnManager {

	/**
	 * @param spawnGroup
	 * @param instanceIndex
	 */
	public static void spawnTemplate(SpawnGroup2 spawn, int instanceIndex) {
		VisibleObjectTemplate objectTemplate = DataManager.ITEM_DATA.getItemTemplate(spawn.getNpcId());
		if (objectTemplate == null)
			return;

		if (spawn.hasPool()) {
			for (int i = 0; i < spawn.getPool(); i++) {
				SpawnTemplate template = spawn.getRndTemplate();
			int objectId = IDFactory.getInstance().nextId();
				StaticObject staticObject = new StaticObject(objectId, new StaticObjectController(), template, objectTemplate);
			staticObject.setKnownlist(new PlayerAwareKnownList(staticObject));
				bringIntoWorld(staticObject, template, instanceIndex);
			}
		}
		else {
			for (SpawnTemplate template : spawn.getSpawnTemplates()) {
				int objectId = IDFactory.getInstance().nextId();
				StaticObject staticObject = new StaticObject(objectId, new StaticObjectController(), template, objectTemplate);
				staticObject.setKnownlist(new PlayerAwareKnownList(staticObject));
				bringIntoWorld(staticObject, template, instanceIndex);
			}
		}
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
