package admincommands;

import light.Settings;
import light.wedding.WeddingService;

import com.light.gameserver.configs.administration.AdminConfig;

import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.light.gameserver.model.gameobjects.player.TeleportTask;
import com.light.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.light.gameserver.skillengine.SkillEngine;
import com.light.gameserver.skillengine.model.Skill;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.world.WorldMapType;

/*
*
* Author: Dzirt Do'Urden & Alex by AionLight
*
*/

public class Wedding extends ChatCommand {
	
	private static int cost = Settings.WEDDING_COST;

	public Wedding() {
		super("wedding");
	}

	@Override
	public void execute(Player player, String... params) {
	
	    if (params == null || params.length < 1) {
			PacketSendUtility.sendMessage(player, "Вводим .wedding < marry | tp | summon | unmarry>");
			return;
		}
		
		if(params[0].equalsIgnoreCase("marry"))
			marry(player, params);
		
		if(params[0].equalsIgnoreCase("tp"))
			teleport(player);
		
		if(params[0].equalsIgnoreCase("summon"))
			summon(player);
		
		if(params[0].equalsIgnoreCase("unmarry"))
			WeddingService.unmarry(player);
	}
	
	private void summon(Player player) {
		if(!WeddingService.isMarried(player)) {
			PacketSendUtility.sendMessage(player, "You are not married!");
			return;
		}
		
		String name = WeddingService.getWedding(player).getPartnerName();
		Player partner = World.getInstance().findPlayer(name);
		
		if(partner == null) {
			PacketSendUtility.sendMessage(player, "Your partner is not online.");
			return;
		}
		
		if (partner.getWorldId() == AdminConfig.LOC_ID || player.getWorldId() == AdminConfig.LOC_ID || partner.getWorldId() == WorldMapType.SANCTUM.getId() || player.getWorldId() == WorldMapType.SANCTUM.getId() || player.getWorldId() == WorldMapType.PANDAEMONIUM.getId() || partner.getWorldId() == WorldMapType.PANDAEMONIUM.getId()) {
                    PacketSendUtility.sendMessage(player, "Нельзя телепортироваться находясь в этой локации");
			        return;
        }
		
		PacketSendUtility.sendMessage(player, "Waiting for " + name + "'s answer...");
		RequestResponseHandler rh = new RequestResponseHandler(player)
		{

			@Override
			public void denyRequest(Creature player, Player partner)
			{
				PacketSendUtility.sendMessage((Player)player, partner.getName() + " declined your summon.");
			}

			@Override
			public void acceptRequest(Creature player, Player partner)
			{
				teleport(partner, (Player)player);
			}
		};
		
		partner.getResponseRequester().putRequest(902247, rh);
		PacketSendUtility.sendPacket(partner, new SM_QUESTION_WINDOW(902247, 0, player.getName() + " просит вас прийти на помощь. Телепортироваться?"));
	}
	
	private void teleport(Player player) {
		if(!WeddingService.isMarried(player)) {
			PacketSendUtility.sendMessage(player, "You are not married!");
			return;
		}
		
		String name = WeddingService.getWedding(player).getPartnerName();
		Player partner = World.getInstance().findPlayer(name);
		
		if(partner == null) {
			PacketSendUtility.sendMessage(player, "Your partner is not online.");
			return;
		}
		
		if (partner.getWorldId() == AdminConfig.LOC_ID || player.getWorldId() == AdminConfig.LOC_ID || partner.getWorldId() == WorldMapType.SANCTUM.getId() || player.getWorldId() == WorldMapType.SANCTUM.getId() || player.getWorldId() == WorldMapType.PANDAEMONIUM.getId() || partner.getWorldId() == WorldMapType.PANDAEMONIUM.getId()) {
                    PacketSendUtility.sendMessage(player, "Нельзя телепортироваться находясь в этой локации");
			        return;
        }
		
		teleport(player, partner);
	}
	
	private void teleport(Player from, Player to) {
		TeleportService.teleportTo(from, to.getWorldId(), to.getInstanceId(), 
        to.getX(), to.getY(), to.getZ(), to.getHeading(), 3000, true);
	}
	
	private void marry(Player player, String... params) {
		if(params.length < 2) {
			PacketSendUtility.sendMessage(player, "Syntax: .wedding marry nickname");
			return;
		}
		
		final String partnerName = Util.convertName(params[1]);
		if(!WeddingService.canMarry(player, partnerName))
			return;
		
		RequestResponseHandler rh = new RequestResponseHandler(player)
		{

			@Override
			public void denyRequest(Creature player, Player partner){}

			@Override
			public void acceptRequest(Creature player, Player partner)
			{
				startMarryTask((Player)player, partnerName);
			}
		};
		
		player.getResponseRequester().putRequest(902247, rh);
		PacketSendUtility.sendPacket(player, new SM_QUESTION_WINDOW(902247, 0, "Это будет стоить " + cost + " Colls. Продолжить?"));
	}
	
	private void startMarryTask(Player player, String partnerName) {
		Player partner = World.getInstance().findPlayer(partnerName);
		PacketSendUtility.sendMessage(player, "Waiting for " + partnerName + "'s answer...");
		
		RequestResponseHandler rh = new RequestResponseHandler(player)
		{

			@Override
			public void denyRequest(Creature player, Player partner)
			{
				PacketSendUtility.sendMessage((Player)player, partner.getName() + " refused to marry you.");
			}

			@Override
			public void acceptRequest(Creature player, Player partner)
			{
				Player p = (Player)player;
				
				int money = p.getDonatmoney();
				if(money < cost) {
					PacketSendUtility.sendMessage(p, "You have not enough money!");
					PacketSendUtility.sendMessage(partner, p.getName() + " doesn't have enough money for wedding!");
					return;
				}
				
				WeddingService.marry(p, partner);
				p.setDonatmoney(money - cost);
			}
		};
		
		partner.getResponseRequester().putRequest(902247, rh);
		PacketSendUtility.sendPacket(partner, new SM_QUESTION_WINDOW(902247, 0, player.getName() + " предлагает вам обручиться. Вы согласны?"));
	}

}
