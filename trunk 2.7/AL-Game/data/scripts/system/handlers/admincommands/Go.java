package admincommands;

import com.light.gameserver.cache.HTMLCache;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.HTMLService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author Alex
 */
public class Go extends ChatCommand {

	public Go() {
		super("go");
	}

	@Override
	public void execute(Player player, String... params) {
		HTMLService.showHTML(player, HTMLCache.getInstance().getHTML("go-help.xhtml"));
                PacketSendUtility.sendMessage(player, "Названия всех локаций для команды //go появились в окне голосования");    
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
