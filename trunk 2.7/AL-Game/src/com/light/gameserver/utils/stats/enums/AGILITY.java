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
 
public enum AGILITY {
	WARRIOR(100),
	GLADIATOR(100),
	TEMPLAR(110),
	SCOUT(100),
	ASSASSIN(100),
	RANGER(100),
	MAGE(95),
	SORCERER(100),
	SPIRIT_MASTER(100),
	PRIEST(100),
	CLERIC(90),
	CHANTER(90);

	private int value;

	private AGILITY(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
