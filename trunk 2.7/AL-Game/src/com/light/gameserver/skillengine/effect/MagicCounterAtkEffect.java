/*
 Aion-core by vegar
 */
package com.light.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.light.gameserver.controllers.observer.ActionObserver;
import com.light.gameserver.controllers.observer.ObserverType;
import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.model.stats.container.CreatureLifeStats;
import com.light.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.LOG;
import com.light.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.TYPE;
import com.light.gameserver.skillengine.model.Effect;
import com.light.gameserver.skillengine.model.Skill;
import com.light.gameserver.skillengine.model.SkillSubType;
import com.light.gameserver.skillengine.model.SkillType;
import com.light.gameserver.utils.ThreadPoolManager;

/**
 * @author ViAl
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MagicCounterAtkEffect")
public class MagicCounterAtkEffect extends EffectTemplate {

	@XmlAttribute
	protected int maxdmg;

	//TODO bosses are resistent to this?
	
	@Override
	public void applyEffect(Effect effect) {
		effect.addToEffectedController();
	}

	@Override
	public void startEffect(final Effect effect) {
		final Creature effector = effect.getEffector();
		final Creature effected = effect.getEffected();
		final CreatureLifeStats<? extends Creature> cls = effect.getEffected().getLifeStats();
		ActionObserver observer = null;

		observer = new ActionObserver(ObserverType.SKILLUSE) {

			@Override
			public void skilluse(final Skill skill) {
				ThreadPoolManager.getInstance().schedule(new Runnable() {

					@Override
					public void run() {
						if (skill.getSkillTemplate().getType() == SkillType.MAGICAL && 
							skill.getSkillTemplate().getSubType() == SkillSubType.ATTACK) {
							if (cls.getMaxHp() / 100 * value <= maxdmg)
								effected.getController().onAttack(effector, effect.getSkillId(), TYPE.DAMAGE,
									cls.getMaxHp() / 100 * value, true, LOG.REGULAR);
							else
								effected.getController().onAttack(effector, maxdmg, true);
						}
					}
				}, 0);

			}
		};

		effect.setActionObserver(observer, position);
		effected.getObserveController().addObserver(observer);
	}

	@Override
	public void endEffect(Effect effect) {
		ActionObserver observer = effect.getActionObserver(position);
		if (observer != null)
			effect.getEffected().getObserveController().removeObserver(observer);
	}
}
