package admincommands;

import com.aionemu.commons.database.dao.DAOManager;
import com.light.gameserver.dao.PlayerDAO;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.PunishmentService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author nrg
 */
public class UnBanChar extends ChatCommand {

	public UnBanChar() {
		super("unbanchar");
	}

	@Override
	public void execute(Player admin, String... params) {
		if (params == null || params.length < 1) {
			PacketSendUtility.sendMessage(admin, "Syntax: //unbanchar <player>");
			return;
		}

		// Banned player must be offline
		String name = Util.convertName(params[0]);
		int playerId = DAOManager.getDAO(PlayerDAO.class).getPlayerIdByName(name);
		if (playerId == 0) {
			PacketSendUtility.sendMessage(admin, "Player " + name + " was not found!");
			PacketSendUtility.sendMessage(admin, "Syntax: //unbanchar <player>");
			return;
		}

		PacketSendUtility.sendMessage(admin, "Character " + name + " is not longer banned!");
		
    PunishmentService.unbanChar(playerId);
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "Syntax: //unban <player> [account|ip|full]");
	}
}
