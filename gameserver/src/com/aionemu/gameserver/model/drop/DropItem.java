/*
 * This file is part of aion-unique <aion-unique.com>.
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
package com.aionemu.gameserver.model.drop;

import com.aionemu.commons.utils.Rnd;

/**
 * @author ATracer
 *
 */
public class DropItem
{

	private int index = 0;

	private long count = 0;

	private DropTemplate dropTemplate;

	private int playerObjId = 0;

	public DropItem(DropTemplate dropTemplate)
	{
		this.dropTemplate = dropTemplate;
	}

	/**
	 *  Regenerates item count upon each call
	 *  // TODO input parameters - based on attacker stats
	 *  // TODO more precise calculations (non-linear)
	 */
	public void calculateCount(float rate)
	{
		if(Rnd.get() * 100 < dropTemplate.getChance() * rate)
		{
			count = Rnd.get(dropTemplate.getMin(), dropTemplate.getMax());
		}
	}

	/**
	 * @return the index
	 */
	public int getIndex()
	{
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index)
	{
		this.index = index;
	}

	/**
	 * @return the count
	 */
	public long getCount()
	{
		return count;
	}

	/**
	 * @param count
	 */
	public void setCount(long count)
	{
		this.count = count;
	}

	/**
	 * @return the dropTemplate
	 */
	public DropTemplate getDropTemplate()
	{
		return dropTemplate;
	}

	/**
	 * @return the playerObjId
	 */
	public int getPlayerObjId()
	{
		return playerObjId;
	}

	/**
	 * @param playerObjId the playerObjId to set
	 */
	public void setPlayerObjId(int playerObjId)
	{
		this.playerObjId = playerObjId;
	}
}
