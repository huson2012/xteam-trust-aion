package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.audit.GMService;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import java.util.Collection;

/**
 * admin gmtome command.
 * 
 * @author Alex
 */
public class GmToMe extends ChatCommand 
{

	public GmToMe() 
	{
		super("gmtome");
	}

	@Override
	public void execute(final Player player, String... params) 
	{
            Collection<Player> Gms = GMService.getInstance().getGMs();
            for (Player gm : Gms){
                if (gm != player){
                TeleportService.teleportTo(gm, player.getWorldId(), player.getInstanceId(), player.getX(), player.getY(), player.getZ(), player.getHeading(), 3000, true);
                PacketSendUtility.sendMessage(player, ""+ gm.getName() +" телепортируется к вам");
                PacketSendUtility.sendMessage(gm, "Администратор "+player.getName()+" телепортирует всех гмов к себе");
				}
            }
        }

	@Override
	public void onFail(Player player, String message) 
	{
		PacketSendUtility.sendMessage(player, "syntax //gmtome");
	}
}
