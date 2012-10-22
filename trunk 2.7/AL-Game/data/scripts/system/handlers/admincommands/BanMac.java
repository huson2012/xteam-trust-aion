package admincommands;

import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.BannedMacManager;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.global.additions.MessagerAddition;

/**
 * @author KID
 */
public class BanMac extends ChatCommand {

	public BanMac() {
		super("banmac");
	}

	@Override
	public void execute(Player player, String... params) {
		if (params == null || params.length < 1) {
			PacketSendUtility.sendMessage(player, "Syntax: //banmac <mac> [время в минутах]");
			return;
		}

		String address = params[0];

		int time = 0; // Default: infinity
		if (params.length > 1) {
			try {
				time = Integer.parseInt(params[1]);
			}
			catch (NumberFormatException e) {
				PacketSendUtility.sendMessage(player, "Syntax: //banmac <mac> [время в минутах]");
				return;
			}
		}

		VisibleObject target = player.getTarget();
		String targetName = "direct_type";
		if (target != null && target instanceof Player) {
			if (target.getObjectId() == player.getObjectId()) {
				PacketSendUtility.sendMessage(player, "Нельзя банить себя О_о.");
				return;
			}

			Player targetpl = (Player) target;
			address = targetpl.getClientConnection().getMacAddress();
			targetName = targetpl.getName();
			targetpl.getClientConnection().closeNow();
			MessagerAddition.announceAll("Администратор "+ player.getName() +", забанил нахуй по мак адресу персонажа "+ 
			target.getName() +", на "+ time +" мин.", 0);
		}

		BannedMacManager.getInstance().banAddress(address, System.currentTimeMillis() + time * 60000,
			"author=" + player.getName() + ", " + player.getObjectId() + "; target=" + targetName);
	}

	@Override
	public void onFail(Player player, String message) 
	{
		PacketSendUtility.sendMessage(player, "Syntax: //banmac <mac> [время в минутах]");
	}

}
