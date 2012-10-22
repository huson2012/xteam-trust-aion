package admincommands;

import com.light.gameserver.dataholders.DataManager;
import com.light.gameserver.model.drop.Drop;
import com.light.gameserver.model.drop.DropGroup;
import com.light.gameserver.model.drop.NpcDrop;
import com.light.gameserver.model.gameobjects.Npc;
import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.templates.npc.NpcTemplate;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author Oliver
 */
public class DropInfo extends ChatCommand {

	public DropInfo() {
		super("dropinfo");
	}

	@Override
	public void execute(Player player, String... params) {
		NpcDrop npcDrop = null;
		if (params.length > 0) {
			int npcId = Integer.parseInt(params[0]);
			NpcTemplate npcTemplate = DataManager.NPC_DATA.getNpcTemplate(npcId);
			if (npcTemplate == null){
				PacketSendUtility.sendMessage(player, "Incorrect npcId: "+ npcId);
				return;
			}
			npcDrop = npcTemplate.getNpcDrop();
		}
		else {
			VisibleObject visibleObject = player.getTarget();

			if (visibleObject == null) {
				PacketSendUtility.sendMessage(player, "You should target some NPC first !");
				return;
			}

			if (visibleObject instanceof Npc) {
				npcDrop = ((Npc)visibleObject).getNpcDrop();
			}
		}
		if (npcDrop == null){
			PacketSendUtility.sendMessage(player, "No drops for the selected NPC");
			return;
		}
		
		int count = 0;
		PacketSendUtility.sendMessage(player, "[Drop Info for the specified NPC]\n");
		for (DropGroup dropGroup: npcDrop.getDropGroup()){
			PacketSendUtility.sendMessage(player, "DropGroup: "+ dropGroup.getGroupName());
			for (Drop drop : dropGroup.getDrop()){
				PacketSendUtility.sendMessage(player, "[item:" + drop.getItemId() + "]" + "	Rate: " + drop.getChance());
				count ++;
			}
		}
		PacketSendUtility.sendMessage(player, count + " drops available for the selected NPC");
	}

	@Override
	public void onFail(Player player, String message) {
		// TODO Auto-generated method stub
	}
}
