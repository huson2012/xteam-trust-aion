/*
 * This file is part of aion-unique <aion-unique.com>.
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
package com.light.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.skillengine.model.Effect;
import com.light.gameserver.model.gameobjects.player.TeleportTask;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnEffect")
public class ReturnEffect extends EffectTemplate {

	@Override
	public void applyEffect(Effect effect) {
	
	Player player = (Player) effect.getEffector();
    TeleportTask teleport = player.getTeleportTask();
	
	if(teleport == null)
		TeleportService.moveToBindLocation((Player) effect.getEffector(), true, 500);
	else
	    TeleportService.teleportTo(player, teleport.MapId, teleport.instanceId, teleport.x, teleport.y, teleport.z, 0, true);
	}

	@Override
	public void calculate(Effect effect) {
		if (effect.getEffected().isSpawned())
			effect.addSucessEffect(this);
	}
}
