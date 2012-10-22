package admincommands;

import com.light.gameserver.configs.administration.AdminConfig;
import com.light.gameserver.model.PlayerClass;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.instance.InstanceService;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.world.WorldMap;
import com.light.gameserver.world.WorldMapInstance;

/**
 * admin GotoMeAll command.
 * 
 * @author Alex
 */
public class GotoMeAll extends ChatCommand 
{

	public GotoMeAll() 
	{
		super("gmtomeall");
	}

	@Override
	public void execute(final Player player, String... params) 
	{
            for (Player pa : World.getInstance().getAllPlayers()){

			if (player.getWorldId() == AdminConfig.LOC_ID){
			if(pa.getCommonData().getPlayerClass() == PlayerClass.ASSASSIN)
                  {
                     goTo(pa, 300090000, 196, 290, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(pa, "Вы переноситесь в FAN зону, точка Убийц.");
                  }
                  else if(pa.getCommonData().getPlayerClass() == PlayerClass.CLERIC)
                  {
                     goTo(pa, 300090000, 192, 219, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(pa, "Вы переноситесь в FAN зону, точка Целителей.");
                  }
				  else if(pa.getCommonData().getPlayerClass() == PlayerClass.GLADIATOR)
                  {
                     goTo(pa, 300090000, 191, 149, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(pa, "Вы переноситесь в FAN зону, точка Гладиаторов.");
                  }
				  else if(pa.getCommonData().getPlayerClass() == PlayerClass.TEMPLAR)
                  {
                     goTo(pa, 300090000, 263, 118, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(pa, "Вы переноситесь в FAN зону, точка Стражей.");
                  }
				  else if(pa.getCommonData().getPlayerClass() == PlayerClass.RANGER)
                  {
                     goTo(pa, 300090000, 332, 147, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(pa, "Вы переноситесь в FAN зону, точка Лучников.");
                  }
				  else if(pa.getCommonData().getPlayerClass() == PlayerClass.SORCERER)
                  {
                     goTo(pa, 300090000, 361, 217, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(pa, "Вы переноситесь в FAN зону, точка Волшебников.");
                  }
				  else if(pa.getCommonData().getPlayerClass() == PlayerClass.SPIRIT_MASTER)
                  {
                     goTo(pa, 300090000, 333, 285, 103);
                     PacketSendUtility.sendYellowMessageOnCenter(pa, "Вы переноситесь в FAN зону, точка Заклинателей.");
                  }
				  else if(pa.getCommonData().getPlayerClass() == PlayerClass.CHANTER)
                  {
                     goTo(pa, 300090000, 263, 342, 105);
                     PacketSendUtility.sendYellowMessageOnCenter(pa, "Вы переноситесь в FAN зону, точка Чародеев.");
                  }
			}
			else {
                TeleportService.teleportTo(pa, player.getWorldId(), player.getInstanceId(), player.getX(), player.getY(), player.getZ(), player.getHeading(), 3000, true);
                PacketSendUtility.sendMessage(player, ""+ pa.getName() +" телепортируется к вам");
                PacketSendUtility.sendMessage(pa, "Администратор "+player.getName()+" телепортирует вас к себе");
				}
				
            }
        }

        public static void goTo(final Player player, int worldId, float x, float y, float z) {
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
	@Override
	public void onFail(Player player, String message) 
	{
		PacketSendUtility.sendMessage(player, "syntax //GotoMeAll");
	}
        
}