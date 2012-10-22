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
package com.light.gameserver.network.aion.serverpackets;

import com.light.gameserver.model.siege.FortressLocation;
import com.light.gameserver.model.siege.Influence;
import com.light.gameserver.network.aion.AionConnection;
import com.light.gameserver.network.aion.AionServerPacket;
import com.light.gameserver.services.SiegeService;
import javolution.util.FastList;

public class SM_FORTRESS_STATUS extends AionServerPacket {

	protected void writeImpl(AionConnection con) {
		FastList<FortressLocation> fortresses = new FastList<FortressLocation>();

		writeC(1);
		writeD(SiegeService.getInstance().getSecondsBeforeHourEnd());
		writeF(Influence.getInstance().getElyos());
		writeF(Influence.getInstance().getAsmos());
		writeF(Influence.getInstance().getBalaur());
		writeH(3);
		writeD(210050000);
		writeF(Influence.getInstance().getElyos());
		writeF(Influence.getInstance().getAsmos());
		writeF(Influence.getInstance().getBalaur());
		writeD(220070000);
		writeF(Influence.getInstance().getElyos());
		writeF(Influence.getInstance().getAsmos());
		writeF(Influence.getInstance().getBalaur());
		writeD(400010000);
		writeF(Influence.getInstance().getElyos());
		writeF(Influence.getInstance().getAsmos());
		writeF(Influence.getInstance().getBalaur());
		writeD(0);
		writeD(0);
		writeD(0);
		writeD(0);
		writeH(fortresses.size());

		for (FortressLocation loc : fortresses) {
			writeD(loc.getLocationId());
			writeC(loc.getNextState());
		}
	}

}