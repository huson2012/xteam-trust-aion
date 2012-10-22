package quest.abyssal_splinter;

import com.light.gameserver.model.gameobjects.Item;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.questEngine.handlers.HandlerResult;
import com.light.gameserver.questEngine.handlers.QuestHandler;
import com.light.gameserver.questEngine.model.QuestEnv;
import com.light.gameserver.questEngine.model.QuestState;
import com.light.gameserver.questEngine.model.QuestStatus;
import com.light.gameserver.services.QuestService;

/**
 * @author Rikka
 */
public class _30264ANecklacewithHistory extends QuestHandler {

	private final static int questId = 30264;

	public _30264ANecklacewithHistory() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(203706).addOnTalkEvent(questId);
		qe.registerQuestItem(182209802, questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			return false;
		}
		else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203706) { // Charna
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}

	@Override
	public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (QuestService.startQuest(env)) {
				changeQuestStep(env, 0, 0, true); // reward
				return HandlerResult.fromBoolean(sendQuestDialog(env, 4));
			}
		}
		return HandlerResult.UNKNOWN;
	}
}
