package admincommands;

import com.light.gameserver.model.Race;
import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.light.gameserver.model.skill.PlayerSkillEntry;
import com.light.gameserver.model.skill.PlayerSkillList;
import com.light.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.light.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.light.gameserver.services.SkillLearnService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author Alex
 * 
*/
public class ChangeRace extends ChatCommand {

    public ChangeRace() {
        super("race");
    }

    @Override
    public void execute(Player admin, String... params) {

        final int coll = 200;
        final int money = admin.getDonatmoney();
        final String message = "За смену расы с вашего счета будет списано " + coll + " Coll. Текущий баланс : " + admin.getDonatmoney() + ". Вы действительно желаете сменить расу?";
        final String messages = "Желаете сменить расу?";
        if (money < coll && !admin.isGM()) {
            PacketSendUtility.sendWhiteMessageOnCenter(admin, "У вас не достаточно coll, для смены расы\nВаш текущий баланс : " + money + " Coll\n- Необходимо " + coll + "");
            return;
        }
        RequestResponseHandler responseHandler = new RequestResponseHandler(admin) {

            public void acceptRequest(Creature requester, Player p) {
                PlayerSkillList playerSkillList = null;
                playerSkillList = p.getSkillList();

                if (p.getAccessLevel() == 0) {
                    if (p.getDonatmoney() >= coll) {
					if (p.getCommonData().getRace() == Race.ELYOS) {
                            p.getCommonData().setRace(Race.ASMODIANS);
                        } else {
                            p.getCommonData().setRace(Race.ELYOS);
                        }
                        p.setDonatmoney(money - coll);
                        PacketSendUtility.sendWhiteMessageOnCenter(p, "За смену расы с вас было списано " + coll + " Coll");
						
                    } else if (p.getDonatmoney() < coll) {
                        PacketSendUtility.sendWhiteMessageOnCenter(p, "У вас не достаточно coll, для смены расы\nВаш текущий баланс : " + money + " Coll\n- необходимо " + coll + "");
						return;
                    }
                } else {
				if (p.getCommonData().getRace() == Race.ELYOS) {
                            p.getCommonData().setRace(Race.ASMODIANS);
                        } else {
                            p.getCommonData().setRace(Race.ELYOS);
                        }
				}
                for (PlayerSkillEntry skillEntry : playerSkillList.getAllSkills()) {
                    
                        SkillLearnService.removeSkill(p, skillEntry.getSkillId());
                    
                }
                p.clearKnownlist();
                PacketSendUtility.sendPacket(p, new SM_PLAYER_INFO(p, false));
                p.updateKnownlist();
                SkillLearnService.addMissingSkills(p);

                if (p.getAccessLevel() == 0) {
                    PacketSendUtility.sendWhiteMessageOnCenter(p, "За смену расы с вас было списано " + coll + " Coll");
                }
				return;
            }

            public void denyRequest(Creature requester, Player p) {
                PacketSendUtility.sendWhiteMessageOnCenter(p, "Вы отказались от смены расы");
                return;
            }
        };
        boolean requested = admin.getResponseRequester().putRequest(902247, responseHandler);

        if (requested) {
            if (admin.getAccessLevel() == 0) {
                PacketSendUtility.sendPacket(admin, new SM_QUESTION_WINDOW(902247, 0, message));
            } else {
                PacketSendUtility.sendPacket(admin, new SM_QUESTION_WINDOW(902247, 0, messages));
            }
        }
    }
}
