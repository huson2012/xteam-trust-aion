package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author cura
 */
public class Teleportation extends ChatCommand {

	public Teleportation() {
		super("teleportation");
	}

	@Override
	public void execute(Player player, String... params) {
		boolean isTeleportation = player.getAdminTeleportation();

		if (isTeleportation) {
			PacketSendUtility.sendMessage(player, "Teleported state is disabled.");
			player.setAdminTeleportation(false);
		}
		else {
			PacketSendUtility.sendMessage(player, "Teleported state.");
			player.setAdminTeleportation(true);
		}
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
