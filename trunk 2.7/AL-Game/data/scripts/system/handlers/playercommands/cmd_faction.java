package playercommands;

import com.light.gameserver.configs.main.CustomConfig;
import com.light.gameserver.model.Race;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.items.storage.Storage;
import com.light.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.light.gameserver.services.player.PlayerChatService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import org.apache.commons.lang.StringUtils;

/**
 * @author Shepper
 */
public class cmd_faction extends ChatCommand {

	public cmd_faction() {
		super("world");
	}

	@Override
	public void execute(Player player, String... params) {
		Storage sender = player.getInventory();

		if (!CustomConfig.FACTION_CMD_CHANNEL) {
			PacketSendUtility.sendMessage(player, "Команда временно отключена.");
			return;
		}

		if (params == null || params.length < 1) {
			PacketSendUtility.sendMessage(player, ".world <сообщение>");
			return;
		}

		if (player.getWorldId() == 510010000 || player.getWorldId() == 520010000) {
			PacketSendUtility.sendMessage(player, "Вы не можете использовать команду в тюрьме.");
			return;
		}
		else if (player.isGagged()) {
			PacketSendUtility.sendMessage(player, "Вы не можете использовать команду в состоянии немоты.");
			return;
		}

		if (!CustomConfig.FACTION_FREE_USE) {
			if (sender.getKinah() > CustomConfig.FACTION_USE_PRICE)
				sender.decreaseKinah(CustomConfig.FACTION_USE_PRICE);
			else {
				PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_NOT_ENOUGH_MONEY);
				return;
			}
		}

		String message = StringUtils.join(params, " ");

		if (!PlayerChatService.isFlooding(player)) {
			message = player.getName() + ": " + message;
			for (Player a : World.getInstance().getAllPlayers()) {
					PacketSendUtility.sendMessage(a, (player.getRace() == Race.ASMODIANS ? "[A] " : "[E] ") + message);
			}
		}
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}

}