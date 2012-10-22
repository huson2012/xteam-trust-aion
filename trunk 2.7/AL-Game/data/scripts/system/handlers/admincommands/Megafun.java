/*package admincommands;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.TYPE;
import com.aionemu.gameserver.services.teleport.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.ChatCommand;


/**
*@author Cookiezi
 */

/*public class Megafun extends ChatCommand
{
	public Megafun()
	{
		super("gofun");
	}
    
        @Override
	public void execute(Player player, String... params)
	{

		if (params == null || params.length < 1)
		{
			PacketSendUtility.sendMessage(player, "Синтаксис комманды //gofun новичок|pvp1|pvp2|fun|obmens|obmenz|home|arena");
			return;
		}

		if (params[0].toLowerCase().equals("pvp1"))
		{
		if (player.getWorldId() == 600010000)
		{
		PacketSendUtility.sendMessage(player, "Вы и так находитесь в [Silentera Canyon: FORT]");
		return;
		}
		if(player.getCommonData().getRace() == Race.ASMODIANS)
		{
		TeleportService.teleportTo(player, 600010000, player.getInstanceId(), 1015, 944, 307, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() +1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() +1);
		player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendBrightYellowMessageOnCenter(player, "Вы телепортируетесь в PvP локацию: Fort, Удачного боя, нагибатор!");
		}
		if(player.getCommonData().getRace() == Race.ELYOS)
		{
		TeleportService.teleportTo(player, 600010000, player.getInstanceId(), 977, 576, 308, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() +1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() +1);
	        player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в PvP локацию: Fort, Удачного боя, нагибатор!");
	    }
		}
		
		if (params[0].toLowerCase().equals("fun"))
		{
		if (player.getWorldId() == 300100000)
		{
		PacketSendUtility.sendMessage(player, "Вы и так находитесь в [STEEL RAKE]");
		return;
		}
		if(player.getCommonData().getRace() == Race.ASMODIANS)
		{
		TeleportService.teleportTo(player, 300100000, player.getInstanceId(), 577, 508, 1023, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() +1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() +1);
		player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendBrightYellowMessageOnCenter(player, "Вы телепортируетесь в FUN-PVP локацию, Удачного боя, нагибатор!");
		}
		if(player.getCommonData().getRace() == Race.ELYOS)
		{
		TeleportService.teleportTo(player, 300100000, player.getInstanceId(), 475, 508, 1032, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() +1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() +1);
	        player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в FUN-PVP локацию, Удачного боя, нагибатор!");
	    }
		}
		
		if (params[0].toLowerCase().equals("home"))
		{
		if (player.getWorldId() == 120010000)
		{
		PacketSendUtility.sendMessage(player, "Вы и так находитесь в Столице своей рассы");
		return;
		}
		if(player.getCommonData().getRace() == Race.ASMODIANS)
		{
		{
		TeleportService.teleportTo(player, 120010000, player.getInstanceId(), 1275, 1249, 216, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() +1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() +1);
		player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь домой");
		}
		}
		if (player.getWorldId() == 110010000)
		{
		PacketSendUtility.sendMessage(player, "Вы и так находитесь в Столице своей рассы");
		return;
		}
		if(player.getCommonData().getRace() == Race.ELYOS)
		{
		TeleportService.teleportTo(player, 110010000, player.getInstanceId(), 1512, 1511, 566, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() +1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() +1);
	        player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь домой");
	        }
		}
		
		if (params[0].toLowerCase().equals("obmenz"))
		{
		if (player.getWorldId() == 220070000)
		{
		PacketSendUtility.sendMessage(player, "Вы и так находитесь в Точке обмена: Золото -> Платина");
		return;
		}
		if(player.getCommonData().getRace() == Race.ASMODIANS)
		{
		TeleportService.teleportTo(player, 220070000, player.getInstanceId(), 1821, 2913, 548, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() +1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() +1);
		player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в точку обмена: Золото -> Платина");
		}
		if (player.getWorldId() == 210050000)
		{
		PacketSendUtility.sendMessage(player, "Вы и так находитесь в Точке обмена: Золото -> Платина");
		return;
		}
		if(player.getCommonData().getRace() == Race.ELYOS)
		{
		TeleportService.teleportTo(player, 210050000, player.getInstanceId(), 1350, 329, 588, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() +1);
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() +1);
	        player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в точку обмена: Золото -> Платина");
	    }
		}
		
		if (params[0].toLowerCase().equals("obmens"))
		{
		if (player.getWorldId() == 400010000)
		{
		PacketSendUtility.sendMessage(player, "Вы и так находитесь в Точке обмена: Серебро -> Золото");
		return;
		}
		if(player.getCommonData().getRace() == Race.ASMODIANS)
		{
		TeleportService.teleportTo(player, 400010000, player.getInstanceId(), 845, 2739, 1567, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() +1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() +1);
		player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в точку обмена: Серебро -> Золото");
		}
		if(player.getCommonData().getRace() == Race.ELYOS)
		{
		TeleportService.teleportTo(player, 400010000, player.getInstanceId(), 3032, 954, 1462, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() +1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() +1);
	        player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в точку обмена: Серебро -> Золото");
	    }
		}
		
		if (params[0].toLowerCase().equals("arena"))
		{
		if (player.getWorldId() == 120080000)
		{
		PacketSendUtility.sendMessage(player, "Вы и так находитесь в Главном зале священный арены Маркутана.");
		return;
		}
		if(player.getCommonData().getRace() == Race.ASMODIANS)
		{
		TeleportService.teleportTo(player, 120080000, player.getInstanceId(), 548, 225, 93, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() +1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() +1);
		player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в Главный зал священный арены Маркутана.");
		}
		if (player.getWorldId() == 110070000)
		{
		PacketSendUtility.sendMessage(player, "Вы и так находитесь в Главном зале священный арены Кайсинель.");
		return;
		}
		if(player.getCommonData().getRace() == Race.ELYOS)
		{
		TeleportService.teleportTo(player, 110070000, player.getInstanceId(), 478, 250, 126, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp() +1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp() +1);
	        player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в Главный зал священный арены Кайсинель.");
	    }
		}
		
		if (params[0].toLowerCase().equals("pvp2"))
		{
		if (player.getWorldId() == 600010000)
		{
		PacketSendUtility.sendMessage(player, "Вы и так находитесь в PvP локации: Верхний форт");
		return;
		}
		if(player.getCommonData().getRace() == Race.ASMODIANS)
		{
		TeleportService.teleportTo(player, 600010000, player.getInstanceId(), 1217, 842, 331, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp()+1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp()+1);
		player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в PvP локацию: Верхний форт, Удачных боёв,нагибатор!");
		}
		if(player.getCommonData().getRace() == Race.ELYOS)
		{
		TeleportService.teleportTo(player, 600010000, player.getInstanceId(), 1241, 685, 327, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp()+1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp()+1);
	        player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в PvP локацию: Верхний форт, Удачных боёв,нагибатор!");
	    }
		}
		
		if (params[0].toLowerCase().equals("pvp2"))
		{
		if (player.getWorldId() == 600010000)
		{
		PacketSendUtility.sendMessage(player, "Вы и так находитесь в PvP локации: Верхний форт");
		return;
		}
		if(player.getCommonData().getRace() == Race.ASMODIANS)
		{
		TeleportService.teleportTo(player, 600010000, player.getInstanceId(), 1217, 842, 331, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp()+1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp()+1);
		player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в PvP локацию: Верхний форт, Удачных боёв,нагибатор!");
		}
		if(player.getCommonData().getRace() == Race.ELYOS)
		{
		TeleportService.teleportTo(player, 600010000, player.getInstanceId(), 1241, 685, 327, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp()+1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp()+1);
	        player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в PvP локацию: Верхний форт, Удачных боёв,нагибатор!");
	    }
		}
		
		if (params[0].toLowerCase().equals("новичок"))
		{
		if (player.getWorldId() == 220070000)
		{
		PacketSendUtility.sendMessage(player, "Вы и так находитесь в PvP локации для новичка!");
		return;
		}
		if(player.getCommonData().getRace() == Race.ASMODIANS)
		{
		TeleportService.teleportTo(player, 220070000, player.getInstanceId(), 1294, 1561, 304, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp()+1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp()+1);
		player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в PvP локаци для новичка!");
		}
		if(player.getCommonData().getRace() == Race.ELYOS)
		{
		TeleportService.teleportTo(player, 220070000, player.getInstanceId(), 1393, 1278, 308, 3750, true);
		player.getLifeStats().increaseHp(TYPE.HP, player.getLifeStats().getMaxHp()+1); 
		player.getLifeStats().increaseMp(TYPE.MP, player.getLifeStats().getMaxMp()+1);
	        player.getCommonData().setDp(player.getGameStats().getMaxDp().getCurrent());
		PacketSendUtility.sendMessage(player, "Вы телепортируетесь в PvP локацию для новичка!");
	    }
		}
		
		/**
		* Вы можете дописать сюда по анологии дополнительную/ые локации для PVP !
		*/
	/*	}
		}*/