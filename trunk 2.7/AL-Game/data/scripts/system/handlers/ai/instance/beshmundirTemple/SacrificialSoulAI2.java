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
package ai.instance.beshmundirTemple;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.light.gameserver.ai2.AI2Actions;
import com.light.gameserver.ai2.AIName;
import com.light.gameserver.model.gameobjects.Creature;

import ai.AggressiveNpcAI2;


/**
 * @author Luzien
 * TODO: implement movement to isbariya, when movement by AI works properly
 */
@AIName("templeSoul")
public class SacrificialSoulAI2 extends AggressiveNpcAI2 {
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		AI2Actions.useSkill(this, 18901);
		toDespawn();
	}
	
	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
		if (creature.getEffectController().hasAbnormalEffect(18959)) {
			AI2Actions.deleteOwner(this);
		}
	}
	

	
	private void toDespawn() {
		ThreadPoolManager.getInstance().schedule(new Runnable() {

			@Override
			public void run() {
				if(!isAlreadyDead()) {
					AI2Actions.targetCreature(SacrificialSoulAI2.this, getPosition().getWorldMapInstance().getNpc(216263));
					AI2Actions.useSkill(SacrificialSoulAI2.this, 18960);
				}
				AI2Actions.deleteOwner(SacrificialSoulAI2.this);
			}
		}, 25000);
	}
}
