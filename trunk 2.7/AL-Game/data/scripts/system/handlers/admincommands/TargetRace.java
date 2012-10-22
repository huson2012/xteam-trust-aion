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
public class TargetRace extends ChatCommand 
{

	public TargetRace() 
	{
		super("trace");
	}

	@Override
	public void execute(Player admin, String... params) 
	{
		if (params == null || params.length < 1) 
		{
			PacketSendUtility.sendMessage(admin, "syntax: //trace <ely | asmo>");
			return;
		}

		VisibleObject visibleobject = admin.getTarget();

		if (visibleobject == null || !(visibleobject instanceof Player)) 
		{
			PacketSendUtility.sendMessage(admin, "Выберите таргет");
			return;
		}

		Player target = (Player) visibleobject;
		if (params[0].equalsIgnoreCase("ely")) 
		{
			target.getCommonData().setRace(Race.ELYOS);
		}
		else if (params[0].equalsIgnoreCase("asmo")) 
		{
			target.getCommonData().setRace(Race.ASMODIANS);
		}
		PacketSendUtility.sendMessage(admin,
			target.getName() + " race has been changed to " + params[0] + ".\n" + target.getName()
				+ " has been moved to town.");
	}

	@Override
	public void onFail(Player player, String message) 
	{
		PacketSendUtility.sendMessage(player, "syntax: //trace <ely | asmo>");
	}
}
