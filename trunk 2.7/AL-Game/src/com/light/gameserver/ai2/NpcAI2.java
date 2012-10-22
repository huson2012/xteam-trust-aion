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
package com.light.gameserver.ai2;

import com.light.gameserver.ai2.handler.ActivateEventHandler;
import com.light.gameserver.ai2.handler.DiedEventHandler;
import com.light.gameserver.ai2.handler.ShoutEventHandler;
import com.light.gameserver.ai2.handler.SpawnEventHandler;
import com.light.gameserver.ai2.poll.AIAnswer;
import com.light.gameserver.ai2.poll.AIAnswers;
import com.light.gameserver.ai2.poll.AIQuestion;
import com.light.gameserver.ai2.poll.NpcAIPolls;
import com.light.gameserver.configs.main.AIConfig;
import com.light.gameserver.controllers.attack.AggroList;
import com.light.gameserver.controllers.effect.EffectController;
import com.light.gameserver.controllers.movement.NpcMoveController;
import com.light.gameserver.model.Race;
import com.light.gameserver.model.TribeClass;
import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.model.gameobjects.Npc;
import com.light.gameserver.model.gameobjects.VisibleObject;
import com.light.gameserver.model.skill.NpcSkillList;
import com.light.gameserver.model.stats.container.NpcLifeStats;
import com.light.gameserver.model.templates.npc.NpcTemplate;
import com.light.gameserver.model.templates.spawns.SpawnTemplate;
import com.light.gameserver.utils.MathUtil;
import com.light.gameserver.world.knownlist.KnownList;

/**
 * @author ATracer
 */
@AIName("npc")
public class NpcAI2 extends AITemplate {

	@Override
	public Npc getOwner() {
		return (Npc) super.getOwner();
	}

	protected NpcTemplate getObjectTemplate() {
		return getOwner().getObjectTemplate();
	}

	protected SpawnTemplate getSpawnTemplate() {
		return getOwner().getSpawn();
	}

	protected NpcLifeStats getLifeStats() {
		return getOwner().getLifeStats();
	}

	protected Race getRace() {
		return getOwner().getRace();
	}

	protected TribeClass getTribe() {
		return getOwner().getTribe();
	}

	protected EffectController getEffectController() {
		return getOwner().getEffectController();
	}

	protected KnownList getKnownList() {
		return getOwner().getKnownList();
	}

	protected AggroList getAggroList() {
		return getOwner().getAggroList();
	}

	protected NpcSkillList getSkillList() {
		return getOwner().getSkillList();
	}

	protected Creature getCreator() {
		return getOwner().getCreator();
	}

	/**
	 * DEPRECATED as movements will be processed as commands only from ai
	 */
	protected NpcMoveController getMoveController() {
		return getOwner().getMoveController();
	}

	protected int getNpcId() {
		return getOwner().getNpcId();
	}

	protected int getCreatorId() {
		return getOwner().getCreatorId();
	}

	protected boolean isInRange(VisibleObject object, int range) {
		return MathUtil.isIn3dRange(getOwner(), object, range);
	}

	@Override
	protected void handleActivate() {
		ActivateEventHandler.onActivate(this);
	}

	@Override
	protected void handleDeactivate() {
		ActivateEventHandler.onDeactivate(this);
	}

	@Override
	protected void handleSpawned() {
		SpawnEventHandler.onSpawn(this);
	}

	@Override
	protected void handleRespawned() {
		SpawnEventHandler.onRespawn(this);
	}

	@Override
	protected void handleDespawned() {
		if (poll(AIQuestion.CAN_SHOUT))
			ShoutEventHandler.onBeforeDespawn(this);
		SpawnEventHandler.onDespawn(this);
	}

	@Override
	protected void handleDied() {
		DiedEventHandler.onSimpleDie(this);
	}

	@Override
	protected void handleMoveArrived() {
		if (!poll(AIQuestion.CAN_SHOUT) || getSpawnTemplate().getWalkerId() == null)
			return;
		ShoutEventHandler.onReachedWalkPoint(this);
	}

	@Override
	protected void handleTargetChanged(Creature creature) {
		super.handleMoveArrived();
		if (!poll(AIQuestion.CAN_SHOUT))
			return;
		ShoutEventHandler.onSwitchedTarget(this, creature);
	}

	@Override
	protected AIAnswer pollInstance(AIQuestion question) {
		switch (question) {
			case SHOULD_DECAY:
				return NpcAIPolls.shouldDecay(this);
			case SHOULD_RESPAWN:
				return NpcAIPolls.shouldRespawn(this);
			case SHOULD_REWARD:
				return AIAnswers.POSITIVE;
			case CAN_SHOUT:
				return isMayShout() ? AIAnswers.POSITIVE : AIAnswers.NEGATIVE;
			default:
				return null;
		}
	}

	@Override
	public boolean isMayShout() {
		// temp fix, we shouldn't rely on it because of inheritance
		if (AIConfig.SHOUTS_ENABLE)
			return getOwner().mayShout(0);
		return false;
	}

	public boolean isMoveSupported() {
		return getOwner().getGameStats().getMovementSpeedFloat() > 0;
	}

}
