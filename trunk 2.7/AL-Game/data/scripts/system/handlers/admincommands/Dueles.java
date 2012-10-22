package admincommands;

import com.light.gameserver.model.DuelResult;
import com.light.gameserver.model.Race;
import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.light.gameserver.model.templates.zone.ZoneType;
import com.light.gameserver.network.aion.serverpackets.SM_DUEL;
import com.light.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.light.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.light.gameserver.services.DuelService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.ThreadPoolManager;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alex
 */
public class Dueles extends ChatCommand 
{
    private static Logger log = LoggerFactory.getLogger(Dueles.class);
	private FastMap<Integer, Integer> duels;
	
	public Dueles()
	{
		super("duel");
	}
	
	@Override
	public void execute(Player admin, String... params) 
	{
	
        if (admin.getClientConnection().getAccount().getMembership() != 3)
		{
		  PacketSendUtility.sendMessage(admin, "Вы не можете.");
		  return;
		}
		
	    if (params.length < 1) 
		{
			sendInfo(admin);
			return;
		}
		
	    Player target = World.getInstance().findPlayer(Util.convertName(params[0]));
	 
	    if (target == null) 
	    {
		  PacketSendUtility.sendMessage(admin, "Указанный игрок не онлайн!");
		  return;
	    }
	

	    if (target.getAccessLevel() > 0 && admin.getAccessLevel() == 0)
	    {
	        PacketSendUtility.sendMessage(admin, "Нельзя предлагать такое администрации...");
	        return;
	    }
	
		if (admin.isInsideZoneType(ZoneType.PVP) || target.isInsideZoneType(ZoneType.PVP))
		{
			PacketSendUtility.sendPacket(admin, SM_SYSTEM_MESSAGE.STR_DUEL_PARTNER_INVALID(target.getName()));
			return;
		}
		if (target.isEnemy(target) || DuelService.getInstance().isDueling(admin.getObjectId()) || DuelService.getInstance().isDueling(target.getObjectId())) 
		{
			PacketSendUtility.sendPacket(admin, SM_SYSTEM_MESSAGE.STR_DUEL_HE_REJECT_DUEL(target.getName()));
			return;
		}

		RequestResponseHandler rrh = new RequestResponseHandler(admin)
		{

			@Override
			public void denyRequest(Creature admin, Player target)
			{
				rejectDuelRequest((Player) admin, target);
			}

			@Override
			public void acceptRequest(Creature admin, Player target)
			{
				startDuel((Player) admin, target);
			}
		};
		String message = "Персонаж "+ admin.getName() +" хочет вас нагнуть. Вы хотите дать этому храбрецу такую возможность?:)Если согласны вас телепортнет в отдельную локацию вместе с ним.";
		String mess = ""+ admin.getName() +" хочет вас нагнуть...";
		target.getResponseRequester().putRequest(902247, rrh);
		PacketSendUtility.sendPacket(target, new SM_QUESTION_WINDOW(902247, 0, message));
		PacketSendUtility.sendMessage(target, mess);
		PacketSendUtility.sendMessage(admin, "Вы предлагаете PvP персонажу "+ target.getName() +" ");
	}
	
	private void rejectDuelRequest(Player admin, Player target) 
	{
	   String message = ""+ target.getName() +" отклонил ваше добродушное предложение...Ваще печаль.Если хошь мож снова попробовать хД";
	   String mess = "Вы послали этого наглеца по имени "+ admin.getName() +"";
	   
		log.debug("[DuelCommand] Player " + target.getName() + " rejected duel request from " + admin.getName());
		PacketSendUtility.sendMessage(admin, message);
		PacketSendUtility.sendMessage(target, mess);
	}
	
	private void startDuel(final Player admin, final Player target) 
	{
	    if (admin.getWorldId() == 120020000 && target.getWorldId() == 120020000)
		{
	         PacketSendUtility.sendPacket(admin, SM_DUEL.SM_DUEL_STARTED(target.getObjectId()));
		 PacketSendUtility.sendPacket(target, SM_DUEL.SM_DUEL_STARTED(admin.getObjectId()));
		 DuelService.getInstance().createDuel(admin.getObjectId(), target.getObjectId());
		}
		else
		{
		 GoTo.goTo(admin, 120020000, 1442, 1133, 302);
		 GoTo.goTo(target, 120020000, 1442, 1133, 302);
                  if (admin.getWorldId() == 120020000 && target.getWorldId() == 120020000)
                  {
		    PacketSendUtility.sendPacket(admin, SM_DUEL.SM_DUEL_STARTED(target.getObjectId()));
		    PacketSendUtility.sendPacket(target, SM_DUEL.SM_DUEL_STARTED(admin.getObjectId()));
		    DuelService.getInstance().createDuel(admin.getObjectId(), target.getObjectId());
                  }
		}

		// Schedule for draw
		ThreadPoolManager.getInstance().schedule(new Runnable() 
		{

			@Override
			public void run() 
			{
				if (DuelService.getInstance().isDueling(admin.getObjectId(), target.getObjectId())) 
				{
					PacketSendUtility.sendPacket(admin, SM_DUEL.SM_DUEL_RESULT(DuelResult.DUEL_DRAW, admin.getName()));
					PacketSendUtility.sendPacket(target, SM_DUEL.SM_DUEL_RESULT(DuelResult.DUEL_DRAW, target.getName()));
					removeDuel(admin.getObjectId(), target.getObjectId());
					
					if(target.getCommonData().getRace() == Race.ELYOS)
                    {
                     GoTo.goTo(target, 110010000, 1476, 1595, 572);
                    }
                    else if(target.getCommonData().getRace() == Race.ASMODIANS)
                    {
                     GoTo.goTo(target, 120010000, 1456, 1417, 177);
                    }
					
					if(admin.getCommonData().getRace() == Race.ELYOS)
                    {
                     GoTo.goTo(admin, 110010000, 1476, 1595, 572);
                    }
                    else if(admin.getCommonData().getRace() == Race.ASMODIANS)
                    {
                     GoTo.goTo(admin, 120010000, 1456, 1417, 177);
                    }
				}
			}
		}, 15 * 60 * 1000); // 15 минут битвы после чего дуэль прекратится
	}
	
	private void removeDuel(int adminObjId, int targetObjId) {
		duels.remove(adminObjId);
		duels.remove(targetObjId);
	}

	@Override
	public void onFail(Player admin, String message) 
	{
		sendInfo(admin);
		return;
	}
	
	private void sendInfo(Player admin) 
	{
		PacketSendUtility.sendMessage(admin, "syntax //duel <имя персонажа>");
	}
}