package admincommands;

import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author Steve [JS]Folio
 * @modifer Alex
 */

public class SetStat extends ChatCommand 
{

    public SetStat()
    {
        super("setstat");
    }

    @Override
    public void execute (final Player admin, String... params) {
        Creature target = (Creature) admin.getTarget();
        if (params == null || params.length < 1)
		{
            PacketSendUtility.sendMessage(admin, "Syntax: //setstat mp | hp <int value>");
			return;
        }
        
        if (target == null) {
            PacketSendUtility.sendMessage(admin, "Неопределен таргет персонажа");
        }
        if (params[0].equals("mp")) {
          try {
            target.getLifeStats().setCurrentMp(Integer.parseInt(params[1]));
          } catch (NumberFormatException e) {
            PacketSendUtility.sendMessage(admin, "//setstat mp <int value>");
          }
        }
        else if (params[0].equals("hp")) {
        try {
            target.getLifeStats().setCurrentHp(Integer.parseInt(params[1]));
        } catch (NumberFormatException e) {
            PacketSendUtility.sendMessage(admin, "//setstat hp <int value>");
        }
        }
    }
}
