package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
* @author Alex
*
*/
public class Coll extends ChatCommand {
	public Coll() {
		super("coll");
	}

	@Override
	public void execute(Player admin, String... params) {
            PacketSendUtility.sendMessage(admin, "Ваш текущий баланс: "+admin.getDonatmoney()+" Coll");
	}
}
