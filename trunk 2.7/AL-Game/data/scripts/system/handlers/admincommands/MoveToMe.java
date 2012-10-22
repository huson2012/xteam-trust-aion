package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;

/**
 * Admin movetome command.
 * 
 * @author Cyrakuse
 */
public class MoveToMe extends ChatCommand 
{

	public MoveToMe() 
	{
		super("gotome");
	}

	@Override
	public void execute(Player player, String... params) 
	{
		if (params == null || params.length < 1)
		{
			PacketSendUtility.sendMessage(player, "syntax //gotome <имя персонажа>");
			return;
		}

		Player playerToMove = World.getInstance().findPlayer(Util.convertName(params[0]));
		if (playerToMove == null) 
		{
			PacketSendUtility.sendMessage(player, "The specified player is not online.");
			return;
		}

		if (playerToMove == player) 
		{
			PacketSendUtility.sendMessage(player, "Cannot use this command on yourself.");
			return;
		}

		TeleportService.teleportTo(playerToMove, player.getWorldId(), player.getInstanceId(), player.getX(), player.getY(),	player.getZ(), player.getHeading(), 3000, true);
		PacketSendUtility.sendMessage(player, "Teleported player " + playerToMove.getName() + " to your location.");
		PacketSendUtility.sendMessage(playerToMove, "You have been teleported by " + player.getName() + ".");
	}

	@Override
	public void onFail(Player player, String message) 
	{
		PacketSendUtility.sendMessage(player, "syntax //gotome <characterName>");
	}
}
