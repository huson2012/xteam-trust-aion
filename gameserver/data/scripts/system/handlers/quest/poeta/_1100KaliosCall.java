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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package quest.poeta;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author MrPoke
 * 
 */
public class _1100KaliosCall extends QuestHandler
{

	private final static int	questId	= 1100;

	public _1100KaliosCall()
	{
		super(questId);
	}

	@Override
	public void register()
	{
		qe.setNpcQuestData(203067).addOnTalkEvent(questId);
		qe.setQuestEnterZone(ZoneName.AKARIOS_VILLAGE).add(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env)
	{
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if(qs == null)
			return false;

		int targetId = 0;
		if(env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		if(targetId != 203067)
			return false;
		if(qs.getStatus() == QuestStatus.START)
		{
			if(env.getDialogId() == 25)
			{
				qs.setQuestVar(1);
				qs.setStatus(QuestStatus.REWARD);
				updateQuestStatus(player, qs);
				return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
			}
			else
				return defaultQuestStartDialog(env);
		}
		else if(qs.getStatus() == QuestStatus.REWARD)
		{
			if(env.getDialogId() == 17)
			{
				int[] ids = {1001, 1002, 1003, 1004, 1005};
				for (int id : ids)
				{
					QuestService.startQuest(new QuestEnv(env.getVisibleObject(), env.getPlayer(), id, env.getDialogId()), QuestStatus.LOCKED);
				}
			}
			return defaultQuestEndDialog(env);
		}
		return false;
	}

	@Override
	public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName)
	{
		if(zoneName != ZoneName.AKARIOS_VILLAGE)
			return false;
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if(qs != null)
			return false;
		env.setQuestId(questId);
		QuestService.startQuest(env, QuestStatus.START);
		return true;
	}
}
