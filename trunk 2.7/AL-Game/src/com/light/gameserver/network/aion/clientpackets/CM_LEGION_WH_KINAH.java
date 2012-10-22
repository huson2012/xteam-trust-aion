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
package com.light.gameserver.network.aion.clientpackets;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.items.storage.StorageType;
import com.light.gameserver.model.team.legion.Legion;
import com.light.gameserver.network.aion.AionClientPacket;
import com.light.gameserver.network.aion.AionConnection.State;

/**
 * @author ATracer
 */
public class CM_LEGION_WH_KINAH extends AionClientPacket {

	public CM_LEGION_WH_KINAH(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	private long amount;
	private int operation;

	@Override
	protected void readImpl() {
		this.amount = readQ();
		this.operation = readC();
	}

	@Override
	protected void runImpl() {
		final Player activePlayer = getConnection().getActivePlayer();

		Legion legion = activePlayer.getLegion();
		if (legion != null) {
			switch (operation) {
				case 0:
					if (activePlayer.getStorage(StorageType.LEGION_WAREHOUSE.getId()).tryDecreaseKinah(amount)) {
						activePlayer.getInventory().increaseKinah(amount);
					}
					break;
				case 1:
					if (activePlayer.getInventory().tryDecreaseKinah(amount)) {
						activePlayer.getStorage(StorageType.LEGION_WAREHOUSE.getId()).increaseKinah(amount);
					}
					break;
			}
		}
	}

}
