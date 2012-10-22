package admincommands;

import com.aionemu.commons.database.dao.DAOManager;
import com.light.gameserver.dao.PlayerDAO;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.PunishmentService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.global.additions.MessagerAddition;

/**
 * @author nrg
 */
public class BanChar extends ChatCommand {

	public BanChar() {
		super("banchar");
	}

	@Override
	public void execute(Player admin, String... params) {
		if (params == null || params.length < 3) {
			sendInfo(admin, true);
			return;
		}
		
	try {	
		int playerId = 0;
		String playerName = Util.convertName(params[0]);

		// First, try to find player in the World
		Player player = World.getInstance().findPlayer(playerName);
		if (player != null)
			playerId = player.getObjectId();

		// Second, try to get player Id from offline player from database
		if (playerId == 0)
			playerId = DAOManager.getDAO(PlayerDAO.class).getPlayerIdByName(playerName);

		// Third, fail
		if (playerId == 0) {
			PacketSendUtility.sendMessage(admin, "Player " + playerName + " was not found!");
			sendInfo(admin, true);
			return;
		}

		int dayCount = -1;
		try {
			dayCount = Integer.parseInt(params[1]);
		}
		catch (NumberFormatException e) {
			PacketSendUtility.sendMessage(admin, "неверный параметр int dayCount");
			sendInfo(admin, true);
			return;
		}
		
		if(dayCount < 0) {
			PacketSendUtility.sendMessage(admin, "Параметр не должен быть нулевым, так как время в днях");
			sendInfo(admin, true);;
			return;
		}

		String reason = Util.convertName(params[2]);
		for(int itr = 3; itr < params.length; itr++)
			reason += " "+params[itr];

		String er = " ";
		if (dayCount == 1) {er = "день";}
		else if (dayCount == 2 || dayCount == 3 || dayCount == 4) {er = "дня";}
		else if (dayCount >= 5) {er = "дней";}
		PacketSendUtility.sendMessage(admin, "Персонаж " + playerName + " забанен на "+dayCount+" "+ er +"!");
		MessagerAddition.announceAll("Администратор "+ admin.getName() +" выдал 'бан персонажа' игроку " + 
		 playerName + " на "+ dayCount +" "+ er +". Причина: "+ reason +".", 0);
		
		PunishmentService.banChar(playerId, dayCount, reason);
	}
	catch (Exception e)
	{
	 sendInfo(admin, false);
	}
	}

	@Override
	public void onFail(Player player, String message) {
		sendInfo(player, false);
	}
	
	private void sendInfo(Player player, boolean withNote) {
		PacketSendUtility.sendMessage(player, "Syntax: //banChar <имя персонажа> <days>/0 (for permanent) <причина>");
		if(withNote)
		  PacketSendUtility.sendMessage(player, "Инфо: the current day is defined as a whole day even if it has just a few hours left!");
	}
}
