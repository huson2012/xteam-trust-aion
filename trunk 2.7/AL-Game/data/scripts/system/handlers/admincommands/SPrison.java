package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.PunishmentService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.model.gameobjects.VisibleObject;

/**
 * @author lord_rex Command: //sprison <player> <delay>(minutes) This command is sending player to prison.
 */
public class SPrison extends ChatCommand {

	public SPrison() {
		super("sprison");
	}

	@Override
	public void execute(Player admin, String... params) {
		if (params.length < 2) 
		{
			sendInfo(admin);
			return;
		}

		try 
		{
			Player playerToPrison = World.getInstance().findPlayer(Util.convertName(params[0]));
			int delay = Integer.parseInt(params[1]);
			
			String reason = Util.convertName(params[2]);
			for(int itr = 3; itr < params.length; itr++)
				reason += " "+params[itr];
				String mb = "Администратор: "+ admin.getName() +", отправил персонажа: "+ playerToPrison.getName() +" в тюрьму, на " + delay +" минут. Причина: " + reason + ".";

			if (playerToPrison != null)
			{
				 PunishmentService.setIsInPrison(playerToPrison, true, delay, reason, mb);
				 PacketSendUtility.sendMessage(playerToPrison,"Администратор: " + admin.getName() + " отправил вас [" + playerToPrison.getName() + "] в тюрьму на " + delay
					+ " минут. Причина: " + reason + ".");
				 PacketSendUtility.sendMessage(admin,"Персонаж " + playerToPrison.getName() + " отправлен в тюрьму на " + delay
					+ " минут. Причина: " + reason + ".");
			}
		}
		catch (Exception e) 
		{
		    PacketSendUtility.sendMessage(admin, "Ошибка:\n"+ e +"\nВы не ввели причину.");
			sendInfo(admin);
		}
	
	}

	@Override
	public void onFail(Player player, String message) {
		sendInfo(player);
	}
	
	private void sendInfo(Player player) {
		PacketSendUtility.sendMessage(player, "//sprison <игрок> <время> <причина>");
	}
}
