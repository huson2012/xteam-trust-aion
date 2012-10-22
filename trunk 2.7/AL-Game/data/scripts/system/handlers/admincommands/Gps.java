package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.geo.GeoService;

/**
 * @author GoodT
 */
public class Gps extends ChatCommand
{
	public Gps()
	{
		super("gps");
	}

	@Override
	public void execute(Player admin, String... params)
	{
		PacketSendUtility.sendMessage(admin, "== GPS Coordinates ==");
		PacketSendUtility.sendMessage(admin, "X = " + admin.getX());
		PacketSendUtility.sendMessage(admin, "Y = " + admin.getY());
		PacketSendUtility.sendMessage(admin, "Z = " + admin.getZ());
		PacketSendUtility.sendMessage(admin, "GeoZ = " + GeoService.getInstance().getZ(admin));
		PacketSendUtility.sendMessage(admin, "H = " + admin.getHeading());
		PacketSendUtility.sendMessage(admin, "World = " + admin.getWorldId());
	}
        
	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}