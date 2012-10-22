package admincommands;

import java.util.NoSuchElementException;

import java.util.Iterator;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.PunishmentService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;

/**
 * @author lord_rex Command: //rprison <player> This command is removing player from prison.
 */
public class RPrison extends ChatCommand {

	public RPrison() {
		super("rprison");
	}

	@Override
	public void execute(Player admin, String... params) {
		if (params.length == 0 || params.length > 2) {
			PacketSendUtility.sendMessage(admin, "syntax //rprison <имя персонажа>");
			return;
		}

		try {
			Player playerFromPrison = World.getInstance().findPlayer(Util.convertName(params[0]));
			Iterator<Player> iter = World.getInstance().getPlayersIterator();

			if (playerFromPrison != null) {
				PunishmentService.setIsInPrison(playerFromPrison, false, 0, "", "");
				PacketSendUtility.sendMessage(admin, "Персонаж " + playerFromPrison.getName() + " освобождается из тюрьмы.");
				String message = "Администратор: "+ admin.getName() +", освобождает персонажа "+ playerFromPrison.getName() +" из тюрьмы.";
				while (iter.hasNext()) 
				{
			       PacketSendUtility.sendBrightYellowMessageOnCenter(iter.next(), message);
		        }
			}
		}
		catch (NoSuchElementException nsee) {
			PacketSendUtility.sendMessage(admin, "Использовать: //rprison <имя персонажа>");
		}
		catch (Exception e) {
			PacketSendUtility.sendMessage(admin, "Использовать: //rprison <имя персонажа>");
		}
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "syntax //rprison <имя персонажа>");
	}
}
