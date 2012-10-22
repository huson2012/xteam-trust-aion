package quest.fountain;

import com.light.gameserver.dataholders.DataManager;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.templates.QuestTemplate;

import com.light.gameserver.model.templates.bonus.InventoryBonusType;
import com.light.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.light.gameserver.questEngine.model.QuestDialog;
import com.light.gameserver.questEngine.model.QuestEnv;
import com.light.gameserver.questEngine.model.QuestState;
import com.light.gameserver.questEngine.model.QuestStatus;
import com.light.gameserver.services.item.ItemService;
import com.light.gameserver.questEngine.handlers.HandlerResult;
import com.light.gameserver.questEngine.handlers.QuestHandler;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.services.QuestService;


public class _12061Platinum_Time_Lucky extends QuestHandler{

private final static int questId = 12061;
	
	public _12061Platinum_Time_Lucky() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(730553).addOnQuestStart(questId);
		qe.registerQuestNpc(730553).addOnTalkEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		int targetId = env.getTargetId();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		QuestDialog dialog = env.getDialog();
		@SuppressWarnings("unused")
		QuestTemplate template = DataManager.QUEST_DATA.getQuestById(env.getQuestId()); {
			if (env.getTargetId() == 730553) { // 3.0 Fountain
				switch (dialog) {
					case START_DIALOG: {
						if (player.getCommonData().getLevel() >= 50)
							return sendQuestDialog(env, 1011);
						else
							return true;
					}
					case STEP_TO_1: {
						long silverMedals = player.getInventory().getItemCountByItemId(186000030);
						if (silverMedals > 0) {
							if (qs == null) {
								qs = new QuestState(questId, QuestStatus.REWARD, 0, 0, null, targetId, null);
								player.getQuestStateList().addQuest(questId, qs);
							}	
							else
								qs.setStatus(QuestStatus.REWARD);
							return sendQuestDialog(env, 5);
							
						}
						else {
							return true;
						
					}
				}
			}
		}
	}
	
		if (qs == null)
			return false;
		if (qs.getStatus() == QuestStatus.REWARD && env.getTargetId() == 730553) {//LDF4b_CoinFountain_L_1.
			if (env.getDialogId() == 18) {
				if (player.getInventory().getItemCountByItemId(186000030) > 0 && QuestService.finishQuest(env, 0)) {
					sendQuestDialog(env, 1008);
					return true;
				}
			}
			return sendQuestDialog(env, 5);
		}
		return false;
}
	}