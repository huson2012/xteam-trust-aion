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
package ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.light.gameserver.ai2.AI2Actions;
import com.light.gameserver.ai2.AI2Request;
import com.light.gameserver.ai2.AIName;
import com.light.gameserver.ai2.NpcAI2;
import com.light.gameserver.configs.main.CustomConfig;
import com.light.gameserver.dao.PlayerBindPointDAO;
import com.light.gameserver.dataholders.DataManager;
import com.light.gameserver.model.Race;
import com.light.gameserver.model.TribeClass;
import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.model.gameobjects.PersistentState;
import com.light.gameserver.model.gameobjects.player.BindPointPosition;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.templates.BindPointTemplate;
import com.light.gameserver.network.aion.serverpackets.SM_LEVEL_UPDATE;
import com.light.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.light.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.MathUtil;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.world.WorldType;

/**
 * @author ATracer
 */
@AIName("resurrect")
public class ResurrectAI2 extends NpcAI2 {

	private static Logger log = LoggerFactory.getLogger(ResurrectAI2.class);

	@Override
	protected void handleDialogStart(Player player) {
		BindPointTemplate bindPointTemplate = DataManager.BIND_POINT_DATA.getBindPointTemplate(getNpcId());
		Race race = player.getRace();
		if (bindPointTemplate == null) {
			log.info("There is no bind point template for npc: " + getNpcId());
			return;
		}

		if (player.getBindPoint() != null
			&& player.getBindPoint().getMapId() == getPosition().getMapId()
			&& MathUtil.getDistance(player.getBindPoint().getX(), player.getBindPoint().getY(), player.getBindPoint().getZ(),
				getPosition().getX(), getPosition().getY(), getPosition().getZ()) < 20) {
			PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_ALREADY_REGISTER_THIS_RESURRECT_POINT);
			return;
		}

		WorldType worldType = player.getWorldType();
		if (!CustomConfig.ENABLE_CROSS_FACTION_BINDING) {
			if ((!getRace().equals(Race.NONE) && !getRace().equals(race)) ||
					(race.equals(Race.ASMODIANS) && getTribe().equals(TribeClass.FIELD_OBJECT_LIGHT)) ||
					(race.equals(Race.ELYOS) && getTribe().equals(TribeClass.FIELD_OBJECT_DARK))) {
				PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_BINDSTONE_CANNOT_FOR_INVALID_RIGHT(player.getCommonData().
						getOppositeRace().toString()));
				return;
			}
		}
		if (worldType == WorldType.PRISON) {
			PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_RESURRECT_POINT_FAR_FROM_NPC);
			return;
		}
		bindHere(player, bindPointTemplate);
	}

	private void bindHere(Player player, final BindPointTemplate bindPointTemplate) {

		String price = Integer.toString(bindPointTemplate.getPrice());
		AI2Actions.addRequest(this, player, SM_QUESTION_WINDOW.STR_BIND_TO_LOCATION, 0, new AI2Request() {

			@Override
			public void acceptRequest(Creature requester, Player responder) {
				// check if this both creatures are in same world
				if (responder.getWorldId() == requester.getWorldId()) {
					// check enough kinah
					if (responder.getInventory().getKinah() < bindPointTemplate.getPrice()) {
						PacketSendUtility.sendPacket(responder,
							SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_RESURRECT_POINT_NOT_ENOUGH_FEE);
						return;
					}
					else if (MathUtil.getDistance(requester, responder) > 5) {
						PacketSendUtility.sendPacket(responder, SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_RESURRECT_POINT_FAR_FROM_NPC);
						return;
					}

					BindPointPosition old = responder.getBindPoint();
					BindPointPosition bpp = new BindPointPosition(requester.getWorldId(), responder.getX(), responder.getY(),
						responder.getZ(), responder.getHeading());
					bpp.setPersistentState(old == null ? PersistentState.NEW : PersistentState.UPDATE_REQUIRED);
					responder.setBindPoint(bpp);
					if (DAOManager.getDAO(PlayerBindPointDAO.class).store(responder)) {
						responder.getInventory().decreaseKinah(bindPointTemplate.getPrice());
						TeleportService.sendSetBindPoint(responder);
						PacketSendUtility.broadcastPacket(responder, new SM_LEVEL_UPDATE(responder.getObjectId(), 2, responder.getCommonData().getLevel()), true);
						PacketSendUtility.sendPacket(responder, SM_SYSTEM_MESSAGE.STR_DEATH_REGISTER_RESURRECT_POINT("")); //TODO
						old = null;
					}
					else
						// if any errors happen, left that player with old bind point
						responder.setBindPoint(old);
				}
			}
		}, price);
	}

}
