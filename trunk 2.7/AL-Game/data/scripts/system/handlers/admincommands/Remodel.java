/*
 *  This file is part of Zetta-Core Engine <http://www.zetta-core.org>.
 *
 *  Zetta-Core is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published
 *  by the Free Software Foundation, either version 3 of the License,
 *  or (at your option) any later version.
 *
 *  Zetta-Core is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a  copy  of the GNU General Public License
 *  along with Zetta-Core.  If not, see <http://www.gnu.org/licenses/>.
 */
package admincommands;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.ItemRemodelService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;


/**
 * @author HellBoy
 *
 */
public class Remodel extends ChatCommand
{
	public Remodel()
	{
		super("remodel");
	}

	@Override
	public void execute (Player player, String... params)
	{
		//String[] params = new String[]{};
		//if(params.length > 1)
			
		
		if(params.length == 0 || params.length > 2)
		{
			PacketSendUtility.sendMessage(player, "Syntax: .remodel <MAIN_ITEM_ID> <SKIN_ITEM_ID>");
			return;
		}
		
		int itemId = 0;
		
		try
		{
			itemId = Integer.parseInt(params[0]);
		}
		catch (NumberFormatException ex)
		{
			PacketSendUtility.sendMessage(player, "itemId не верный");
			return;
		}
		catch (Exception ex2)
		{
			PacketSendUtility.sendMessage(player, "itemId не верный");
			return;
		}
		
		int skinId = 0;
		
		try
		{
			skinId = Integer.parseInt(params[1]);
		}
		catch (NumberFormatException ex)
		{
			PacketSendUtility.sendMessage(player, "skinId не верный");
			return;
		}
		catch (Exception ex2)
		{
			PacketSendUtility.sendMessage(player, "skinId не верный");
			return;
		}
		
		if(player.getInventory().getFirstItemByItemId(itemId) != null && player.getInventory().getFirstItemByItemId(skinId) != null)
		{
			int keepItemObjId = player.getInventory().getFirstItemByItemId(itemId).getObjectId();
			int extractItemObjId = player.getInventory().getFirstItemByItemId(skinId).getObjectId();
			
			ItemRemodelService.remodelItem(player, keepItemObjId, extractItemObjId);
		}
		else
			PacketSendUtility.sendMessage(player, "Syntax: .remodel <MAIN_ITEM_ID> <SKIN_ITEM_ID>");
	}
}
