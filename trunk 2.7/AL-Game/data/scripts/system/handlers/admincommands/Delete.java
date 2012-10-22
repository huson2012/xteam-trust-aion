package admincommands;

import java.io.IOException;

import com.light.gameserver.dataholders.DataManager;
import com.light.gameserver.model.gameobjects.Npc;
import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.templates.spawns.siegespawns.SiegeSpawnTemplate;
import com.light.gameserver.model.templates.spawns.SpawnTemplate;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author Luno
 */
public class Delete extends ChatCommand {

	public Delete() {
		super("delete");
	}

	@Override
	public void execute(Player player, String... params) {

		VisibleObject cre = player.getTarget();
		if (!(cre instanceof Npc)) {
			PacketSendUtility.sendMessage(player, "Wrong target");
			return;
		}
		Npc npc = (Npc) cre;
		SpawnTemplate template = npc.getSpawn();
		if (template.hasPool()) {
			PacketSendUtility.sendMessage(player, "Can't delete pooled spawn template");
			return;
		}
		if (template instanceof SiegeSpawnTemplate) {
			PacketSendUtility.sendMessage(player, "Can't delete siege spawn template");
			return;
		}
		npc.getController().delete();
		try {
			DataManager.SPAWNS_DATA2.saveSpawn(player, npc, true);
		}
		catch (IOException e) {
			e.printStackTrace();
			PacketSendUtility.sendMessage(player, "Could not remove spawn");
			return;
		}
		PacketSendUtility.sendMessage(player, "Spawn removed");
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
