package admincommands;

import com.light.gameserver.configs.main.CustomConfig;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author Nemiroff Date: 11.01.2010
 */
public class Unstuck extends ChatCommand {

	public Unstuck() {
		super("unstuck");
	}

	@Override
	public void execute(Player player, String... params) {
		if (player.getLifeStats().isAlreadyDead()) {
			PacketSendUtility.sendMessage(player, "You dont have execute this command. You die");
			return;
		}
		if (player.isInPrison()) {
			PacketSendUtility.sendMessage(player, "You can't use the unstuck command when you are in Prison");
			return;
		}

		TeleportService.moveToBindLocation(player, true, CustomConfig.UNSTUCK_DELAY);
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
