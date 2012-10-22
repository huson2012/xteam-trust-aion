package admincommands;

import com.aionemu.commons.database.dao.DAOManager;
import com.light.gameserver.dao.PlayerDAO;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.loginserver.LoginServer;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.global.additions.MessagerAddition;

/**
 * @author Watson
 */
public class Ban extends ChatCommand {

	public Ban() {
		super("ban");
	}

	@Override
	public void execute(Player admin, String... params) 
	{
		if (params == null || params.length < 1) {
			PacketSendUtility.sendMessage(admin, "Syntax: //ban <player> [account|ip|full] [время в минутах]");
			return;
		}

		// We need to get player's account ID
		String name = Util.convertName(params[0]);
		int accountId = 0;
		String accountIp = "";

		// First, try to find player in the World
		Player player = World.getInstance().findPlayer(name);
		if (player != null) 
		{
			accountId = player.getClientConnection().getAccount().getId();
			accountIp = player.getClientConnection().getIP();
		}

		// Second, try to get account ID of offline player from database
		if (accountId == 0)
			accountId = DAOManager.getDAO(PlayerDAO.class).getAccountIdByName(name);

		// Third, fail
		if (accountId == 0) 
		{
			PacketSendUtility.sendMessage(admin, "Персонаж " + name + " не был найден!");
			PacketSendUtility.sendMessage(admin, "Syntax: //ban <player> [account|ip|full] [время в минутах]");
			return;
		}

	try
	{
		byte type = 3; // Default: full
		String stype = params[1].toLowerCase();
		if (params.length > 1) 
		{
			// Smart Matching
			if (("account").startsWith(stype))
				type = 1;
			else if (("ip").startsWith(stype))
				type = 2;
			else if (("full").startsWith(stype))
				type = 3;
			else {
				PacketSendUtility.sendMessage(admin, "Syntax: //ban <player> [account|ip|full] [время в минутах]");
				return;
			}
		}

		int time = 0; // Default: infinity
		if (params.length > 2) 
		{
			try 
			{
				time = Integer.parseInt(params[2]);
			}
			catch (NumberFormatException e) 
			{
				PacketSendUtility.sendMessage(admin, "Syntax: //ban <player> [account|ip|full] [время в минутах]");
				return;
			}
		}

		LoginServer.getInstance().sendBanPacket(type, accountId, accountIp, time, admin.getObjectId());
		if (type == 1)
		{
		 MessagerAddition.announceAll("Администратор "+ admin.getName() +" забанил персонажа "+ name +" на "+ time +" мин. по акаунту", 0);
		}
		else if (type == 2)
		{
		 MessagerAddition.announceAll("Администратор "+ admin.getName() +" забанил персонажа "+ name +" на "+ time +" мин. по IP", 0);
		 }
		else if (type == 3)
		{
		 MessagerAddition.announceAll("Администратор "+ admin.getName() +" забанил персонажа "+ name +" на "+ time +" мин. полностью", 0);
		}
		 else
		{
		  SendInfo(admin);
		  return;
		}
	}
	catch (Exception e)
	{
	 SendInfo(admin);
	}
	}


	public void SendInfo(Player player)
	{
	 PacketSendUtility.sendMessage(player, "Syntax: //ban <player> [account|ip|full] [время в минутах]");
	}
	
	@Override
	public void onFail(Player player, String message)
	{
		SendInfo(player);
	}
}
