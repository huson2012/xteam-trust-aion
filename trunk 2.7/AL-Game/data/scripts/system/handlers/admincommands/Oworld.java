package admincommands;

import java.lang.reflect.Field;

import com.light.gameserver.configs.administration.AdminConfig;
import com.light.gameserver.configs.main.CustomConfig;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author SheppeR
 */
public class Oworld extends ChatCommand {

	public Oworld() {
		super("oworld");
	}
	
	@Override
	public void execute(Player player, String... params) {
		Class<?> classToMofify = CustomConfig.class;
		Field someField;
		try {
			someField = classToMofify.getDeclaredField("FACTION_CMD_CHANNEL");
			if (params[0].equalsIgnoreCase("on") && !CustomConfig.FACTION_CMD_CHANNEL) {
				someField.set(null, Boolean.valueOf(true));
				PacketSendUtility.sendMessage(player, "Команда .world переключена в режим online.");
			}
			else if (params[0].equalsIgnoreCase("off") && CustomConfig.FACTION_CMD_CHANNEL) {
				someField.set(null, Boolean.valueOf(false));
				PacketSendUtility.sendMessage(player, "Команда .world переключена в режим offline.");
			}
		}
		catch (Exception e) {
			PacketSendUtility.sendMessage(player, "Error! Ты явно какойто бред написал(а).");
			return;
		}
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "syntax //oworld <On | Off>");
	}
}
