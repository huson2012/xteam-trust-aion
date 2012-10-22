/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 * aion-unique is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-unique is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-unique. If not, see <http://www.gnu.org/licenses/>.
 */
package quest.poeta;

import com.light.gameserver.model.Race;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.light.gameserver.questEngine.handlers.QuestHandler;
import com.light.gameserver.questEngine.model.QuestEnv;
import com.light.gameserver.questEngine.model.QuestState;
import com.light.gameserver.questEngine.model.QuestStatus;
import com.light.gameserver.services.QuestService;
import com.light.gameserver.utils.PacketSendUtility;

/**
 * @author MrPoke
 */
public class _1000Prologue extends QuestHandler {

	private final static int questId = 1000;

	public _1000Prologue() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerOnEnterWorld(questId);
		qe.registerOnMovieEndQuest(1, questId);
	}

	@Override
	public boolean onEnterWorldEvent(QuestEnv env) {
		Player player = env.getPlayer();
		if (player.getCommonData().getRace() != Race.ELYOS)
			return false;
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null) {
			env.setQuestId(questId);
			QuestService.startQuest(env);
		}
		qs = player.getQuestStateList().getQuestState(questId);
		if (qs.getStatus() == QuestStatus.START) {
			PacketSendUtility.sendPacket(player, new SM_PLAY_MOVIE(1, 1));
			return true;
		}
		return false;
	}

	@Override
	public boolean onMovieEndEvent(QuestEnv env, int movieId) {
		if (movieId != 1)
			return false;
		Player player = env.getPlayer();
		if (player.getCommonData().getRace() != Race.ELYOS)
			return false;
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() != QuestStatus.START)
			return false;
		qs.setStatus(QuestStatus.REWARD);
		QuestService.finishQuest(env);
		return true;
	}
}
