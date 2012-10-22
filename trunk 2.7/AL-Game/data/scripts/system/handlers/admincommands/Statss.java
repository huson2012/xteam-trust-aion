package admincommands;

import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.stats.container.CreatureGameStats;
import com.light.gameserver.model.stats.container.StatEnum;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;


public class Statss extends ChatCommand
{
	public Statss()
	{
		super("statss");
	}

	@Override
	public void execute (Player admin, String... params)
	{
		if (admin.getAccessLevel() < 5)
		{
			PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");
			return;
		}

		if (admin.getTarget() == null)
		{
			PacketSendUtility.sendMessage(admin, "You have to select a target");
			return;
		}

		VisibleObject target = admin.getTarget();

		if (!(target instanceof Creature))
		{
			PacketSendUtility.sendMessage(admin, "Your target is not a Creature");
			return;
		}

		Creature cTarget = (Creature)target;

		PacketSendUtility.sendMessage(admin, ">>> Stats information about "+cTarget.getClass().getSimpleName()+" \""+cTarget.getName()+"\"");
		if (cTarget.getGameStats() != null)
		{
			CreatureGameStats<?> cgs = cTarget.getGameStats();
			for (int i=0; i<StatEnum.values().length; i++)
			{
				/*if (cgs.getStat(StatEnum.values()[i], i+) != 0)
				{
                                    PacketSendUtility.sendMessage(admin, "asd");
                                }*/
					PacketSendUtility.sendMessage(admin, StatEnum.values()[i]+": "+cgs.getStatsByStatEnum(StatEnum.values()[i])+" ("+cgs.getStat(StatEnum.values()[i], null) +")");
				
			}
		}
		PacketSendUtility.sendMessage(admin, ">>> End of stats information");
	}
}
