/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.light.gameserver.network.aion.serverpackets;

import com.light.gameserver.model.gameobjects.Item;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.templates.item.ItemTemplate;
import com.light.gameserver.network.aion.AionConnection;
import com.light.gameserver.network.aion.AionServerPacket;
import com.light.gameserver.network.aion.iteminfo.ItemInfoBlob;
import com.light.gameserver.network.aion.iteminfo.ItemInfoBlob.ItemBlobType;
import com.light.gameserver.services.item.ItemPacketService.ItemUpdateType;

/**
 * @author kosyachok
 * @author -Nemesiss-
 */
public class SM_WAREHOUSE_UPDATE_ITEM extends AionServerPacket {

	private Player player;
	private Item item;
	private int warehouseType;
	private ItemUpdateType updateType;

	public SM_WAREHOUSE_UPDATE_ITEM(Player player, Item item, int warehouseType, ItemUpdateType updateType) {
		this.player = player;
		this.item = item;
		this.warehouseType = warehouseType;
		this.updateType = updateType;
	}

	@Override
	protected void writeImpl(AionConnection con) {
		ItemTemplate itemTemplate = item.getItemTemplate();

		writeD(item.getObjectId());
		writeC(warehouseType);
		writeNameId(itemTemplate.getNameId());

		ItemInfoBlob itemInfoBlob = new ItemInfoBlob(player, item);
		itemInfoBlob.addBlobEntry(ItemBlobType.GENERAL_INFO);
		itemInfoBlob.writeMe(getBuf());

		if (updateType.isSendable())
			writeH(updateType.getMask());
	}
}
