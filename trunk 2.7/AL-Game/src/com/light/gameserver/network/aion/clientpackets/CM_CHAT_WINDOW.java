package com.light.gameserver.network.aion.clientpackets;

import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.network.aion.AionClientPacket;
import com.light.gameserver.network.aion.AionConnection.State;
import com.light.gameserver.network.aion.serverpackets.SM_CHAT_WINDOW;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.world.World;

/**
 * @author prix
 */
public class CM_CHAT_WINDOW extends AionClientPacket {

	private String playerName;
	@SuppressWarnings("unused")
	private int unk;

	public CM_CHAT_WINDOW(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	@Override
	protected void readImpl() {
		playerName = readS();
		unk = readD();
	}

	@Override
	protected void runImpl() {
		Player player = getConnection().getActivePlayer();
		Player target = World.getInstance().findPlayer(playerName);

		if (target == null)
			return;

		PacketSendUtility.sendPacket(player, new SM_CHAT_WINDOW(target));
	}
}
