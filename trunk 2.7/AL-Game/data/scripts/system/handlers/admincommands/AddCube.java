package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.CubeExpandService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;

/**
 * @author Kamui
 *
 */
public class AddCube extends ChatCommand {


	public AddCube() {
		super("cube");
	}

	@Override
	public void execute(Player admin, String... params) {

		if (params.length != 1) {
			PacketSendUtility.sendMessage(admin, "Syntax: //cube <имя игрока>");
			return;
		}

		Player receiver = null;

		receiver = World.getInstance().findPlayer(Util.convertName(params[0]));

		if (receiver == null) {
			PacketSendUtility.sendMessage(admin, "Указанный игрок "+ Util.convertName(params[0]) +" не онлайн.");
			return;
		}

		if (receiver != null) {
			if (receiver.getNpcExpands() < 12) {
				CubeExpandService.expand(receiver, true);
				PacketSendUtility.sendMessage(admin, "9 слотов были успешно добавленны персонажу "+receiver.getName()+"!");
				PacketSendUtility.sendMessage(receiver, "Администратор "+admin.getName()+" расширил ваш куб!");
			}
			else {
				PacketSendUtility.sendMessage(admin, "Куб не может быть расширен "+receiver.getName()+"!\nReason: Куб игрока достиг максимального значения.");
				return;
			}
		}
	}
}
