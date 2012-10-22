/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.light.gameserver.skillengine.effect;

import java.util.concurrent.Future;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.light.gameserver.dataholders.DataManager;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.stats.container.StatEnum;
import com.light.gameserver.network.aion.serverpackets.SM_MANTRA_EFFECT;
import com.light.gameserver.skillengine.model.Effect;
import com.light.gameserver.skillengine.model.SkillTemplate;
import com.light.gameserver.utils.MathUtil;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.ThreadPoolManager;

/**
 * @author ATracer modified by kecimis
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuraEffect")
public class AuraEffect extends EffectTemplate {

	@XmlAttribute
	protected int distance;
	@XmlAttribute(name = "skill_id")
	protected int skillId;

	@Override
	public void applyEffect(Effect effect) {
		effect.addToEffectedController();
	}

	@Override
	public void onPeriodicAction(final Effect effect) {
		final Player effector = (Player) effect.getEffector();
		final int actualRange = (int)(distance * effector.getGameStats().getStat(StatEnum.BOOST_MANTRA_RANGE, 100).getCurrent() / 100f);
		if (effector.isInAlliance2()) {
			for (Player player : effector.getPlayerAllianceGroup2().getMembers()) {
				if (player.isOnline() && MathUtil.isIn3dRange(effector, player, actualRange)) {
					applyAuraTo(player, effect);
				}
			}
		}
		else if (effector.isInGroup2()) {
			for (Player player : effector.getPlayerGroup2().getOnlineMembers()) {
				if (MathUtil.isIn3dRange(effector, player, actualRange)) {
					applyAuraTo(player, effect);
				}
			}
		}
		else {
			applyAuraTo(effector, effect);
		}
		
		PacketSendUtility.broadcastPacket(effector, new SM_MANTRA_EFFECT(effector, skillId));
	}

	/**
	 * @param effector
	 */
	private void applyAuraTo(Player effected, Effect effect) {
		SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skillId);
		Effect e = new Effect(effected, effected, template, template.getLvl(), template.getEffectsDuration());
		e.initialize();
		e.applyEffect();
	}

	@Override
	public void startEffect(final Effect effect) {
		Future<?> task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				onPeriodicAction(effect);
			}
		}, 0, 6500);
		effect.setPeriodicTask(task, position);
	}

	@Override
	public void endEffect(Effect effect) {
		// nothing todo
	}

}
