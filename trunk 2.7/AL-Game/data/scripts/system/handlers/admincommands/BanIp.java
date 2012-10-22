package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.loginserver.LoginServer;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.global.additions.MessagerAddition;

/**
 * @author Watson
 */
public class BanIp extends ChatCommand {

	public BanIp() {
		super("banip");
	}

	@Override
	public void execute(Player player, String... params) 
	{
		if (params == null || params.length < 1) 
		{
			sendInfo(player);
			return;
		}

	try
	{
		String mask = params[0];

		int time = 0; // Default: infinity
		if (params.length > 1) {
			try {
				time = Integer.parseInt(params[1]);
			}
			catch (NumberFormatException e) {
				onFail(player, e.getMessage());
				return;
			}
		}
		
		LoginServer.getInstance().sendBanPacket((byte) 2, 0, mask, time, player.getObjectId());
		if (time == 0)
		{
		 MessagerAddition.announceAll("Администратор "+ player.getName() +" забанил IP персонажа "+ mask +" на все время.", 0);
		}
		else
		{
		  MessagerAddition.announceAll("Администратор "+ player.getName() +" забанил IP персонажа "+ mask +" на "+ time +" мин.", 0);
		}  
	}
	catch (Exception e)
	{
	 sendInfo(player);
	}
	}

	@Override
	public void onFail(Player player, String message) 
	{
		sendInfo(player);
	}
	private void sendInfo(Player player)
	{
	 PacketSendUtility.sendMessage(player, "Syntax: //banip <mask> [время в минутах]");
	}
}
