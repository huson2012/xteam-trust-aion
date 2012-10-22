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
 
public enum WILL {
	WARRIOR(90),
	GLADIATOR(90),
	TEMPLAR(105),
	SCOUT(90),
	ASSASSIN(90),
	RANGER(110),
	MAGE(115),
	SORCERER(110),
	SPIRIT_MASTER(115),
	PRIEST(110),
	CLERIC(110),
	CHANTER(110);

	private int value;

	private WILL(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
