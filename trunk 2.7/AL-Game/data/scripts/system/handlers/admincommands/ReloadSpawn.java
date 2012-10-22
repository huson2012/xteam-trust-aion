package admincommands;

import com.light.gameserver.model.gameobjects.Gatherable;
import com.light.gameserver.model.gameobjects.Npc;
import com.light.gameserver.model.gameobjects.StaticObject;
import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.spawnengine.SpawnEngine;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.world.knownlist.Visitor;
import com.light.gameserver.utils.PacketSendUtility;

/**
 * @author Luno
 */
public class ReloadSpawn extends ChatCommand {

	public ReloadSpawn() {
		super("reload_spawn");
	}

	@Override
	public void execute(Player player, String... params) 
	{
		int worldId = 0;
		if (params.length == 1 && "this".equals(params[0])) 
		{
			worldId = player.getWorldId();
		}

		final int worldIdFinal = worldId;
		// despawn all
		World.getInstance().doOnAllObjects(new Visitor<VisibleObject>() 
		{

			@Override
			public void visit(VisibleObject object) 
			{
				if (worldIdFinal != 0 && object.getWorldId() != worldIdFinal) 
				{
					return;
				}
				if (object instanceof Npc || object instanceof Gatherable || object instanceof StaticObject) 
				{
					object.getController().delete();
				}
			}
		});

		try 
		{
		if (worldId == 0)
		{
			SpawnEngine.spawnAll();
			PacketSendUtility.sendMessage(player, "Спавны успешно перезагруженны!");
		}
		else 
		{
			SpawnEngine.spawnWorldMap(worldId);
			PacketSendUtility.sendMessage(player, "Спавны успешно перезагруженны!");
		}
		}
		catch (Exception e)
		{
		 PacketSendUtility.sendMessage(player, "Errors log: "+ e +"");
		 return;
		}
	}

	@Override
	public void onFail(Player player, String message) 
	{
		// TODO Auto-generated method stub
		PacketSendUtility.sendMessage(player, "Error");
	}
}
