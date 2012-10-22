package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

public class Chat extends ChatCommand {

	public Chat() {
		super("chat");
	}

	@Override
	public void execute(Player admin, String... params) {

		if(params[0].equalsIgnoreCase("off")) {
			admin.setUnWispable();
			PacketSendUtility.sendMessage(admin, "Ваш ПМ в режиме : offline");
		}
		else if (params[0].equalsIgnoreCase("on")) {
			admin.setWispable();
			PacketSendUtility.sendMessage(admin, "Ваш ПМ в режиме : online");
		}
	}	

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "syntax //chat [on включит ваш ПМ / off выключит ваш ПМ]");
	}
}
