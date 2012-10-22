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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.light.gameserver.controllers.attack.AttackUtil;
import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.skillengine.action.DamageType;
import com.light.gameserver.skillengine.model.DashStatus;
import com.light.gameserver.skillengine.model.Effect;
import com.light.gameserver.skillengine.model.Skill;
import com.light.gameserver.utils.MathUtil;
import com.light.gameserver.world.World;
import com.light.gameserver.world.geo.GeoService;

/**
 * @author Sarynth
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MoveBehindEffect")
public class MoveBehindEffect extends DamageEffect {

	@Override
	public void applyEffect(Effect effect) {
		super.applyEffect(effect);
		final Player effector = (Player) effect.getEffector();
		// Deselect targets
		AttackUtil.deselectTargettingMe(effector);

		// Move Effector to Effected
		Skill skill = effect.getSkill();
		World.getInstance().updatePosition(effector, skill.getX(), skill.getY(), skill.getZ(), skill.getH());
	}

	@Override
	public void calculate(Effect effect) {
		if (effect.getEffected() == null)
			return;
		if (!(effect.getEffector() instanceof Player))
			return;
		if (!super.calculate(effect, DamageType.PHYSICAL))
			return;
		
		effect.setDashStatus(DashStatus.MOVEBEHIND);

		final Creature effected = effect.getEffected();
		double radian = Math.toRadians(MathUtil.convertHeadingToDegree(effected.getHeading()));
		float x1 = (float) (Math.cos(Math.PI + radian) * 1.3F);
		float y1 = (float) (Math.sin(Math.PI + radian) * 1.3F);
		float z = GeoService.getInstance().getZAfterMoveBehind(effected.getWorldId(), effected.getX() + x1,
			effected.getY() + y1, effected.getZ(), effected.getInstanceId());
		effect.getSkill().setTargetPosition(effected.getX() + x1, effected.getY() + y1, z, effected.getHeading());
	}
}
