/*
 * This file is part of aion-lightning <aion-lightning.com>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.light.gameserver.ai2.handler;

import com.light.gameserver.ai2.AISubState;
import com.light.gameserver.ai2.NpcAI2;
import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.model.gameobjects.Npc;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.light.gameserver.questEngine.QuestEngine;
import com.light.gameserver.questEngine.model.QuestEnv;
import com.light.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer
 */
public class TalkEventHandler {

	/**
	 * @param npcAI
	 * @param creature
	 */
	public static void onTalk(NpcAI2 npcAI, Creature creature) {
		onSimpleTalk(npcAI, creature);

		if (creature instanceof Player) {
			Player player = (Player) creature;
			if (QuestEngine.getInstance().onDialog(new QuestEnv(npcAI.getOwner(), player, 0, -1)))
				return;
			PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(npcAI.getOwner().getObjectId(), 10));
		}

	}

	/**
	 * @param npcAI
	 * @param creature
	 */
	public static void onSimpleTalk(NpcAI2 npcAI, Creature creature) {
		npcAI.setSubStateIfNot(AISubState.TALK);
		npcAI.getOwner().setTarget(creature);
	}

	/**
	 * @param npcAI
	 * @param creature
	 */
	public static void onFinishTalk(NpcAI2 npcAI, Creature creature) {
		Npc owner = npcAI.getOwner();
		if (owner.isTargeting(creature.getObjectId())) {
			owner.setTarget(null);
			npcAI.think();
		}
	}

	/**
	 * No SM_LOOKATOBJECT broadcast
	 * 
	 * @param npcAI
	 * @param creature
	 */
	public static void onSimpleFinishTalk(NpcAI2 npcAI, Creature creature) {
		Npc owner = npcAI.getOwner();
		if (owner.isTargeting(creature.getObjectId()) && npcAI.setSubStateIfNot(AISubState.NONE)) {
			owner.setTarget(null);
		}
	}

}
