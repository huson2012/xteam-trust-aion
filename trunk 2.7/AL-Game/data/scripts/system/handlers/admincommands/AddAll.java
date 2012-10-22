package admincommands;

import com.light.gameserver.dataholders.DataManager;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.AdminService;
import com.light.gameserver.services.item.ItemService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.global.additions.MessagerAddition;


/**
 * @author Phantom, ATracer
 */

public class AddAll extends ChatCommand {

	public AddAll() {
		super("addall");
	}

    @Override
	public void execute(Player admin, String... params) {
	
	    if (params.length == 0 || params.length > 3)
        {
            PacketSendUtility.sendMessage(admin, "Syntax: .add [itemID] [кол-во]");
            return;
        }

        int itemId = 0;
        long itemCount = 1;
        
        try
        {
            itemId = Integer.parseInt(params[0]);

            if ( params.length == 2 )
            {
                itemCount = Long.parseLong(params[1]);
            }
        }
        catch (NumberFormatException e)
        {

            try
            {
                itemId = Integer.parseInt(params[1]);

                if ( params.length == 3 )
                {
                    itemCount = Long.parseLong(params[2]);
                }
            }
            catch (NumberFormatException ex)
            {
                PacketSendUtility.sendMessage(admin, "Параметр должен быть целым числом");
                return;
            }
            catch (Exception ex2)
            {
                PacketSendUtility.sendMessage(admin, "Не вышло! Ы");
                return;
            }
        }
        
 for (Player ppa : World.getInstance().getAllPlayers()) {
        long count = ItemService.addItem(ppa, itemId, itemCount);

        if (count == 0)
        {
            PacketSendUtility.sendMessage(admin, ppa.getName()+" получил предмет [item:"+itemId+"] в количестве "+itemCount+" шт.");
        }
        else
        {
			PacketSendUtility.sendMessage(admin, ppa.getName()+" получил предмет [item:"+itemId+"] в количестве "+itemCount+" шт.");
            PacketSendUtility.sendMessage(ppa, "Получено от администратора "+admin.getName()+ " [item:"+itemId+"] "+ itemCount+" шт.");
        }
    }
	MessagerAddition.announceAll("Щедрый "+ admin.getName() +" выдал всем что то в количестве "+itemCount+" шт", 0);
    }
}