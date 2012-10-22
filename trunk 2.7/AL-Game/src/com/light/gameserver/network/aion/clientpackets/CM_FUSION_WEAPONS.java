/**
 * This file is part of aion-unique <aion-unique.smfnew.com>.
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

import com.light.gameserver.network.aion.AionClientPacket;
import com.light.gameserver.network.aion.AionConnection.State;
import com.light.gameserver.services.ArmsfusionService;

/**
 * @author zdead modified by Wakizashi
 */
public class CM_FUSION_WEAPONS extends AionClientPacket {

	public CM_FUSION_WEAPONS(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	private int firstItemId;
	private int secondItemId;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void readImpl() {
		readD();
		firstItemId = readD();
		secondItemId = readD();
		/*
		 * Temporary: fusion price fixed to 50000 kinah TODO: find price value
		 */
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void runImpl() {
		ArmsfusionService.fusionWeapons(getConnection().getActivePlayer(), firstItemId, secondItemId);
	}
}
