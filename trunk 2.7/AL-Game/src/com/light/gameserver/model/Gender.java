/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 * aion-emu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-emu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.light.gameserver.model;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Creature gender. Typically there are males and females. But who knows, maybe NC can invent something new ;)
 * 
 * @author SoulKeeper
 */
@XmlEnum
public enum Gender {
	/**
	 * Males
	 */
	MALE(0, "Мужской"),

	/**
	 * Females
	 */
	FEMALE(1, "Женский");

	/**
	 * id of gender
	 */
	private int genderId;
        private String rusname;

	/**
	 * Constructor.
	 * 
	 * @param genderId
	 *          id of the gender
	 */
	private Gender(int genderId, String rusname) {
		this.genderId = genderId;
                this.rusname = rusname;
	}

	/**
	 * Get id of this gender.
	 * 
	 * @return gender id
	 */
	public int getGenderId() {
		return genderId;
	}
        
        public String getRusname() {
		return this.rusname;
	}
}
