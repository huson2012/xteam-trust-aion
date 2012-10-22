/*
 * This file is part of aion-lightning <aion-lightning.org>.
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
package ai.siege;

import ai.GeneralNpcAI2;
import com.light.gameserver.ai2.AIName;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.gameobjects.siege.SiegeNpc;
import com.light.gameserver.network.aion.serverpackets.SM_FORTRESS_INFO;
import com.light.gameserver.services.SiegeService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.world.knownlist.Visitor;

/**
 * @author Source
 */
@AIName("siege_teleporter")
public class SiegeTeleporterAI2 extends GeneralNpcAI2 {

	@Override
	protected void handleDespawned() {
		canTeleport(false);
		super.handleDespawned();
	}

	@Override
	protected void handleSpawned() {
		canTeleport(true);
		super.handleSpawned();
	}

	private void canTeleport(final boolean status) {
		final int id = ((SiegeNpc) getOwner()).getSiegeId();
		SiegeService.getInstance().getFortress(id).setCanTeleport(status);

		getPosition().getWorldMapInstance().doOnAllPlayers(new Visitor<Player>() {

			@Override
			public void visit(Player player) {
				PacketSendUtility.sendPacket(player, new SM_FORTRESS_INFO(id, status));
			}

		});
	}

}