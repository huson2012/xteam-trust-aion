package com.light.gameserver.network.aion.clientpackets;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.aion.AionClientPacket;
import com.light.gameserver.network.aion.AionConnection.State;
import com.light.gameserver.services.item.ItemSplitService;

/**
 * @author kosyak
 */
public class CM_SPLIT_ITEM extends AionClientPacket {

	int sourceItemObjId;
	byte sourceStorageType;
	long itemAmount;
	int destinationItemObjId;
	byte destinationStorageType;
	short slotNum;

	public CM_SPLIT_ITEM(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	@Override
	protected void readImpl() {
		sourceItemObjId = readD();
		itemAmount = readD();

		readB(4); // Nothing

		sourceStorageType = readSC();
		destinationItemObjId = readD();
		destinationStorageType = readSC();
		slotNum = readSH();
	}

	@Override
	protected void runImpl() {
		Player player = getConnection().getActivePlayer();
		ItemSplitService.splitItem(player, sourceItemObjId, destinationItemObjId, itemAmount, slotNum, sourceStorageType,
			destinationStorageType);
	}
}
