/*
*
* Приватный Java EMU от разработчиков Aion Light
* Созданный специально для проэкта Aion-Light.Ru
*
*/
package com.light.gameserver.utils.stats.enums;

/**
 * @author Drusik
 */
 
public enum KNOWLEDGE {
	WARRIOR(90),
	GLADIATOR(90),
	TEMPLAR(90),
	SCOUT(90),
	ASSASSIN(90),
	RANGER(120),
	MAGE(115),
	SORCERER(120),
	SPIRIT_MASTER(115),
	PRIEST(100),
	CLERIC(105),
	CHANTER(105);

	private int value;

	private KNOWLEDGE(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
