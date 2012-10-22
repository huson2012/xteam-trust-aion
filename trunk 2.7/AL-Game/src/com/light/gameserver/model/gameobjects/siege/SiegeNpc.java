/**
 * 
 */
package com.light.gameserver.model.gameobjects.siege;

import com.light.gameserver.controllers.NpcController;
import com.light.gameserver.model.gameobjects.Npc;
import com.light.gameserver.model.siege.SiegeRace;
import com.light.gameserver.model.templates.npc.NpcTemplate;
import com.light.gameserver.model.templates.spawns.siegespawns.SiegeSpawnTemplate;

/**
 * @author ViAl
 */
public class SiegeNpc extends Npc {

	private int siegeId;
	private SiegeRace siegeRace;

	/**
	 * @param objId
	 * @param controller
	 * @param spawnTemplate
	 * @param objectTemplate
	 *          SiegeNpc constructor
	 */
	public SiegeNpc(int objId, NpcController controller, SiegeSpawnTemplate spawnTemplate, NpcTemplate objectTemplate) {
		super(objId, controller, spawnTemplate, objectTemplate);
		this.siegeId = spawnTemplate.getSiegeId();
		this.siegeRace = spawnTemplate.getSiegeRace();
	}

	public SiegeRace getSiegeRace() {
		return siegeRace;
	}

	public int getSiegeId() {
		return siegeId;
	}

	@Override
	public SiegeSpawnTemplate getSpawn() {
		return (SiegeSpawnTemplate) super.getSpawn();
	}
	
	

}
