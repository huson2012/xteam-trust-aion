package weddingcommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;

/**
 * @author synchro2
 */
public class missyou extends ChatCommand {

	public missyou() {
		super("missyou");
	}

	@Override
	public void execute(Player player, String... params) {

		Player partner = player.findPartner();
		if (partner == null) {
			PacketSendUtility.sendMessage(player, "Not online.");
			return;
		}
		if (player.getWorldId() == 510010000 || player.getWorldId() == 520010000) {
			PacketSendUtility.sendMessage(player, "You can't use this command on prison.");
			return;
		}
		if (partner.getWorldId() == 510010000 || partner.getWorldId() == 520010000) {
			PacketSendUtility.sendMessage(player, "You can't teleported to " + partner.getName() +", your partner is on prison.");
			return;
		}

		TeleportService.teleportTo(player, partner.getWorldId(), partner.getInstanceId(), partner.getX(), partner.getY(),
			partner.getZ(), partner.getHeading(), 3000, true);
		PacketSendUtility.sendMessage(player, "Teleported to player " + partner.getName() + ".");
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "Failed");
	}
}
