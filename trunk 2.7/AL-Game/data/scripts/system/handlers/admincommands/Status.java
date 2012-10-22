package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.team2.alliance.PlayerAllianceService;
import com.light.gameserver.model.team2.group.PlayerGroupService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author KID
 */
public class Status extends ChatCommand {

	public Status() {
		super("status");
	}

	@Override
	public void execute(Player admin, String... params) {
		if (params[0].equalsIgnoreCase("alliance")) {
			PacketSendUtility.sendMessage(admin, PlayerAllianceService.getServiceStatus());
		}
		else if (params[0].equalsIgnoreCase("group")) {
			PacketSendUtility.sendMessage(admin, PlayerGroupService.getServiceStatus());
		}
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "<usage //status alliance | group");
	}
}
