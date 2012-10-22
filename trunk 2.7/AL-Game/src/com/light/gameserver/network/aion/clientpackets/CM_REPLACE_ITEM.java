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

package com.light.gameserver.network.aion.clientpackets;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.aion.AionClientPacket;
import com.light.gameserver.network.aion.AionConnection.State;
import com.light.gameserver.services.item.ItemMoveService;

/**
 * @author kosyachok
 */
public class CM_REPLACE_ITEM extends AionClientPacket {

	private byte sourceStorageType;
	private int sourceItemObjId;
	private byte replaceStorageType;
	private int replaceItemObjId;

	public CM_REPLACE_ITEM(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	@Override
	protected void readImpl() {
		sourceStorageType = readSC();
		sourceItemObjId = readD();
		replaceStorageType = readSC();
		replaceItemObjId = readD();
	}

	@Override
	protected void runImpl() {
		Player player = getConnection().getActivePlayer();
		ItemMoveService.switchItemsInStorages(player, sourceStorageType, sourceItemObjId, replaceStorageType, replaceItemObjId);
	}

}
