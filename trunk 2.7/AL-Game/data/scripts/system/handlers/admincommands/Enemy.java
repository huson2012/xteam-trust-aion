package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.aion.serverpackets.SM_MOTION;
import com.light.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author Pan
 */
public class Enemy extends ChatCommand {

	public Enemy() {
		super("enemy");
	}

	@Override
	public void execute(Player player, String... params) {
		String help = "Syntax: //enemy < players | npcs | all | cancel >\n"
			+ "Players - Вы становитесь соперником для всех игроков.\n" + "Npcs - Вы становитесь вражеским по отношению к мобам.\n"
			+ "All - Вы становитесь вражеским ко всем игрокам и мобам.\n"
			+ "Cancel - Cancel all. Players and Npcs have default enmity to you.";

		if (params.length != 1) {
			onFail(player, null);
			return;
		}

		String output = "You now appear as enemy to АЫЫ " + params[0] + ".";

		int neutralType = player.getAdminNeutral();

		if (params[0].equals("all")) {
			player.setAdminEnmity(3);
			player.setAdminNeutral(0);
		}

		else if (params[0].equals("players")) {
			player.setAdminEnmity(2);
			if (neutralType > 1)
				player.setAdminNeutral(0);
		}

		else if (params[0].equals("npcs")) {
			player.setAdminEnmity(1);
			if (neutralType == 1 || neutralType == 3)
				player.setAdminNeutral(0);
		}

		else if (params[0].equals("cancel")) {
			player.setAdminEnmity(0);
			output = "You appear regular to both Players and Npcs.";
		}

		else if (params[0].equals("help")) {
			PacketSendUtility.sendMessage(player, help);
			return;
		}

		else {
			onFail(player, null);
			return;
		}

		PacketSendUtility.sendMessage(player, output);

		player.clearKnownlist();
		PacketSendUtility.sendPacket(player, new SM_PLAYER_INFO(player, false));
		PacketSendUtility.sendPacket(player, new SM_MOTION(player.getObjectId(), player.getMotions().getActiveMotions()));
		player.updateKnownlist();
	}

	@Override
	public void onFail(Player player, String message) {
		String syntax = "Syntax: //enemy < players | npcs | all | cancel >\nIf you're unsure about what you want to do, type //enemy help";
		PacketSendUtility.sendMessage(player, syntax);
	}
}
