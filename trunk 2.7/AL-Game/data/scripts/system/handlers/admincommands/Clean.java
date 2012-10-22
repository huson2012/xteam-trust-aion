package admincommands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Collections;
import com.light.gameserver.model.items.storage.Storage;
import com.light.gameserver.model.items.storage.StorageType;
import com.light.gameserver.network.aion.InventoryPacketType;
import javolution.util.FastList;
import com.light.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;

import com.light.gameserver.model.gameobjects.Item;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.templates.item.ItemTemplate;
import com.light.gameserver.network.aion.AionConnection;
import com.light.gameserver.network.aion.AionServerPacket;
import com.light.gameserver.network.aion.iteminfo.ItemInfoBlob;
import com.light.gameserver.network.aion.iteminfo.ItemInfoBlob.ItemBlobType;
import com.light.gameserver.services.item.ItemPacketService.ItemUpdateType;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.services.AdminService;
import com.light.gameserver.services.item.ItemService;
import com.light.gameserver.utils.Util;
import com.light.gameserver.dataholders.DataManager;
import com.light.gameserver.network.aion.serverpackets.SM_INVENTORY_UPDATE_ITEM;
import com.light.gameserver.network.aion.serverpackets.SM_TARGET_UPDATE;
import com.light.gameserver.network.aion.serverpackets.SM_UPDATE_NOTE;
import com.light.gameserver.network.aion.serverpackets.SM_HEADING_UPDATE;
import com.light.gameserver.services.item.ItemPacketService;
import com.light.gameserver.services.item.ItemPacketService.ItemUpdateType;
import com.light.gameserver.services.item.ItemPacketService.ItemDeleteType;
import javolution.util.FastList;
import com.light.gameserver.network.aion.serverpackets.*;
import com.light.gameserver.model.gameobjects.VisibleObject;


public class Clean extends ChatCommand {

    public Clean() {
        super("clean");
    }

    @Override
    public void execute(Player player, String... params) {

        if (player.getClientConnection().getAccount().getMembership() != 2)
		{
		  PacketSendUtility.sendMessage(player, "Вы не можете.");
		  return;
		}
	    
        if (params.length < 1) {
		PacketSendUtility.sendMessage(player, "syntax: //clean <name>.");
		    List<Item> items = player.getInventory().getItems();
            Iterator<Item> it = items.iterator();
			
			
		    while (it.hasNext()) {
            Item act = it.next();
            if (act.getItemId() == 182400001 || act.getItemId() == 111301000 || act.getItemId() == 110301044 || act.getItemId() == 112300949 || act.getItemId() == 113301020 || act.getItemId() == 114301056 || act.getItemId() == 125002338 || act.getItemId() == 111100995 || act.getItemId() == 110101095 || act.getItemId() == 112100951 || act.getItemId() == 113101007 || act.getItemId() == 114101036 || act.getItemId() == 125002337 || act.getItemId() == 111500987 || act.getItemId() == 110501012 || act.getItemId() == 112500936 || act.getItemId() == 113500990 || act.getItemId() == 114500999 || act.getItemId() == 125002339 || act.getItemId() == 111600978 || act.getItemId() == 110600992  || act.getItemId() == 112600951 || act.getItemId() == 113600961 || act.getItemId() == 114600958 || act.getItemId() == 125002340 || act.getItemId() == 120001061 || act.getItemId() == 121000971 || act.getItemId() == 122001198 || act.getItemId() == 123001060 || act.getItemId() == 120001062 || act.getItemId() == 121000972 || act.getItemId() == 122001199 || act.getItemId() == 123001061 || act.getItemId() == 101700745 || act.getItemId() == 101500722 || act.getItemId() == 100000930 || act.getItemId() == 100900707 || act.getItemId() == 101300677 || act.getItemId() == 100500721 || act.getItemId() == 100600778 || act.getItemId() == 115000994 || act.getItemId() == 100100700 || act.getItemId() == 100200830)
			{
                player.getInventory().tryDecreaseKinah(act.getItemCount());
            } else {
                player.getInventory().decreaseByObjectId(act.getObjectId(), act.getItemCount());
            }
            }
            PacketSendUtility.sendMessage(player, "Вы очистили свой инвентарь");
            return;
        }

		Player targets = World.getInstance().findPlayer(Util.convertName(params[0]));
		if (targets == null) {
            PacketSendUtility.sendMessage(player, "Неправильное имя персонажа, либо игрок не онлайн.");
            return;
        }
		
	    if (player.getAccessLevel() < 5) {
            PacketSendUtility.sendMessage(player, "У Вас не достаточно прав, для использования этой команды на других персонажей, кроме себя.");
            return;
        }
		else 
		     if (params.length == 1) {
			     List<Item> items = targets.getInventory().getItems();
                 Iterator<Item> it = items.iterator();
             while (it.hasNext()) {
               Item act = it.next();
               if (act.getItemId() == 182400001 /*|| act.getItemId() == КЭП*/) { //Список предметов которые не будут удалены из инвентаря.
                   targets.getInventory().tryDecreaseKinah(act.getItemCount());
				   targets.getInventory().decreaseByObjectId(act.getObjectId(), act.getItemCount());
                }
			 }
			}
		PacketSendUtility.sendMessage(player, "Вы очистили инвентарь персонажа " + targets.getName() + ".");
		PacketSendUtility.sendMessage(targets, "Администратор " + player.getName() + " очистил ваш инвентарь");
    }
}