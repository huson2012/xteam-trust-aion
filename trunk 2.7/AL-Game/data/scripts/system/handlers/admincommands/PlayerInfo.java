package admincommands;

import light.wedding.WeddingService;

import com.google.common.base.Predicate;
import com.light.gameserver.configs.administration.AdminConfig;
import com.light.gameserver.model.account.PlayerAccountData;
import com.light.gameserver.model.gameobjects.Item;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.skill.PlayerSkillEntry;
import com.light.gameserver.model.team.legion.Legion;
import com.light.gameserver.model.team.legion.LegionMemberEx;
import com.light.gameserver.model.team2.group.PlayerGroup;
import com.light.gameserver.services.LegionService;
import com.light.gameserver.utils.ChatUtil;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.Util;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lyahim
 * @modified Alex
 */
public class PlayerInfo extends ChatCommand {

	public PlayerInfo() {
		super("playerinfo");
	}

	@Override
	public void execute(Player admin, String... params) {
		if (params == null || params.length < 1 && !admin.isGM()) {
			PacketSendUtility.sendMessage(admin, "//playerinfo <имя персонажа> < item | skill > ");
			return;
		} else if(params == null || params.length < 1 && admin.isGM()) {
		    PacketSendUtility.sendMessage(admin, "syntax //playerinfo <имя персонажа> <loc | item | group | skill | legion | ap | chars>");
			return;
			}//isGM() - берется из админ конфига, уровень гм
		
		Player target = World.getInstance().findPlayer(Util.convertName(params[0]));

		if (target == null) {
			PacketSendUtility.sendMessage(admin, "Выбранный игрок не онлайн!");
			return;
		}

		if (target.getClientConnection().getAccount().getMembership() == 2 && admin.getAccessLevel() == 0 && admin.getClientConnection().getAccount().getMembership() != 2  && !admin.isGM()) {
		    PacketSendUtility.sendMessage(admin, "Вы не можете просматривать информацию данного персонажа, так как его уровень доступа выше вашего!");
		    return;
		}
		else {
                    String access = "";
                    String member = "";
                    String marry = "";

                    switch(target.getAccessLevel()) {
                        case 0:
				access = "(обычный игрок)";
				break;
                        case 1:
				access = "("+AdminConfig.CUSTOMTAG_ACCESS1+") - помошник админа";
				break;
		        case 2:
				access = "("+AdminConfig.CUSTOMTAG_ACCESS2+") - евент гм";
				break;
		        case 3:
				access = "("+AdminConfig.CUSTOMTAG_ACCESS3+") - главный гм";
				break;
			case 4:
				access = "("+AdminConfig.CUSTOMTAG_ACCESS4+") - главный админ";
				break;
			case 5:
				access = "("+AdminConfig.CUSTOMTAG_ACCESS5+") - разработчик";
				break;
                    }
                    switch(target.getClientConnection().getAccount().getMembership())
                        
                    {
                        case 0:	member = "обычный";break;
                        case 1:	member = "Премиум";break;
		        case 2:	member = "VIP";break;
		        case 3:	member = "Admin";break;
                    }
                    
                    if (WeddingService.isMarried(target)){
                        marry = "Обручен(а) с персонажем "+WeddingService.getWedding(target).getPartnerName();
                    }
                    else {
                        marry = "Не обручен(а)";
                    }
                    
                    if(admin.isGM()) {
                        PacketSendUtility.sendMessage(admin,
			    "\n[Инфо персонажа "+ target.getName() +"]\n"
                            + "-Общая информация:\n" 
                            + "Уровень: "+ target.getLevel() +"\n"
                            + "Имя акаунта: "+ target.getClientConnection().getAccount().getName() +"\n"
                            + "Ip: "+ target.getClientConnection().getIP() +"\n" 
                            + "Раса: "+ target.getRace().getRusname()+ "\n"
                            + "Клас: "+ target.getPlayerClass().getRusname() + "\n"
                            + "Ранг бездны: "+target.getAbyssRank().getRank().getRusname()+"\n"
                            + "АП: "+target.getAbyssRank().getAp()+"\n"
                            + "Уровень доступа: "+access+"\n"
                            + "Тип акаунта: "+member+"\n"
                            + "Дата создания персонажа: "+target.getCommonData().getCreationDate() +"\n"
                            + "Последний заход был "+target.getCommonData().getLastOnline()+"\n"
                            + "Игрок в онлайне уже "+target.getOnlineTime() +" секунд\n"
                            //+ "Скорость движения перса : "+target.speedHackValue +"\n"
                            + "Статус see (видимость инвиза) : "+target.getSeeState() +"\n"
                            + "Пол перса (муж женский): "+target.getCommonData().getGender() +"\n"
                            + "Donatemoney (кредитов) : "+target.getDonatmoney()+"\n"
                            + ""+marry
                                );
                    }
                    else
                    {
                        PacketSendUtility.sendMessage(admin,
			    "\n[Инфо персонажа "+ target.getName() + "]\n"
                            + "-Общая информация:\n"
                            + "Раса: "+ target.getRace().getRusname() + "\n"
                            + "Клас: "+ target.getPlayerClass().getRusname() + "\n"
                            + "Ранг бездны:"+target.getAbyssRank().getRank().getRusname()+"\n"
                            + "Уровень доступа: "+access+"\n"
                            + "Тип акаунта: "+member+"\n"
                            + ""+marry
                                );
                    }		

		if (params.length < 2)
			return;

		if (params[1].equals("item")) {
			StringBuilder strbld = new StringBuilder("-Предметы в инвентаре:\n");

			List<Item> items = target.getInventory().getItemsWithKinah();
			Iterator<Item> it = items.iterator();

			if (items.isEmpty())
				strbld.append("нету\n");
			else
				while (it.hasNext()) {

					Item act = it.next();
					strbld.append("    ").append(act.getItemCount()).append("(s) of ").append(ChatUtil.item(act.getItemTemplate().getTemplateId())).append("\n");
				}
			items.clear();
			items = target.getEquipment().getEquippedItems();
			it = items.iterator();
			strbld.append("-Надетые предметы:\n");
			if (items.isEmpty())
				strbld.append("нету\n");
			else
				while (it.hasNext()) {
					Item act = it.next();
					strbld.append("    ").append(act.getItemCount()).append("(s) of ").append(ChatUtil.item(act.getItemTemplate().getTemplateId())).append("\n");
				}

			items.clear();
			items = target.getWarehouse().getItemsWithKinah();
			it = items.iterator();
			strbld.append("-Предметы на складе:\n");
			if (items.isEmpty())
				strbld.append("нету\n");
			else
				while (it.hasNext())
                                {
					Item act = it.next();
					strbld.append("    ").append(act.getItemCount()).append("(s) of " + "[item:").append(act.getItemTemplate().getTemplateId()).append("]" + "\n");
				}
			showAllLines(admin, strbld.toString());
		}
		else if (params[1].equals("group") && admin.getAccessLevel() != 0) {
			final StringBuilder strbld = new StringBuilder("-Инфо группы:\n  Лидер пати: ");

				PlayerGroup group = target.getPlayerGroup2();
				if (group == null)
					PacketSendUtility.sendMessage(admin, "-Инфо группы: нет группы");
				else {
					strbld.append(group.getLeader().getName()).append("\n  Учасники группы:\n");
					group.applyOnMembers(new Predicate<Player>() {

						@Override
						public boolean apply(Player player) {
							strbld.append("    ").append(player.getName()).append("\n");
							return true;
						}

					});
					PacketSendUtility.sendMessage(admin, strbld.toString());
				}
			
		}
		else if (params[1].equals("skill")) {
			StringBuilder strbld = new StringBuilder("-Список умений:\n");

			PlayerSkillEntry sle[] = target.getSkillList().getAllSkills();

			for (int i = 0; i < sle.length; i++)
				strbld.append("    Уровень ").append(sle[i].getSkillLevel()).append(" of ").append(sle[i].getSkillName()).append("\n");
			showAllLines(admin, strbld.toString());
		}
		else if (params[1].equals("loc")) {
			String chatLink = ChatUtil.position(target.getName(), target.getPosition());
			PacketSendUtility.sendMessage(
				admin,
				"- " + chatLink + "'s Локация:\n  Mapid: " + target.getWorldId() + "\n  X: " + target.getX() + " Y: "
					+ target.getY() + "Z: " + target.getZ() + "heading: " + target.getHeading());
		}
		else if (params[1].equals("legion")  && admin.getAccessLevel() != 0) 
{
			StringBuilder strbld = new StringBuilder();

			Legion legion = target.getLegion();
			if (legion == null)
				PacketSendUtility.sendMessage(admin, "-Инфо легиона: нет легиона");
			else {
				ArrayList<LegionMemberEx> legionmemblist = LegionService.getInstance().loadLegionMemberExList(legion);
				Iterator<LegionMemberEx> it = legionmemblist.iterator();

				strbld.append("-Инфо легиона:\n  Название: ").append(legion.getLegionName()).append(", Уровень: ").append(legion.getLegionLevel()).append("\n  учасники(в online):\n");
				while (it.hasNext()) {
					LegionMemberEx act = it.next();
					strbld.append("    ").append(act.getName()).append("(").append((act.isOnline() == true) ? "online" : "offline").append(")").append(act.getRank().toString()).append("\n");
				}
			}
			showAllLines(admin, strbld.toString());
		}
		else if(params[1].equals("ap") && admin.getAccessLevel() != 0)	{
			PacketSendUtility.sendMessage(admin, "AP инфо персонажа " + target.getName());
			PacketSendUtility.sendMessage(admin, "Всего AP = " + target.getAbyssRank().getAp());
			PacketSendUtility.sendMessage(admin, "Всего убийств = " + target.getAbyssRank().getAllKill());
			PacketSendUtility.sendMessage(admin, "Убийств за неделю = " + target.getAbyssRank().getDailyKill());
			PacketSendUtility.sendMessage(admin, "AP за неделю = " + target.getAbyssRank().getDailyAP());
		}
		else if(params[1].equals("chars") && admin.getAccessLevel() != 0) {
			PacketSendUtility.sendMessage(admin, "На аккаунте персонажа " + target.getName() + " еще (" + target.getClientConnection().getAccount().size() + ") персов:");//тут влом переводить...

			Iterator<PlayerAccountData> data = target.getClientConnection().getAccount().iterator();
			while(data.hasNext()) {
				PlayerAccountData d = data.next();
				if(d != null && d.getPlayerCommonData() != null) {
					PacketSendUtility.sendMessage(admin, d.getPlayerCommonData().getName());
				}
			}
		}
		else {
			PacketSendUtility.sendMessage(admin, "syntax //playerinfo <имя персонажа> < item |  skill > ");
		}
		}
	}
	
	private void showAllLines(Player admin, String str) {
		int index = 0;
		String[] strarray = str.split("\n");

		while (index < strarray.length - 20) {
			StringBuilder strbld = new StringBuilder();
			for (int i = 0; i < 20; i++, index++) {
				strbld.append(strarray[index]);
				if (i < 20 - 1)
					strbld.append("\n");
			}
			PacketSendUtility.sendMessage(admin, strbld.toString());
		}
		int odd = strarray.length - index;
		StringBuilder strbld = new StringBuilder();
		for (int i = 0; i < odd; i++, index++)
			strbld.append(strarray[index]).append("\n");
		PacketSendUtility.sendMessage(admin, strbld.toString());
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "syntax //playerinfo <имя персонажа> <loc | item | group | skill | legion | ap | chars> ");
	}

}
