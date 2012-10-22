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
package com.light.gameserver.services;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.light.gameserver.configs.main.CustomConfig;
import com.light.gameserver.dao.LegionDAO;
import com.light.gameserver.dao.OldNamesDAO;
import com.light.gameserver.dao.PlayerDAO;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.aion.serverpackets.SM_RENAME;
import com.light.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.light.gameserver.services.player.PlayerService;
import com.light.gameserver.utils.audit.AuditLogger;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.world.World;

/**
 * @author ATracer modified cura
 */
public class RenameService {

	private static Logger log = LoggerFactory.getLogger(RenameService.class);

	public static boolean renamePlayer(Player player, String oldName, String newName, int item) {
		if (!PlayerService.isValidName(newName)) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400151));
			return false;
		}
		if (!PlayerService.isFreeName(newName)) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400155));
			return false;
		}
		if (player.getName().equals(newName)) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400153));
			return false;
		}
		if (!CustomConfig.OLD_NAMES_COUPON_DISABLED && PlayerService.isOldName(newName)) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400155));
			return false;
		}
		if ((player.getInventory().getItemByObjId(item).getItemId() != 169670000 && player.getInventory()
			.getItemByObjId(item).getItemId() != 169670001)
			|| (!player.getInventory().decreaseByObjectId(item, 1))) {
			AuditLogger.info(player, "Try rename youself without coupon.");
			return false;
		}
		if (!CustomConfig.OLD_NAMES_COUPON_DISABLED)
			DAOManager.getDAO(OldNamesDAO.class).insertNames(player.getObjectId(), player.getName(), newName);
		player.getCommonData().setName(newName);

		Iterator<Player> onlinePlayers = World.getInstance().getPlayersIterator();
		while (onlinePlayers.hasNext()) {
			Player p = onlinePlayers.next();
			if (p != null && p.getClientConnection() != null)
				PacketSendUtility.sendPacket(p, new SM_RENAME(player.getObjectId(), oldName, newName));
		}
		DAOManager.getDAO(PlayerDAO.class).storePlayer(player);

		return true;
	}

	public static boolean renameLegion(Player player, String name, int item) {
		if (!player.isLegionMember())
			return false;
		if (!LegionService.getInstance().isValidName(name)) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400152));
			return false;
		}
		if (DAOManager.getDAO(LegionDAO.class).isNameUsed(name)) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400156));
			return false;
		}
		if (player.getLegion().getLegionName().equals(name)) {
			PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400154));
			return false;
		}
		if ((player.getInventory().getItemByObjId(item).getItemId() != 169680000 && player.getInventory()
			.getItemByObjId(item).getItemId() != 169680001)
			|| (!player.getInventory().decreaseByObjectId(item, 1))) {
			AuditLogger.info(player, "Try rename legion without coupon.");
			return false;
		}
		LegionService.getInstance().setLegionName(player.getLegion(), name, true);

		return true;
	}
}
