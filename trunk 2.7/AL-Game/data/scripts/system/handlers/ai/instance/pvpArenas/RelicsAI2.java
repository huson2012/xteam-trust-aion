/*
 * This file is part of aion-lightning <aion-lightning.org>
 *
 * aion-lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-lightning. If not, see <http://www.gnu.org/licenses/>.
 */
package ai.instance.pvpArenas;

import ai.ActionItemNpcAI2;
import com.light.gameserver.ai2.AI2Actions;
import com.light.gameserver.ai2.AIName;
import com.light.gameserver.model.gameobjects.player.Player;

/**
 *
 * @author xTz
 */
@AIName("pvparenarelics")
public class RelicsAI2 extends ActionItemNpcAI2 {

	private boolean isRewarded;

	@Override
	protected void handleDialogStart(Player player) {
		player.getActionItemNpc().setCondition(1, 0, getTalkDelay());
		super.handleUseItemStart(player);
	}

	@Override
	protected void handleUseItemFinish(Player player) {
		if (!isRewarded) {
			isRewarded = true;
			AI2Actions.handleUseItemFinish(this, player);
			AI2Actions.deleteOwner(this);
		}
	}
}
