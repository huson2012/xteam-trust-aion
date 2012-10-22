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
package com.light.gameserver.utils.stats;

import com.light.gameserver.configs.main.RateConfig;
import com.light.gameserver.model.DescriptionId;
import com.light.gameserver.model.Race;
import com.light.gameserver.model.gameobjects.player.Player;

import javax.xml.bind.annotation.XmlEnum;

/**
 * @author ATracer
 * @author Sarynth
 * @author Imaginary
 */
@XmlEnum
public enum AbyssRankEnum {
	GRADE9_SOLDIER(1, 120, 9, 0, 0, 1802431, "Воин 9го ранга"),
	GRADE8_SOLDIER(2, 168, 18, 1200, 0, 1802433, "Воин 8го ранга"),
	GRADE7_SOLDIER(3, 235, 36, 4220, 0, 1802435, "Воин 7го ранга"),
	GRADE6_SOLDIER(4, 329, 59, 10990, 0, 1802437, "Воин 6го ранга"),
	GRADE5_SOLDIER(5, 461, 75, 23500, 0, 1802439, "Воин 5го ранга"),
	GRADE4_SOLDIER(6, 645, 90, 42780, 0, 1802441, "Воин 4го ранга"),
	GRADE3_SOLDIER(7, 903, 125, 69700, 0, 1802443, "Воин 3го ранга"),
	GRADE2_SOLDIER(8, 1264, 250, 105600, 0, 1802445, "Воин 2го ранга"),
	GRADE1_SOLDIER(9, 1770, 500, 150800, 0, 1802447, "Воин 1го ранга"),
	STAR1_OFFICER(10, 2124, 800, 214100, 1000, 1802449, "Офицер 1го ранга"),
	STAR2_OFFICER(11, 2549, 1300, 278700, 800, 1802451, "Офицер 2го ранга"),
	STAR3_OFFICER(12, 3059, 1700, 344500, 500, 1802453, "Офицер 3го ранга"),
	STAR4_OFFICER(13, 3671, 2300, 411700, 300, 1802455, "Офицер 4го ранга"),
	STAR5_OFFICER(14, 4405, 2700, 488200, 100, 1802457, "Офицер 5го ранга"),
	GENERAL(15, 5286, 4300, 565400, 30, 1802459, "Генерал"),
	GREAT_GENERAL(16, 6343, 7000, 643200, 10, 1802461, "Великий генерал"),
	COMMANDER(17, 7612, 9500, 721600, 3, 1802463, "Командующий"),
	SUPREME_COMMANDER(18, 9134, 12000, 800700, 1, 1802465, "Военачальник");

	private int id;
	private int pointsGained;
	private int pointsLost;
	private int required;
	private int quota;
	private int descriptionId;
        private String rusname;

	/**
	 * @param id
	 * @param pointsGained
	 * @param pointsLost
	 * @param required
	 * @param quota
	 */
	private AbyssRankEnum(int id, int pointsGained, int pointsLost, int required, int quota, int descriptionId, String rusname) {
		this.id = id;
		this.pointsGained = pointsGained;
		this.pointsLost = pointsLost;
		this.required = required * RateConfig.ABYSS_RANK_RATE;
		this.quota = quota;
		this.descriptionId = descriptionId;
                this.rusname = rusname;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the pointsLost
	 */
	public int getPointsLost() {
		return pointsLost;
	}

	/**
	 * @return the pointsGained
	 */
	public int getPointsGained() {
		return pointsGained;
	}

	/**
	 * @return AP required for Rank
	 */
	public int getRequired() {
		return required;
	}

	/**
	 * @return The quota is the maximum number of allowed player to have the rank
	 */
	public int getQuota() {
		return quota;
	}
	
	public int getDescriptionId() {
		return descriptionId;
	}

	public static DescriptionId getRankDescriptionId(Player player){
		int pRankId = player.getAbyssRank().getRank().getId();
		for (AbyssRankEnum rank : values()) {
			if (rank.getId() == pRankId) {
				int descId = rank.getDescriptionId();
				return (player.getRace() == Race.ELYOS) ? new DescriptionId(descId) : new DescriptionId(descId + 36);
			}
		}
		throw new IllegalArgumentException("No rank Description Id found for player: " + player);
	}

	/**
	 * @param id
	 * @return The abyss rank enum by his id
	 */
	public static AbyssRankEnum getRankById(int id) {
		for (AbyssRankEnum rank : values()) {
			if (rank.getId() == id)
				return rank;
		}
		throw new IllegalArgumentException("Invalid abyss rank provided");
	}

	/**
	 * @param ap
	 * @return The abyss rank enum for his needed ap
	 */
	public static AbyssRankEnum getRankForAp(int ap) {
		AbyssRankEnum r = AbyssRankEnum.GRADE9_SOLDIER;
		for (AbyssRankEnum rank : values()) {
			if (rank.getRequired() <= ap)
				r = rank;
			else
				break;
		}
		return r;
	}
        
        public String getRusname() {
		return this.rusname;
	}
}
