package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.HTMLService;
import com.light.gameserver.services.instance.InstanceService;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.model.Race;
import com.light.gameserver.world.WorldMap;
import com.light.gameserver.world.WorldMapInstance;
import com.light.gameserver.world.WorldMapType;

/**
 * PvP command
 * 
 * @author Drusik
 * @rework Imaginary
 */
public class PvP extends ChatCommand{

	public PvP() {
		super("pvp");
	}

	@Override
	public void execute(Player player, String... params) {
		if (params == null || params.length < 1) {
			PacketSendUtility.sendMessage(player, "Вводим .pvp < ely | asmo >");
			return;
		}
		
		StringBuilder sbDestination = new StringBuilder();
		for(String p : params)
			sbDestination.append(p + " ");
		
		String destination = sbDestination.toString().trim();
				
		if (player.getWorldId() == 210050000) {
			PacketSendUtility.sendMessage(player, "Вы уже находитесь в зоне PvP.");
			return;
		}
		
		else if (params[0].toLowerCase().equals("home"))
                {
                        if(player.getCommonData().getRace() == Race.ELYOS)
                        {
                                        goTo(player, 210050000, 1314, 1657, 363);
                                        PacketSendUtility.sendMessage(player, "Вы переноситесь в форт Элийцев.");
                        }
                        else if(player.getCommonData().getRace() == Race.ASMODIANS)
                        {
                                        goTo(player, 210050000, 1773, 1294, 380);
                                        PacketSendUtility.sendMessage(player, "Вы переноситесь в форт Асмодиан.");
                        }
        }
           		
		else
			PacketSendUtility.sendMessage(player, "Возможно вы ошиблись при вводе команды!");
	}
	
	private static void goTo(final Player player, int worldId, float x, float y, float z) {
		WorldMap destinationMap = World.getInstance().getWorldMap(worldId);
		if (destinationMap.isInstanceType())
			TeleportService.teleportTo(player, worldId, getInstanceId(worldId, player), x, y, z, 3000 ,true);
		else
			TeleportService.teleportTo(player, worldId, x, y, z, 3000, true);
	}
	
	private static int getInstanceId(int worldId, Player player) {
		if (player.getWorldId() == worldId)	{
			WorldMapInstance registeredInstance = InstanceService.getRegisteredInstance(worldId, player.getObjectId());
			if (registeredInstance != null)
				return registeredInstance.getInstanceId();
		}
		WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(worldId);
		InstanceService.registerPlayerWithInstance(newInstance, player);
		return newInstance.getInstanceId();
	}
}