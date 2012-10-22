/*
 * This file is part of aion-lightning <aion-lightning.com>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.toypet.PetAdoptionService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author ATracer
 */
public class Pet extends ChatCommand {

	public Pet() {
		super("pet");
	}

	@Override
	public void execute(Player player, String... params) {
		String command = params[0];
		if ("add".equals(command)) {
			int petId = Integer.parseInt(params[1]);
			String name = params[2];
			PetAdoptionService.addPet(player, petId, name, 0);
		}
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "syntax //pet <add [petid name]>");
	}
}
