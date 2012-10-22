package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;

/**
 * Admin moveplayertoplayer command.
 * 
 * @author Tanelorn
 */
public class MovePlayerToPlayer extends ChatCommand {

	public MovePlayerToPlayer() {
		super("gotoplayertoplayer");
	}

	@Override
	public void execute(Player admin, String... params) {
		if (params == null || params.length < 2) {
			PacketSendUtility.sendMessage(admin,
				"syntax //gotoplayertoplayer <characterNameToMove> <characterNameDestination>");
			return;
		}

		Player playerToMove = World.getInstance().findPlayer(Util.convertName(params[0]));
		if (playerToMove == null) {
			PacketSendUtility.sendMessage(admin, "The specified player is not online.");
			return;
		}

		Player playerDestination = World.getInstance().findPlayer(Util.convertName(params[1]));
		if (playerDestination == null) {
			PacketSendUtility.sendMessage(admin, "The destination player is not online.");
			return;
		}

		if (playerToMove.getObjectId() == playerDestination.getObjectId()) {
			PacketSendUtility.sendMessage(admin, "Cannot move the specified player to their own position.");
			return;
		}

		TeleportService.teleportTo(playerToMove, playerDestination.getWorldId(), playerDestination.getInstanceId(),
			playerDestination.getX(), playerDestination.getY(), playerDestination.getZ(), playerDestination.getHeading(), 3000, true);

		PacketSendUtility.sendMessage(admin, "Teleported player " + playerToMove.getName() + " to the location of player "
			+ playerDestination.getName() + ".");
		PacketSendUtility.sendMessage(playerToMove, "You have been teleported by an administrator.");
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player,
			"syntax //gotoplayertoplayer <characterNameToMove> <characterNameDestination>");
	}
}
