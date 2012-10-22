package admincommands;

import com.light.gameserver.model.Race;
import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.WorldMapType;

/**
 * @author Centisgood(Barahime)
 */
public class Home extends ChatCommand {

	public Home() {
		super("home");
	}

	@Override
	public void execute(Player admin, String... params) 
	{
		if (params == null) {
			PacketSendUtility.sendMessage(admin, "syntax: //home ");
			return;
		}

		Player target = admin;
		if (admin.getTarget() instanceof Player)
			target = (Player) admin.getTarget();

		if (target == null)
		{
			PacketSendUtility.sendMessage(admin, "Wrong select target.");
			return;
		}

		if(target.getCommonData().getRace() == Race.ELYOS)
		{
			TeleportService.teleportTo(target, WorldMapType.SANCTUM.getId(), 1322, 1511, 568, 0);
            PacketSendUtility.sendYellowMessageOnCenter(target, "Вы переноситесь в столицу Элийцев.");
		}
		else if(target.getCommonData().getRace() == Race.ASMODIANS)
		{
			TeleportService.teleportTo(target, WorldMapType.PANDAEMONIUM.getId(), 1679, 1400, 195, 0);
            PacketSendUtility.sendYellowMessageOnCenter(target, "Вы переноситесь в столицу Асмодиан.");
		}
		PacketSendUtility.sendMessage(target, "Вы были отправлены домой.");
	}

	@Override
	public void onFail(Player player, String message) 
	{
		PacketSendUtility.sendMessage(player, "syntax: //home ");
	}
}