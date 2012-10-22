package admincommands;

import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
/**
 * @author Alex
 */
public class Invuls extends ChatCommand
{

	public Invuls() {
		super("invuls");
	}

	@Override
	public void execute(Player player, String... params) {
            VisibleObject visibleobject = player.getTarget();

		if (visibleobject == null || !(visibleobject instanceof Player)) {
			PacketSendUtility.sendMessage(player, "Wrong select target.");
			return;
		}

		Player target = (Player) visibleobject;
		if (target.isInvul()) {
			target.setInvul(false);
			PacketSendUtility.sendMessage(target, "You are now mortal.");
		}
		else {
			target.setInvul(true);
			PacketSendUtility.sendMessage(target, "You are now immortal.");
		}
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
