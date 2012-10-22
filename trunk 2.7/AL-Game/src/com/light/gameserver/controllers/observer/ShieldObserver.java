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
package com.light.gameserver.controllers.observer;

import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.shield.Shield;
import com.light.gameserver.model.utils3d.Point3D;
import com.light.gameserver.services.SiegeService;
import com.light.gameserver.utils.MathUtil;
import com.light.gameserver.utils.PacketSendUtility;

/**
 * @author Wakizashi, Source
 */
public class ShieldObserver extends ActionObserver {

	private Creature creature;
	private Shield shield;
	private Point3D oldPosition;

	public ShieldObserver() {
		super(ObserverType.MOVE);
		this.creature = null;
		this.shield = null;
		this.oldPosition = null;
	}

	public ShieldObserver(Shield shield, Creature creature) {
		super(ObserverType.MOVE);
		this.creature = creature;
		this.shield = shield;
		this.oldPosition = new Point3D(creature.getX(), creature.getY(), creature.getZ());
	}

	@Override
	public void moved() {
		boolean passedThrough = false;
		boolean isGM = false;

		if (SiegeService.getInstance().getFortress(shield.getId()).isUnderShield())
			if (!(creature.getZ() < shield.getZ() && oldPosition.getZ() < shield.getZ()))
				if (MathUtil.isInSphere(shield, (float) oldPosition.getX(), (float) oldPosition.getY(),
						(float) oldPosition.getZ(), shield.getTemplate().getRadius()) != MathUtil.isIn3dRange(shield, creature,
						shield.getTemplate().getRadius()))
					passedThrough = true;

		if (passedThrough) {
			if (creature instanceof Player) {
				PacketSendUtility.sendMessage(((Player) creature), "You passed through shield.");
				isGM = ((Player) creature).isGM();
			}

			if (!isGM) {
				if (!(creature.getLifeStats().isAlreadyDead()))
					creature.getController().die();
				if (creature instanceof Player)
					((Player) creature).getFlyController().endFly();
				creature.getObserveController().removeObserver(this);
			}
		}

		oldPosition.x = creature.getX();
		oldPosition.y = creature.getY();
		oldPosition.z = creature.getZ();
	}

}