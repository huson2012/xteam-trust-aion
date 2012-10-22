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
 
public enum FLY_SPEED {
	WARRIOR(9),
	GLADIATOR(9),
	TEMPLAR(9),
	SCOUT(9),
	ASSASSIN(9),
	RANGER(9),
	MAGE(9),
	SORCERER(9),
	SPIRIT_MASTER(9),
	PRIEST(9),
	CLERIC(9),
	CHANTER(9);

	private int value;

	private FLY_SPEED(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
